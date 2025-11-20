package com.docpet.animalhospital.service;

import com.docpet.animalhospital.domain.Appointment;
import com.docpet.animalhospital.domain.Owner;
import com.docpet.animalhospital.domain.Pet;
import com.docpet.animalhospital.domain.Vet;
import com.docpet.animalhospital.repository.AppointmentRepository;
import com.docpet.animalhospital.repository.OwnerRepository;
import com.docpet.animalhospital.repository.PetRepository;
import com.docpet.animalhospital.repository.VetRepository;
import com.docpet.animalhospital.security.AuthoritiesConstants;
import com.docpet.animalhospital.security.SecurityUtils;
import com.docpet.animalhospital.service.dto.AppointmentDTO;
import com.docpet.animalhospital.service.mapper.AppointmentMapper;
import com.docpet.animalhospital.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppointmentService {

    private static final Logger LOG = LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final VetRepository vetRepository;

    public AppointmentService(
        AppointmentRepository appointmentRepository, 
        AppointmentMapper appointmentMapper,
        OwnerRepository ownerRepository,
        PetRepository petRepository,
        VetRepository vetRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;
    }

    public AppointmentDTO save(AppointmentDTO appointmentDTO) {
        LOG.debug("Request to save Appointment : {}", appointmentDTO);
        Appointment appointment = appointmentMapper.toEntity(appointmentDTO);
        appointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDto(appointment);
    }

    public AppointmentDTO update(AppointmentDTO appointmentDTO) {
        LOG.debug("Request to update Appointment : {}", appointmentDTO);
        Appointment appointment = appointmentMapper.toEntity(appointmentDTO);
        appointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDto(appointment);
    }

    public Optional<AppointmentDTO> partialUpdate(AppointmentDTO appointmentDTO) {
        LOG.debug("Request to partially update Appointment : {}", appointmentDTO);
        return appointmentRepository
            .findById(appointmentDTO.getId())
            .map(existingAppointment -> {
                return appointmentMapper.partialUpdate(existingAppointment, appointmentDTO);
            })
            .map(appointmentRepository::save)
            .map(appointmentMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<AppointmentDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Appointments");
        return appointmentRepository.findAll(pageable).map(appointmentMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<AppointmentDTO> findOne(Long id) {
        LOG.debug("Request to get Appointment : {}", id);
        return appointmentRepository.findById(id).map(appointmentMapper::toDto);
    }

    public void delete(Long id) {
        LOG.debug("Request to delete Appointment : {}", id);
        appointmentRepository.deleteById(id);
    }

    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO, String currentUserLogin) {
        LOG.debug("Request to create Appointment for user: {}", currentUserLogin);

        if (appointmentDTO.getPet() == null || appointmentDTO.getPet().getId() == null) {
            throw new BadRequestAlertException("Pet is required", "appointment", "petrequired");
        }

        // Load pet cùng với owner để tránh lazy loading issues
        Pet pet = petRepository.findByIdWithOwner(appointmentDTO.getPet().getId())
            .orElseThrow(() -> new BadRequestAlertException("Pet not found", "appointment", "petnotfound"));

        // Kiểm tra pet có owner không
        if (pet.getOwner() == null) {
            throw new BadRequestAlertException("Pet does not have an owner assigned", "appointment", "petnoowner");
        }

        // Xác định owner cho appointment
        Owner appointmentOwner;
        
        // Kiểm tra xem current user có phải là owner của pet không
        Optional<Owner> currentUserOwnerOpt = ownerRepository.findByUser_Login(currentUserLogin);
        if (currentUserOwnerOpt.isPresent() && currentUserOwnerOpt.get().getId().equals(pet.getOwner().getId())) {
            // Current user là owner của pet
            appointmentOwner = currentUserOwnerOpt.get();
            LOG.debug("Current user is the owner of the pet");
        } else {
            // Current user không phải owner (có thể là vet) - sử dụng owner của pet
            appointmentOwner = pet.getOwner();
            LOG.debug("Current user is not the owner, using pet's owner: {}", appointmentOwner.getId());
        }

        if (appointmentDTO.getVet() == null || appointmentDTO.getVet().getId() == null) {
            throw new BadRequestAlertException("Vet is required", "appointment", "vetrequired");
        }

        Vet vet = vetRepository.findById(appointmentDTO.getVet().getId())
            .orElseThrow(() -> new BadRequestAlertException("Vet not found", "appointment", "vetnotfound"));

        if (!"EMERGENCY".equals(appointmentDTO.getAppointmentType()) && !"NORMAL".equals(appointmentDTO.getAppointmentType())) {
            throw new BadRequestAlertException("Invalid appointment type", "appointment", "invalidtype");
        }

        if (!"AT_HOME".equals(appointmentDTO.getLocationType()) && !"AT_CLINIC".equals(appointmentDTO.getLocationType())) {
            throw new BadRequestAlertException("Invalid location type", "appointment", "invalidlocation");
        }

        // Tính toán timeEnd nếu chưa có (mặc định 1 giờ cho NORMAL, 2 giờ cho EMERGENCY)
        ZonedDateTime timeEnd = appointmentDTO.getTimeEnd();
        if (timeEnd == null) {
            int durationHours = "EMERGENCY".equals(appointmentDTO.getAppointmentType()) ? 2 : 1;
            timeEnd = appointmentDTO.getTimeStart().plusHours(durationHours);
        }

        // Logic mới: kiểm tra không trùng giờ startTime và cách nhau ít nhất 1 tiếng
        List<Appointment> appointmentsInSameDay = appointmentRepository.findAppointmentsByVetAndDate(
            vet.getId(), 
            appointmentDTO.getTimeStart()
        );

        for (Appointment appointment : appointmentsInSameDay) {
            long hoursDiff = Math.abs(java.time.Duration.between(appointment.getTimeStart(), appointmentDTO.getTimeStart()).toHours());
            
            // Kiểm tra: trùng giờ (cùng hour) hoặc cách nhau < 1 tiếng
            if (appointment.getTimeStart().getHour() == appointmentDTO.getTimeStart().getHour() || hoursDiff < 1) {
                throw new BadRequestAlertException(
                    "Vet không có sẵn trong khoảng thời gian này. Vui lòng chọn vet khác hoặc thời gian khác (cách nhau ít nhất 1 tiếng).", 
                    "appointment", 
                    "vetnotavailable"
                );
            }
        }

        // Set timeEnd nếu chưa có
        if (appointmentDTO.getTimeEnd() == null) {
            appointmentDTO.setTimeEnd(timeEnd);
        }

        appointmentDTO.setOwner(new com.docpet.animalhospital.service.dto.OwnerDTO(appointmentOwner));
        appointmentDTO.setStatus("PENDING");

        Appointment appointment = appointmentMapper.toEntity(appointmentDTO);
        // Set các quan hệ vì mapper ignore chúng
        appointment.setPet(pet);
        appointment.setVet(vet);
        appointment.setOwner(appointmentOwner);
        
        appointment = appointmentRepository.save(appointment);
        
        Appointment savedAppointment = appointmentRepository.findOneWithEagerRelationships(appointment.getId())
            .orElse(appointment);
        
        LOG.debug("Created Appointment: {}", savedAppointment);
        return appointmentMapper.toDto(savedAppointment);
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> findAllForCurrentOwner(String currentUserLogin) {
        LOG.debug("Request to get all Appointments for owner: {}", currentUserLogin);
        List<Appointment> appointments = appointmentRepository.findByOwner_User_Login(currentUserLogin);
        return appointments.stream()
            .map(appointmentMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> findAllForCurrentVet(String currentUserLogin) {
        LOG.debug("Request to get all Appointments for vet: {}", currentUserLogin);
        List<Appointment> appointments = appointmentRepository.findByVet_User_Login(currentUserLogin);
        return appointments.stream()
            .map(appointmentMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAllAppointmentsByDateForVet(String dateString) {
        LOG.debug("Request to get all appointments for date: {} for vet", dateString);
        LocalDate date = parseDate(dateString);
        return findByDate(date);
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> getPetAppointmentHistory(Long petId, String currentUserLogin) {
        LOG.debug("Request to get appointment history for pet: {} by user: {}", petId, currentUserLogin);

        Pet pet = petRepository.findById(petId)
            .orElseThrow(() -> new BadRequestAlertException("Pet not found", "appointment", "petnotfound"));

        // Kiểm tra xem user có phải là Vet (có role DOCTOR) không
        boolean isVet = SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.DOCTOR);
        
        if (isVet) {
            // Vet có thể xem lịch sử appointment của bất kỳ Pet nào
            LOG.debug("User {} is a Vet, allowing access to pet history", currentUserLogin);
        } else {
            // Owner chỉ có thể xem lịch sử appointment của Pet thuộc về mình
            Owner currentOwner = ownerRepository.findByUser_Login(currentUserLogin)
                .orElseThrow(() -> new BadRequestAlertException("Owner profile not found", "appointment", "noowner"));

            if (!pet.getOwner().getId().equals(currentOwner.getId())) {
                throw new BadRequestAlertException("Pet does not belong to current owner", "appointment", "petnotowned");
            }
        }

        List<Appointment> appointments = appointmentRepository.findByPet_Id(petId);
        return appointments.stream()
            .map(appointmentMapper::toDto)
            .collect(Collectors.toList());
    }

    public AppointmentDTO updateAppointmentStatus(Long appointmentId, String status, String currentUserLogin) {
        LOG.debug("Request to update appointment status: {} for appointment: {} by user: {}", status, appointmentId, currentUserLogin);

        Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found", "appointment", "notfound"));

        Vet currentVet = vetRepository.findByUser_Login(currentUserLogin)
            .orElseThrow(() -> new BadRequestAlertException("Vet profile not found", "appointment", "novet"));

        if ("APPROVED".equals(status) || "REJECTED".equals(status)) {
            appointment.setVet(currentVet);
        }

        appointment.setStatus(status);
        appointment = appointmentRepository.save(appointment);
        
        return appointmentMapper.toDto(appointment);
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> findByDateAndAppointmentType(LocalDate date, String appointmentType) {
        LOG.debug("Request to get appointments by date: {} and type: {}", date, appointmentType);
        List<Appointment> appointments = appointmentRepository.findByDateAndAppointmentType(date, appointmentType);
        return appointments.stream()
            .map(appointmentMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> findByDate(LocalDate date) {
        LOG.debug("Request to get appointments by date: {}", date);
        List<Appointment> appointments = appointmentRepository.findByDate(date);
        return appointments.stream()
            .map(appointmentMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> getEmergencyAppointmentsByDate(String dateString) {
        LOG.debug("Request to get emergency appointments for date: {}", dateString);
        LocalDate date = parseDate(dateString);
        return findByDateAndAppointmentType(date, "EMERGENCY");
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> getRegularAppointmentsByDate(String dateString) {
        LOG.debug("Request to get regular appointments for date: {}", dateString);
        LocalDate date = parseDate(dateString);
        return findByDateAndAppointmentType(date, "NORMAL");
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> findByDateAndAppointmentTypeAndVet(LocalDate date, String appointmentType, String currentUserLogin) {
        LOG.debug("Request to get appointments by date: {}, type: {} and vet: {}", date, appointmentType, currentUserLogin);
        List<Appointment> appointments = appointmentRepository.findByDateAndAppointmentTypeAndVet(date, appointmentType, currentUserLogin);
        return appointments.stream()
            .map(appointmentMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> getEmergencyAppointmentsByDateForVet(String dateString, String currentUserLogin) {
        LOG.debug("Request to get emergency appointments for date: {} for vet: {}", dateString, currentUserLogin);
        LocalDate date = parseDate(dateString);
        return findByDateAndAppointmentTypeAndVet(date, "EMERGENCY", currentUserLogin);
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> getRegularAppointmentsByDateForVet(String dateString, String currentUserLogin) {
        LOG.debug("Request to get regular appointments for date: {} for vet: {}", dateString, currentUserLogin);
        LocalDate date = parseDate(dateString);
        return findByDateAndAppointmentTypeAndVet(date, "NORMAL", currentUserLogin);
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> findByDateAndAppointmentTypeAndVetId(LocalDate date, String appointmentType, Long vetId) {
        LOG.debug("Request to get appointments by date: {}, type: {} and vetId: {}", date, appointmentType, vetId);
        List<Appointment> appointments = appointmentRepository.findByDateAndAppointmentTypeAndVetId(date, appointmentType, vetId);
        return appointments.stream()
            .map(appointmentMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> getEmergencyAppointmentsByDateAndVetId(String dateString, Long vetId) {
        LOG.debug("Request to get emergency appointments for date: {} and vetId: {}", dateString, vetId);
        LocalDate date = parseDate(dateString);
        return findByDateAndAppointmentTypeAndVetId(date, "EMERGENCY", vetId);
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> getRegularAppointmentsByDateAndVetId(String dateString, Long vetId) {
        LOG.debug("Request to get regular appointments for date: {} and vetId: {}", dateString, vetId);
        LocalDate date = parseDate(dateString);
        return findByDateAndAppointmentTypeAndVetId(date, "NORMAL", vetId);
    }

    @Transactional(readOnly = true)
    public boolean checkVetAvailability(Long vetId, ZonedDateTime startTime, ZonedDateTime endTime) {
        LOG.debug("Request to check availability for vet: {} at startTime: {}", vetId, startTime);
        
        // Logic mới: chỉ cần không trùng giờ startTime và cách nhau ít nhất 1 tiếng
        List<Appointment> appointmentsInSameDay = appointmentRepository.findAppointmentsByVetAndDate(vetId, startTime);
        
        for (Appointment appointment : appointmentsInSameDay) {
            long hoursDiff = Math.abs(java.time.Duration.between(appointment.getTimeStart(), startTime).toHours());
            
            // Kiểm tra: trùng giờ (cùng hour) hoặc cách nhau < 1 tiếng
            if (appointment.getTimeStart().getHour() == startTime.getHour() || hoursDiff < 1) {
                return false;
            }
        }
        
        return true;
    }

    private LocalDate parseDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return LocalDate.now();
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateString.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new BadRequestAlertException("Invalid date format. Expected format: yyyy-MM-dd", "appointment", "invaliddate");
        }
    }
}


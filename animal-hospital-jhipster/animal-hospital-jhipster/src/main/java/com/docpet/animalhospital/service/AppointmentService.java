package com.docpet.animalhospital.service;

import com.docpet.animalhospital.domain.Appointment;
import com.docpet.animalhospital.domain.Owner;
import com.docpet.animalhospital.domain.Pet;
import com.docpet.animalhospital.domain.Vet;
import com.docpet.animalhospital.repository.AppointmentRepository;
import com.docpet.animalhospital.repository.OwnerRepository;
import com.docpet.animalhospital.repository.PetRepository;
import com.docpet.animalhospital.repository.VetRepository;
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

/**
 * Service Implementation for managing {@link com.docpet.animalhospital.domain.Appointment}.
 */
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

    /**
     * Save a appointment.
     *
     * @param appointmentDTO the entity to save.
     * @return the persisted entity.
     */
    public AppointmentDTO save(AppointmentDTO appointmentDTO) {
        LOG.debug("Request to save Appointment : {}", appointmentDTO);
        Appointment appointment = appointmentMapper.toEntity(appointmentDTO);
        appointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDto(appointment);
    }

    /**
     * Update a appointment.
     *
     * @param appointmentDTO the entity to save.
     * @return the persisted entity.
     */
    public AppointmentDTO update(AppointmentDTO appointmentDTO) {
        LOG.debug("Request to update Appointment : {}", appointmentDTO);
        Appointment appointment = appointmentMapper.toEntity(appointmentDTO);
        appointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDto(appointment);
    }

    /**
     * Partially update a appointment.
     *
     * @param appointmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AppointmentDTO> partialUpdate(AppointmentDTO appointmentDTO) {
        LOG.debug("Request to partially update Appointment : {}", appointmentDTO);

        return appointmentRepository
            .findById(appointmentDTO.getId())
            .map(existingAppointment -> {
                appointmentMapper.partialUpdate(existingAppointment, appointmentDTO);

                return existingAppointment;
            })
            .map(appointmentRepository::save)
            .map(appointmentMapper::toDto);
    }

    /**
     * Get all the appointments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AppointmentDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Appointments");
        return appointmentRepository.findAll(pageable).map(appointmentMapper::toDto);
    }

    /**
     * Get one appointment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppointmentDTO> findOne(Long id) {
        LOG.debug("Request to get Appointment : {}", id);
        return appointmentRepository.findById(id).map(appointmentMapper::toDto);
    }

    /**
     * Delete the appointment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Appointment : {}", id);
        appointmentRepository.deleteById(id);
    }

    /**
     * Create a new appointment with validation.
     *
     * @param appointmentDTO the appointment to create.
     * @param currentUserLogin the current user login.
     * @return the created appointment.
     */
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO, String currentUserLogin) {
        LOG.debug("Request to create Appointment for user: {}", currentUserLogin);

        // Validate and get current owner
        Owner currentOwner = ownerRepository.findByUser_Login(currentUserLogin)
            .orElseThrow(() -> new BadRequestAlertException("Owner profile not found", "appointment", "noowner"));

        // Validate pet belongs to current owner
        if (appointmentDTO.getPet() == null || appointmentDTO.getPet().getId() == null) {
            throw new BadRequestAlertException("Pet is required", "appointment", "petrequired");
        }

        Pet pet = petRepository.findById(appointmentDTO.getPet().getId())
            .orElseThrow(() -> new BadRequestAlertException("Pet not found", "appointment", "petnotfound"));

        if (!pet.getOwner().getId().equals(currentOwner.getId())) {
            throw new BadRequestAlertException("Pet does not belong to current owner", "appointment", "petnotowned");
        }

        // Validate vet exists
        if (appointmentDTO.getVet() == null || appointmentDTO.getVet().getId() == null) {
            throw new BadRequestAlertException("Vet is required", "appointment", "vetrequired");
        }

        Vet vet = vetRepository.findById(appointmentDTO.getVet().getId())
            .orElseThrow(() -> new BadRequestAlertException("Vet not found", "appointment", "vetnotfound"));

        // Validate appointment type
        if (!"EMERGENCY".equals(appointmentDTO.getAppointmentType()) && !"NORMAL".equals(appointmentDTO.getAppointmentType())) {
            throw new BadRequestAlertException("Invalid appointment type", "appointment", "invalidtype");
        }

        // Validate location type
        if (!"AT_HOME".equals(appointmentDTO.getLocationType()) && !"AT_CLINIC".equals(appointmentDTO.getLocationType())) {
            throw new BadRequestAlertException("Invalid location type", "appointment", "invalidlocation");
        }

        // Check for time conflicts
        List<Appointment> conflictingAppointments = appointmentRepository.findByVetAndTimeRange(
            vet.getId(), 
            appointmentDTO.getTimeStart(), 
            appointmentDTO.getTimeEnd()
        );

        if (!conflictingAppointments.isEmpty()) {
            throw new BadRequestAlertException("Vet is not available at this time", "appointment", "vetnotavailable");
        }

        // Set relationships
        appointmentDTO.setOwner(new com.docpet.animalhospital.service.dto.OwnerDTO(currentOwner));
        appointmentDTO.setStatus("PENDING"); // Default status

        Appointment appointment = appointmentMapper.toEntity(appointmentDTO);
        appointment = appointmentRepository.save(appointment);
        
        LOG.debug("Created Appointment: {}", appointment);
        return appointmentMapper.toDto(appointment);
    }

    /**
     * Get all appointments for current owner.
     *
     * @param currentUserLogin the current user login.
     * @return the list of appointments.
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> findAllForCurrentOwner(String currentUserLogin) {
        LOG.debug("Request to get all Appointments for owner: {}", currentUserLogin);
        List<Appointment> appointments = appointmentRepository.findByOwner_User_Login(currentUserLogin);
        return appointments.stream()
            .map(appointmentMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get all appointments for current vet.
     *
     * @param currentUserLogin the current user login.
     * @return the list of appointments.
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> findAllForCurrentVet(String currentUserLogin) {
        LOG.debug("Request to get all Appointments for vet: {}", currentUserLogin);
        List<Appointment> appointments = appointmentRepository.findByVet_User_Login(currentUserLogin);
        return appointments.stream()
            .map(appointmentMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get all appointments for a specific date (for vets to view all appointments in the day).
     *
     * @param dateString the date string in format yyyy-MM-dd. If null or empty, defaults to today.
     * @return the list of appointments for the specified date.
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAllAppointmentsByDateForVet(String dateString) {
        LOG.debug("Request to get all appointments for date: {} for vet", dateString);
        LocalDate date = parseDate(dateString);
        return findByDate(date);
    }

    /**
     * Get appointment history for a specific pet.
     *
     * @param petId the pet id.
     * @param currentUserLogin the current user login.
     * @return the list of appointments.
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getPetAppointmentHistory(Long petId, String currentUserLogin) {
        LOG.debug("Request to get appointment history for pet: {} by user: {}", petId, currentUserLogin);

        // Validate pet belongs to current owner
        Pet pet = petRepository.findById(petId)
            .orElseThrow(() -> new BadRequestAlertException("Pet not found", "appointment", "petnotfound"));

        Owner currentOwner = ownerRepository.findByUser_Login(currentUserLogin)
            .orElseThrow(() -> new BadRequestAlertException("Owner profile not found", "appointment", "noowner"));

        if (!pet.getOwner().getId().equals(currentOwner.getId())) {
            throw new BadRequestAlertException("Pet does not belong to current owner", "appointment", "petnotowned");
        }

        List<Appointment> appointments = appointmentRepository.findByPet_Id(petId);
        return appointments.stream()
            .map(appointmentMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Update appointment status (for vets).
     *
     * @param appointmentId the appointment id.
     * @param status the new status.
     * @param currentUserLogin the current user login.
     * @return the updated appointment.
     */
    public AppointmentDTO updateAppointmentStatus(Long appointmentId, String status, String currentUserLogin) {
        LOG.debug("Request to update appointment status: {} for appointment: {} by user: {}", status, appointmentId, currentUserLogin);

        Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found", "appointment", "notfound"));

        // Get current vet and set to appointment when approving/rejecting
        Vet currentVet = vetRepository.findByUser_Login(currentUserLogin)
            .orElseThrow(() -> new BadRequestAlertException("Vet profile not found", "appointment", "novet"));

        // Set vet to appointment when approving or rejecting
        if ("APPROVED".equals(status) || "REJECTED".equals(status)) {
            appointment.setVet(currentVet);
        }

        appointment.setStatus(status);
        appointment = appointmentRepository.save(appointment);
        
        return appointmentMapper.toDto(appointment);
    }

    /**
     * Get appointments by date and appointment type.
     *
     * @param date the date to search for appointments.
     * @param appointmentType the appointment type (EMERGENCY, REGULAR, etc.).
     * @return the list of appointments.
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> findByDateAndAppointmentType(LocalDate date, String appointmentType) {
        LOG.debug("Request to get appointments by date: {} and type: {}", date, appointmentType);
        List<Appointment> appointments = appointmentRepository.findByDateAndAppointmentType(date, appointmentType);
        return appointments.stream()
            .map(appointmentMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get appointments by date only.
     *
     * @param date the date to search for appointments.
     * @return the list of appointments.
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> findByDate(LocalDate date) {
        LOG.debug("Request to get appointments by date: {}", date);
        List<Appointment> appointments = appointmentRepository.findByDate(date);
        return appointments.stream()
            .map(appointmentMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get emergency appointments for a specific date.
     *
     * @param dateString the date string in format yyyy-MM-dd. If null or empty, defaults to today.
     * @return the list of emergency appointments for the specified date.
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getEmergencyAppointmentsByDate(String dateString) {
        LOG.debug("Request to get emergency appointments for date: {}", dateString);
        LocalDate date = parseDate(dateString);
        return findByDateAndAppointmentType(date, "EMERGENCY");
    }

    /**
     * Get regular appointments for a specific date.
     *
     * @param dateString the date string in format yyyy-MM-dd. If null or empty, defaults to today.
     * @return the list of regular appointments for the specified date.
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getRegularAppointmentsByDate(String dateString) {
        LOG.debug("Request to get regular appointments for date: {}", dateString);
        LocalDate date = parseDate(dateString);
        return findByDateAndAppointmentType(date, "REGULAR");
    }

    /**
     * Get appointments by date and appointment type for current vet.
     *
     * @param date the date to search for appointments.
     * @param appointmentType the appointment type (EMERGENCY, REGULAR, etc.).
     * @param currentUserLogin the current user login.
     * @return the list of appointments.
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> findByDateAndAppointmentTypeAndVet(LocalDate date, String appointmentType, String currentUserLogin) {
        LOG.debug("Request to get appointments by date: {}, type: {} and vet: {}", date, appointmentType, currentUserLogin);
        List<Appointment> appointments = appointmentRepository.findByDateAndAppointmentTypeAndVet(date, appointmentType, currentUserLogin);
        return appointments.stream()
            .map(appointmentMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get emergency appointments for a specific date for current vet.
     *
     * @param dateString the date string in format yyyy-MM-dd. If null or empty, defaults to today.
     * @param currentUserLogin the current user login.
     * @return the list of emergency appointments for the specified date.
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getEmergencyAppointmentsByDateForVet(String dateString, String currentUserLogin) {
        LOG.debug("Request to get emergency appointments for date: {} for vet: {}", dateString, currentUserLogin);
        LocalDate date = parseDate(dateString);
        return findByDateAndAppointmentTypeAndVet(date, "EMERGENCY", currentUserLogin);
    }

    /**
     * Get regular appointments for a specific date for current vet.
     *
     * @param dateString the date string in format yyyy-MM-dd. If null or empty, defaults to today.
     * @param currentUserLogin the current user login.
     * @return the list of regular appointments for the specified date.
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getRegularAppointmentsByDateForVet(String dateString, String currentUserLogin) {
        LOG.debug("Request to get regular appointments for date: {} for vet: {}", dateString, currentUserLogin);
        LocalDate date = parseDate(dateString);
        return findByDateAndAppointmentTypeAndVet(date, "REGULAR", currentUserLogin);
    }

    /**
     * Parse date string to LocalDate.
     *
     * @param dateString the date string in format yyyy-MM-dd.
     * @return LocalDate object. If dateString is null or empty, returns today's date.
     * @throws BadRequestAlertException if dateString format is invalid.
     */
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

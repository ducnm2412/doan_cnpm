package com.docpet.animalhospital.service;

import com.docpet.animalhospital.domain.LabTest;
import com.docpet.animalhospital.domain.Appointment;
import com.docpet.animalhospital.domain.Pet;
import com.docpet.animalhospital.domain.User;
import com.docpet.animalhospital.repository.LabTestRepository;
import com.docpet.animalhospital.repository.AppointmentRepository;
import com.docpet.animalhospital.repository.PetRepository;
import com.docpet.animalhospital.repository.UserRepository;
import com.docpet.animalhospital.service.dto.LabTestDTO;
import com.docpet.animalhospital.service.mapper.LabTestMapper;
import com.docpet.animalhospital.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link com.docpet.animalhospital.domain.LabTest}.
 */
@Service
@Transactional
public class LabTestService {

    private static final Logger LOG = LoggerFactory.getLogger(LabTestService.class);

    private final LabTestRepository labTestRepository;
    private final LabTestMapper labTestMapper;
    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public LabTestService(
        LabTestRepository labTestRepository,
        LabTestMapper labTestMapper,
        AppointmentRepository appointmentRepository,
        PetRepository petRepository,
        UserRepository userRepository
    ) {
        this.labTestRepository = labTestRepository;
        this.labTestMapper = labTestMapper;
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a labTest.
     *
     * @param labTestDTO the entity to save.
     * @return the persisted entity.
     */
    public LabTestDTO save(LabTestDTO labTestDTO) {
        LOG.debug("Request to save LabTest : {}", labTestDTO);
        LabTest labTest = labTestMapper.toEntity(labTestDTO);
        labTest = labTestRepository.save(labTest);
        return labTestMapper.toDto(labTest);
    }

    /**
     * Update a labTest.
     *
     * @param labTestDTO the entity to save.
     * @return the persisted entity.
     */
    public LabTestDTO update(LabTestDTO labTestDTO) {
        LOG.debug("Request to update LabTest : {}", labTestDTO);
        LabTest labTest = labTestMapper.toEntity(labTestDTO);
        labTest = labTestRepository.save(labTest);
        return labTestMapper.toDto(labTest);
    }

    /**
     * Partially update a labTest.
     *
     * @param labTestDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LabTestDTO> partialUpdate(LabTestDTO labTestDTO) {
        LOG.debug("Request to partially update LabTest : {}", labTestDTO);

        return labTestRepository
            .findById(labTestDTO.getId())
            .map(existingLabTest -> {
                labTestMapper.partialUpdate(existingLabTest, labTestDTO);
                return existingLabTest;
            })
            .map(labTestRepository::save)
            .map(labTestMapper::toDto);
    }

    /**
     * Get all the labTests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LabTestDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all LabTests");
        return labTestRepository.findAll(pageable).map(labTestMapper::toDto);
    }

    /**
     * Get all the labTests with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LabTestDTO> findAllWithEagerRelationships() {
        LOG.debug("Request to get all LabTests with eager relationships");
        return labTestRepository.findAll().stream()
            .map(labTestMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get one labTest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LabTestDTO> findOne(Long id) {
        LOG.debug("Request to get LabTest : {}", id);
        return labTestRepository.findOneWithEagerRelationships(id)
            .map(labTestMapper::toDto);
    }

    /**
     * Delete the labTest by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete LabTest : {}", id);
        labTestRepository.deleteById(id);
    }

    /**
     * Get all labTests for a specific appointment.
     *
     * @param appointmentId the appointment id.
     * @return the list of labTests.
     */
    @Transactional(readOnly = true)
    public List<LabTestDTO> findByAppointmentId(Long appointmentId) {
        LOG.debug("Request to get LabTests for appointment: {}", appointmentId);
        return labTestRepository.findByAppointment_Id(appointmentId).stream()
            .map(labTestMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get all labTests for a specific pet.
     *
     * @param petId the pet id.
     * @return the list of labTests.
     */
    @Transactional(readOnly = true)
    public List<LabTestDTO> findByPetId(Long petId) {
        LOG.debug("Request to get LabTests for pet: {}", petId);
        return labTestRepository.findByPet_Id(petId).stream()
            .map(labTestMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get all labTests assigned to a specific user.
     *
     * @param assignedToLogin the assigned user login.
     * @return the list of labTests.
     */
    @Transactional(readOnly = true)
    public List<LabTestDTO> findByAssignedToLogin(String assignedToLogin) {
        LOG.debug("Request to get LabTests assigned to: {}", assignedToLogin);
        return labTestRepository.findByAssignedTo_Login(assignedToLogin).stream()
            .map(labTestMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get all labTests requested by a specific user.
     *
     * @param requestedByLogin the requester user login.
     * @return the list of labTests.
     */
    @Transactional(readOnly = true)
    public List<LabTestDTO> findByRequestedByLogin(String requestedByLogin) {
        LOG.debug("Request to get LabTests requested by: {}", requestedByLogin);
        return labTestRepository.findByRequestedBy_Login(requestedByLogin).stream()
            .map(labTestMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Create a new lab test.
     *
     * @param appointmentId the appointment id.
     * @param testName the test name.
     * @param testType the test type.
     * @param description the description.
     * @param requestedByLogin the requester login.
     * @param assignedToLogin the assigned user login (optional).
     * @return the created labTest.
     */
    public LabTestDTO createLabTest(
        Long appointmentId,
        String testName,
        String testType,
        String description,
        String requestedByLogin,
        String assignedToLogin
    ) {
        LOG.debug("Request to create LabTest for appointment: {} with test: {}", appointmentId, testName);

        // Validate appointment exists
        Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found", "labTest", "appointmentnotfound"));

        // Validate requester exists
        User requestedBy = userRepository.findOneByLogin(requestedByLogin)
            .orElseThrow(() -> new BadRequestAlertException("Requester user not found", "labTest", "requesternotfound"));

        // Validate assigned user exists if provided
        User assignedTo = null;
        if (assignedToLogin != null && !assignedToLogin.trim().isEmpty()) {
            assignedTo = userRepository.findOneByLogin(assignedToLogin)
                .orElseThrow(() -> new BadRequestAlertException("Assigned user not found", "labTest", "assignednotfound"));
        }

        // Create lab test
        LabTest labTest = new LabTest();
        labTest.setAppointment(appointment);
        labTest.setPet(appointment.getPet());
        labTest.setTestName(testName);
        labTest.setTestType(testType);
        labTest.setDescription(description);
        labTest.setStatus("REQUESTED");
        labTest.setRequestedBy(requestedBy);
        labTest.setAssignedTo(assignedTo);
        labTest.setRequestedDate(ZonedDateTime.now());

        labTest = labTestRepository.save(labTest);
        return labTestMapper.toDto(labTest);
    }

    /**
     * Update lab test status.
     *
     * @param labTestId the lab test id.
     * @param status the new status.
     * @param result the test result (optional).
     * @param notes the notes (optional).
     * @param completedByLogin the user who completed the test.
     * @return the updated labTest.
     */
    public LabTestDTO updateLabTestStatus(
        Long labTestId,
        String status,
        String result,
        String notes,
        String completedByLogin
    ) {
        LOG.debug("Request to update LabTest status: {} for test: {}", status, labTestId);

        LabTest labTest = labTestRepository.findById(labTestId)
            .orElseThrow(() -> new BadRequestAlertException("LabTest not found", "labTest", "testnotfound"));

        labTest.setStatus(status);
        if (result != null) {
            labTest.setResult(result);
        }
        if (notes != null) {
            labTest.setNotes(notes);
        }
        if ("COMPLETED".equals(status)) {
            labTest.setCompletedDate(ZonedDateTime.now());
        }
        if ("IN_PROGRESS".equals(status)) {
            labTest.setSampleCollectedDate(ZonedDateTime.now());
        }

        labTest = labTestRepository.save(labTest);
        return labTestMapper.toDto(labTest);
    }

    /**
     * Assign lab test to assistant.
     *
     * @param labTestId the lab test id.
     * @param assignedToLogin the assigned user login.
     * @return the updated labTest.
     */
    public LabTestDTO assignLabTest(Long labTestId, String assignedToLogin) {
        LOG.debug("Request to assign LabTest: {} to: {}", labTestId, assignedToLogin);

        LabTest labTest = labTestRepository.findById(labTestId)
            .orElseThrow(() -> new BadRequestAlertException("LabTest not found", "labTest", "testnotfound"));

        User assignedTo = userRepository.findOneByLogin(assignedToLogin)
            .orElseThrow(() -> new BadRequestAlertException("Assigned user not found", "labTest", "assignednotfound"));

        labTest.setAssignedTo(assignedTo);
        labTest.setStatus("ASSIGNED");

        labTest = labTestRepository.save(labTest);
        return labTestMapper.toDto(labTest);
    }
}




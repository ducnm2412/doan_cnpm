package com.docpet.animalhospital.service;

import com.docpet.animalhospital.domain.AppointmentAction;
import com.docpet.animalhospital.domain.Appointment;
import com.docpet.animalhospital.domain.User;
import com.docpet.animalhospital.repository.AppointmentActionRepository;
import com.docpet.animalhospital.repository.AppointmentRepository;
import com.docpet.animalhospital.repository.UserRepository;
import com.docpet.animalhospital.service.dto.AppointmentActionDTO;
import com.docpet.animalhospital.service.mapper.AppointmentActionMapper;
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
 * Service Implementation for managing {@link com.docpet.animalhospital.domain.AppointmentAction}.
 */
@Service
@Transactional
public class AppointmentActionService {

    private static final Logger LOG = LoggerFactory.getLogger(AppointmentActionService.class);

    private final AppointmentActionRepository appointmentActionRepository;
    private final AppointmentActionMapper appointmentActionMapper;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public AppointmentActionService(
        AppointmentActionRepository appointmentActionRepository,
        AppointmentActionMapper appointmentActionMapper,
        AppointmentRepository appointmentRepository,
        UserRepository userRepository
    ) {
        this.appointmentActionRepository = appointmentActionRepository;
        this.appointmentActionMapper = appointmentActionMapper;
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a appointmentAction.
     *
     * @param appointmentActionDTO the entity to save.
     * @return the persisted entity.
     */
    public AppointmentActionDTO save(AppointmentActionDTO appointmentActionDTO) {
        LOG.debug("Request to save AppointmentAction : {}", appointmentActionDTO);
        AppointmentAction appointmentAction = appointmentActionMapper.toEntity(appointmentActionDTO);
        appointmentAction = appointmentActionRepository.save(appointmentAction);
        return appointmentActionMapper.toDto(appointmentAction);
    }

    /**
     * Update a appointmentAction.
     *
     * @param appointmentActionDTO the entity to save.
     * @return the persisted entity.
     */
    public AppointmentActionDTO update(AppointmentActionDTO appointmentActionDTO) {
        LOG.debug("Request to update AppointmentAction : {}", appointmentActionDTO);
        AppointmentAction appointmentAction = appointmentActionMapper.toEntity(appointmentActionDTO);
        appointmentAction = appointmentActionRepository.save(appointmentAction);
        return appointmentActionMapper.toDto(appointmentAction);
    }

    /**
     * Partially update a appointmentAction.
     *
     * @param appointmentActionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AppointmentActionDTO> partialUpdate(AppointmentActionDTO appointmentActionDTO) {
        LOG.debug("Request to partially update AppointmentAction : {}", appointmentActionDTO);

        return appointmentActionRepository
            .findById(appointmentActionDTO.getId())
            .map(existingAppointmentAction -> {
                appointmentActionMapper.partialUpdate(existingAppointmentAction, appointmentActionDTO);
                return existingAppointmentAction;
            })
            .map(appointmentActionRepository::save)
            .map(appointmentActionMapper::toDto);
    }

    /**
     * Get all the appointmentActions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AppointmentActionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all AppointmentActions");
        return appointmentActionRepository.findAll(pageable).map(appointmentActionMapper::toDto);
    }

    /**
     * Get all the appointmentActions with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AppointmentActionDTO> findAllWithEagerRelationships() {
        LOG.debug("Request to get all AppointmentActions with eager relationships");
        return appointmentActionRepository.findAll().stream()
            .map(appointmentActionMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get one appointmentAction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppointmentActionDTO> findOne(Long id) {
        LOG.debug("Request to get AppointmentAction : {}", id);
        return appointmentActionRepository.findOneWithEagerRelationships(id)
            .map(appointmentActionMapper::toDto);
    }

    /**
     * Delete the appointmentAction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete AppointmentAction : {}", id);
        appointmentActionRepository.deleteById(id);
    }

    /**
     * Get all appointmentActions for a specific appointment.
     *
     * @param appointmentId the appointment id.
     * @return the list of appointmentActions.
     */
    @Transactional(readOnly = true)
    public List<AppointmentActionDTO> findByAppointmentId(Long appointmentId) {
        LOG.debug("Request to get AppointmentActions for appointment: {}", appointmentId);
        return appointmentActionRepository.findByAppointment_Id(appointmentId).stream()
            .map(appointmentActionMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get all appointmentActions assigned to a specific user.
     *
     * @param assignedToLogin the assigned user login.
     * @return the list of appointmentActions.
     */
    @Transactional(readOnly = true)
    public List<AppointmentActionDTO> findByAssignedToLogin(String assignedToLogin) {
        LOG.debug("Request to get AppointmentActions assigned to: {}", assignedToLogin);
        return appointmentActionRepository.findByAssignedTo_Login(assignedToLogin).stream()
            .map(appointmentActionMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get all appointmentActions assigned to a specific user with a specific status.
     *
     * @param assignedToLogin the assigned user login.
     * @param status the status filter (PENDING, COMPLETED, CANCELLED).
     * @return the list of appointmentActions.
     */
    @Transactional(readOnly = true)
    public List<AppointmentActionDTO> findByAssignedToLoginAndStatus(String assignedToLogin, String status) {
        LOG.debug("Request to get AppointmentActions assigned to: {} with status: {}", assignedToLogin, status);
        return appointmentActionRepository.findByAssignedTo_LoginAndStatus(assignedToLogin, status).stream()
            .map(appointmentActionMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get all appointmentActions assigned to a specific user by user ID.
     *
     * @param assignedToId the assigned user ID.
     * @return the list of appointmentActions.
     */
    @Transactional(readOnly = true)
    public List<AppointmentActionDTO> findByAssignedToId(Long assignedToId) {
        LOG.debug("Request to get AppointmentActions assigned to user ID: {}", assignedToId);
        return appointmentActionRepository.findByAssignedTo_Id(assignedToId).stream()
            .map(appointmentActionMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get all appointmentActions assigned to a specific user by user ID with a specific status.
     *
     * @param assignedToId the assigned user ID.
     * @param status the status filter (PENDING, COMPLETED, CANCELLED).
     * @return the list of appointmentActions.
     */
    @Transactional(readOnly = true)
    public List<AppointmentActionDTO> findByAssignedToIdAndStatus(Long assignedToId, String status) {
        LOG.debug("Request to get AppointmentActions assigned to user ID: {} with status: {}", assignedToId, status);
        return appointmentActionRepository.findByAssignedTo_IdAndStatus(assignedToId, status).stream()
            .map(appointmentActionMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get all appointmentActions created by a specific user.
     *
     * @param createdByLogin the creator user login.
     * @return the list of appointmentActions.
     */
    @Transactional(readOnly = true)
    public List<AppointmentActionDTO> findByCreatedByLogin(String createdByLogin) {
        LOG.debug("Request to get AppointmentActions created by: {}", createdByLogin);
        return appointmentActionRepository.findByCreatedBy_Login(createdByLogin).stream()
            .map(appointmentActionMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Create a new appointment action.
     *
     * @param appointmentId the appointment id.
     * @param actionType the action type.
     * @param status the status.
     * @param description the description.
     * @param notes the notes.
     * @param createdByLogin the creator login.
     * @param assignedToLogin the assigned user login (optional).
     * @return the created appointmentAction.
     */
    public AppointmentActionDTO createAppointmentAction(
        Long appointmentId,
        String actionType,
        String status,
        String description,
        String notes,
        String createdByLogin,
        String assignedToLogin
    ) {
        LOG.debug("Request to create AppointmentAction for appointment: {} with action: {}", appointmentId, actionType);

        // Validate appointment exists
        Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found", "appointmentAction", "appointmentnotfound"));

        // Validate creator exists
        User createdBy = userRepository.findOneByLogin(createdByLogin)
            .orElseThrow(() -> new BadRequestAlertException("Creator user not found", "appointmentAction", "creatornotfound"));

        // Validate assigned user exists if provided
        User assignedTo = null;
        if (assignedToLogin != null && !assignedToLogin.trim().isEmpty()) {
            assignedTo = userRepository.findOneByLogin(assignedToLogin)
                .orElseThrow(() -> new BadRequestAlertException("Assigned user not found", "appointmentAction", "assignednotfound"));
        }

        // Create appointment action
        AppointmentAction appointmentAction = new AppointmentAction();
        appointmentAction.setAppointment(appointment);
        appointmentAction.setActionType(actionType);
        appointmentAction.setStatus(status);
        appointmentAction.setDescription(description);
        appointmentAction.setNotes(notes);
        appointmentAction.setCreatedBy(createdBy);
        appointmentAction.setAssignedTo(assignedTo);
        appointmentAction.setScheduledTime(ZonedDateTime.now());

        appointmentAction = appointmentActionRepository.save(appointmentAction);
        return appointmentActionMapper.toDto(appointmentAction);
    }

    /**
     * Update appointment action status.
     *
     * @param actionId the action id.
     * @param status the new status.
     * @param completedByLogin the user who completed the action.
     * @return the updated appointmentAction.
     */
    public AppointmentActionDTO updateActionStatus(Long actionId, String status, String completedByLogin) {
        LOG.debug("Request to update AppointmentAction status: {} for action: {}", status, actionId);

        AppointmentAction appointmentAction = appointmentActionRepository.findById(actionId)
            .orElseThrow(() -> new BadRequestAlertException("AppointmentAction not found", "appointmentAction", "actionnotfound"));

        appointmentAction.setStatus(status);
        if ("COMPLETED".equals(status)) {
            appointmentAction.setCompletedTime(ZonedDateTime.now());
        }

        appointmentAction = appointmentActionRepository.save(appointmentAction);
        return appointmentActionMapper.toDto(appointmentAction);
    }
}




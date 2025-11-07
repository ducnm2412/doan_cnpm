package com.docpet.animalhospital.web.rest;

import com.docpet.animalhospital.repository.AppointmentRepository;
import com.docpet.animalhospital.repository.UserRepository;
import com.docpet.animalhospital.security.AuthoritiesConstants;
import com.docpet.animalhospital.security.SecurityUtils;
import com.docpet.animalhospital.service.AppointmentActionService;
import com.docpet.animalhospital.service.AppointmentService;
import com.docpet.animalhospital.service.dto.AppointmentActionDTO;
import com.docpet.animalhospital.service.dto.AppointmentDTO;
import com.docpet.animalhospital.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.docpet.animalhospital.domain.Appointment}.
 */
@RestController
@RequestMapping("/api/appointments")
public class AppointmentResource {

    private static final Logger LOG = LoggerFactory.getLogger(AppointmentResource.class);

    private static final String ENTITY_NAME = "appointment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppointmentService appointmentService;

    private final AppointmentRepository appointmentRepository;

    private final AppointmentActionService appointmentActionService;

    private final UserRepository userRepository;

    public AppointmentResource(
        AppointmentService appointmentService,
        AppointmentRepository appointmentRepository,
        AppointmentActionService appointmentActionService,
        UserRepository userRepository
    ) {
        this.appointmentService = appointmentService;
        this.appointmentRepository = appointmentRepository;
        this.appointmentActionService = appointmentActionService;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /appointments} : Create a new appointment.
     *
     * @param appointmentDTO the appointmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appointmentDTO, or with status {@code 400 (Bad Request)} if the appointment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AppointmentDTO> createAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) throws URISyntaxException {
        LOG.debug("REST request to save Appointment : {}", appointmentDTO);
        if (appointmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new appointment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));
        
        appointmentDTO = appointmentService.createAppointment(appointmentDTO, currentUserLogin);
        return ResponseEntity.created(new URI("/api/appointments/" + appointmentDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, appointmentDTO.getId().toString()))
            .body(appointmentDTO);
    }

    /**
     * {@code PUT  /appointments/:id} : Updates an existing appointment.
     *
     * @param id the id of the appointmentDTO to save.
     * @param appointmentDTO the appointmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appointmentDTO,
     * or with status {@code 400 (Bad Request)} if the appointmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appointmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AppointmentDTO appointmentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Appointment : {}, {}", id, appointmentDTO);
        if (appointmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appointmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appointmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        appointmentDTO = appointmentService.update(appointmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appointmentDTO.getId().toString()))
            .body(appointmentDTO);
    }

    /**
     * {@code PATCH  /appointments/:id} : Partial updates given fields of an existing appointment, field will ignore if it is null
     *
     * @param id the id of the appointmentDTO to save.
     * @param appointmentDTO the appointmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appointmentDTO,
     * or with status {@code 400 (Bad Request)} if the appointmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appointmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appointmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppointmentDTO> partialUpdateAppointment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AppointmentDTO appointmentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Appointment partially : {}, {}", id, appointmentDTO);
        if (appointmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appointmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appointmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppointmentDTO> result = appointmentService.partialUpdate(appointmentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appointmentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /appointments} : get all the appointments for current user.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appointments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        LOG.debug("REST request to get all Appointments for current user");
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));
        
        List<AppointmentDTO> appointments = appointmentService.findAllForCurrentOwner(currentUserLogin);
        return ResponseEntity.ok().body(appointments);
    }

    /**
     * {@code GET  /appointments/:id} : get the "id" appointment.
     *
     * @param id the id of the appointmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appointmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointment(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Appointment : {}", id);
        Optional<AppointmentDTO> appointmentDTO = appointmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appointmentDTO);
    }

    /**
     * {@code DELETE  /appointments/:id} : delete the "id" appointment.
     *
     * @param id the id of the appointmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Appointment : {}", id);
        appointmentService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /appointments/vet} : get all appointments for a specific date (for vets to view all appointments in the day).
     *
     * @param date the date to search for appointments (format: yyyy-MM-dd). If not provided, defaults to today.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appointments in body.
     */
    @GetMapping("/vet")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.DOCTOR + "')")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointmentsForVet(
        @RequestParam(value = "date", required = false) String date) {
        LOG.debug("REST request to get all appointments for date: {} for vet", date);
        
        List<AppointmentDTO> appointments = appointmentService.getAllAppointmentsByDateForVet(date);
        return ResponseEntity.ok().body(appointments);
    }

    /**
     * {@code GET  /appointments/pet/{petId}/history} : get appointment history for a specific pet.
     *
     * @param petId the pet id.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appointments in body.
     */
    @GetMapping("/pet/{petId}/history")
    public ResponseEntity<List<AppointmentDTO>> getPetAppointmentHistory(@PathVariable("petId") Long petId) {
        LOG.debug("REST request to get appointment history for pet: {}", petId);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));
        
        List<AppointmentDTO> appointments = appointmentService.getPetAppointmentHistory(petId, currentUserLogin);
        return ResponseEntity.ok().body(appointments);
    }

    /**
     * {@code PATCH  /appointments/{id}/status} : update appointment status (for vets).
     *
     * @param id the id of the appointment.
     * @param status the new status.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appointmentDTO.
     */
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.DOCTOR + "')")
    public ResponseEntity<AppointmentDTO> updateAppointmentStatus(
        @PathVariable("id") Long id,
        @RequestBody String status
    ) {
        LOG.debug("REST request to update appointment status: {} for appointment: {}", status, id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));
        
        AppointmentDTO appointmentDTO = appointmentService.updateAppointmentStatus(id, status, currentUserLogin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .body(appointmentDTO);
    }

    /**
     * {@code GET  /appointments/emergency} : get all emergency appointments for a specific date.
     *
     * @param date the date to search for appointments (format: yyyy-MM-dd). If not provided, defaults to today.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emergency appointments in body.
     */
    @GetMapping("/emergency")
    public ResponseEntity<List<AppointmentDTO>> getEmergencyAppointmentsByDate(
        @RequestParam(value = "date", required = false) String date) {
        LOG.debug("REST request to get emergency appointments for date: {}", date);
        List<AppointmentDTO> appointments = appointmentService.getEmergencyAppointmentsByDate(date);
        return ResponseEntity.ok().body(appointments);
    }

    /**
     * {@code GET  /appointments/regular} : get all regular appointments for a specific date.
     *
     * @param date the date to search for appointments (format: yyyy-MM-dd). If not provided, defaults to today.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regular appointments in body.
     */
    @GetMapping("/regular")
    public ResponseEntity<List<AppointmentDTO>> getRegularAppointmentsByDate(
        @RequestParam(value = "date", required = false) String date) {
        LOG.debug("REST request to get regular appointments for date: {}", date);
        List<AppointmentDTO> appointments = appointmentService.getRegularAppointmentsByDate(date);
        return ResponseEntity.ok().body(appointments);
    }

    /**
     * {@code GET  /appointments/vet/emergency} : get all emergency appointments for a specific date for current vet.
     *
     * @param date the date to search for appointments (format: yyyy-MM-dd). If not provided, defaults to today.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emergency appointments in body.
     */
    @GetMapping("/vet/emergency")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.DOCTOR + "')")
    public ResponseEntity<List<AppointmentDTO>> getEmergencyAppointmentsByDateForVet(
        @RequestParam(value = "date", required = false) String date) {
        LOG.debug("REST request to get emergency appointments for date: {} for current vet", date);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));
        
        List<AppointmentDTO> appointments = appointmentService.getEmergencyAppointmentsByDateForVet(date, currentUserLogin);
        return ResponseEntity.ok().body(appointments);
    }

    /**
     * {@code GET  /appointments/vet/regular} : get all regular appointments for a specific date for current vet.
     *
     * @param date the date to search for appointments (format: yyyy-MM-dd). If not provided, defaults to today.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regular appointments in body.
     */
    @GetMapping("/vet/regular")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.DOCTOR + "')")
    public ResponseEntity<List<AppointmentDTO>> getRegularAppointmentsByDateForVet(
        @RequestParam(value = "date", required = false) String date) {
        LOG.debug("REST request to get regular appointments for date: {} for current vet", date);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));
        
        List<AppointmentDTO> appointments = appointmentService.getRegularAppointmentsByDateForVet(date, currentUserLogin);
        return ResponseEntity.ok().body(appointments);
    }

    /**
     * {@code GET  /appointments/assistant/assigned} : get all appointments assigned to current assistant.
     *
     * @param status optional status filter (PENDING, COMPLETED, CANCELLED). If not provided, returns all.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appointment actions in body.
     */
    @GetMapping("/assistant/assigned")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ASSISTANT + "')")
    public ResponseEntity<List<AppointmentActionDTO>> getMyAssignedAppointments(
        @RequestParam(value = "status", required = false) String status
    ) {
        LOG.debug("REST request to get assigned appointments for current assistant with status: {}", status);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        Long currentUserId = userRepository.findOneByLogin(currentUserLogin)
            .map(user -> user.getId())
            .orElseThrow(() -> new BadRequestAlertException("User not found", ENTITY_NAME, "usernotfound"));

        List<AppointmentActionDTO> actions;
        if (status != null && !status.trim().isEmpty()) {
            actions = appointmentActionService.findByAssignedToIdAndStatus(currentUserId, status);
        } else {
            actions = appointmentActionService.findByAssignedToId(currentUserId);
        }

        return ResponseEntity.ok().body(actions);
    }

    /**
     * {@code GET  /appointments/assistant/assigned/pending} : get all pending appointments assigned to current assistant.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pending appointment actions in body.
     */
    @GetMapping("/assistant/assigned/pending")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ASSISTANT + "')")
    public ResponseEntity<List<AppointmentActionDTO>> getMyPendingAssignments() {
        LOG.debug("REST request to get pending assigned appointments for current assistant");
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        Long currentUserId = userRepository.findOneByLogin(currentUserLogin)
            .map(user -> user.getId())
            .orElseThrow(() -> new BadRequestAlertException("User not found", ENTITY_NAME, "usernotfound"));

        List<AppointmentActionDTO> actions = appointmentActionService.findByAssignedToIdAndStatus(
            currentUserId,
            "PENDING"
        );

        return ResponseEntity.ok().body(actions);
    }
}

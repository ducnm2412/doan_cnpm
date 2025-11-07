package com.docpet.animalhospital.web.rest;

import com.docpet.animalhospital.domain.Appointment;
import com.docpet.animalhospital.repository.AppointmentRepository;
import com.docpet.animalhospital.repository.UserRepository;
import com.docpet.animalhospital.security.AuthoritiesConstants;
import com.docpet.animalhospital.security.SecurityUtils;
import com.docpet.animalhospital.service.AppointmentService;
import com.docpet.animalhospital.service.AppointmentActionService;
import com.docpet.animalhospital.service.LabTestService;
import com.docpet.animalhospital.service.dto.AppointmentActionDTO;
import com.docpet.animalhospital.service.dto.AppointmentDTO;
import com.docpet.animalhospital.service.dto.LabTestDTO;
import com.docpet.animalhospital.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing vet appointment workflow.
 */
@RestController
@RequestMapping("/api/vet")
@PreAuthorize("hasAuthority('" + AuthoritiesConstants.DOCTOR + "')")
public class VetWorkflowResource {

    private static final Logger LOG = LoggerFactory.getLogger(VetWorkflowResource.class);

    private static final String ENTITY_NAME = "appointment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppointmentService appointmentService;
    private final AppointmentActionService appointmentActionService;
    private final LabTestService labTestService;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public VetWorkflowResource(
        AppointmentService appointmentService,
        AppointmentActionService appointmentActionService,
        LabTestService labTestService,
        AppointmentRepository appointmentRepository,
        UserRepository userRepository
    ) {
        this.appointmentService = appointmentService;
        this.appointmentActionService = appointmentActionService;
        this.labTestService = labTestService;
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    /**
     * {@code GET  /vet/appointments/{id}/detail} : get appointment detail for vet workflow.
     *
     * @param id the id of the appointment.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appointment detail.
     */
    @GetMapping("/appointments/{id}/detail")
    public ResponseEntity<AppointmentDTO> getAppointmentDetail(@PathVariable("id") Long id) {
        LOG.debug("REST request to get appointment detail for vet workflow: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        Optional<AppointmentDTO> appointmentDTO = appointmentService.findOne(id);
        if (appointmentDTO.isPresent()) {
            Appointment appointment = appointmentRepository.findById(id).orElse(null);
            if (appointment != null && appointment.getVet() != null && 
                appointment.getVet().getUser().getLogin().equals(currentUserLogin)) {
                return ResponseEntity.ok().body(appointmentDTO.get());
            } else {
                throw new BadRequestAlertException("You can only view your own appointments", ENTITY_NAME, "notauthorized");
            }
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * {@code POST  /vet/appointments/{id}/approve} : approve appointment.
     *
     * @param id the id of the appointment.
     * @param notes optional notes for approval.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appointment.
     */
    @PostMapping("/appointments/{id}/approve")
    public ResponseEntity<AppointmentDTO> approveAppointment(
        @PathVariable("id") Long id,
        @RequestBody(required = false) String notes) {
        LOG.debug("REST request to approve appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        AppointmentDTO appointmentDTO = appointmentService.updateAppointmentStatus(id, "APPROVED", currentUserLogin);
        
        // Create appointment action
        appointmentActionService.createAppointmentAction(id, "APPROVE", "COMPLETED", "Appointment approved", notes, currentUserLogin, null);
        
        return ResponseEntity.ok().body(appointmentDTO);
    }

    /**
     * {@code POST  /vet/appointments/{id}/reject} : reject appointment.
     *
     * @param id the id of the appointment.
     * @param notes reason for rejection.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appointment.
     */
    @PostMapping("/appointments/{id}/reject")
    public ResponseEntity<AppointmentDTO> rejectAppointment(
        @PathVariable("id") Long id,
        @RequestBody String notes) {
        LOG.debug("REST request to reject appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        AppointmentDTO appointmentDTO = appointmentService.updateAppointmentStatus(id, "REJECTED", currentUserLogin);
        
        // Create appointment action
        appointmentActionService.createAppointmentAction(id, "REJECT", "COMPLETED", "Appointment rejected", notes, currentUserLogin, null);
        
        return ResponseEntity.ok().body(appointmentDTO);
    }

    /**
     * {@code POST  /vet/appointments/{id}/reschedule} : reschedule appointment.
     *
     * @param id the id of the appointment.
     * @param rescheduleRequest the new time and notes.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appointment.
     */
    @PostMapping("/appointments/{id}/reschedule")
    public ResponseEntity<AppointmentDTO> rescheduleAppointment(
        @PathVariable("id") Long id,
        @Valid @RequestBody RescheduleRequest rescheduleRequest) {
        LOG.debug("REST request to reschedule appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        // Update appointment time
        AppointmentDTO appointmentDTO = appointmentService.findOne(id)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found", ENTITY_NAME, "notfound"));
        
        appointmentDTO.setTimeStart(rescheduleRequest.getNewTimeStart());
        appointmentDTO.setTimeEnd(rescheduleRequest.getNewTimeEnd());
        appointmentDTO.setStatus("RESCHEDULED");
        
        AppointmentDTO updatedAppointment = appointmentService.update(appointmentDTO);
        
        // Create appointment action
        appointmentActionService.createAppointmentAction(id, "RESCHEDULE", "COMPLETED", "Appointment rescheduled", rescheduleRequest.getNotes(), currentUserLogin, null);
        
        return ResponseEntity.ok().body(updatedAppointment);
    }

    /**
     * {@code POST  /vet/appointments/{id}/assign-assistant} : assign assistant for sample collection.
     *
     * @param id the id of the appointment.
     * @param assignRequest the assignment details.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the created action.
     */
    @PostMapping("/appointments/{id}/assign-assistant")
    public ResponseEntity<AppointmentActionDTO> assignAssistant(
        @PathVariable("id") Long id,
        @Valid @RequestBody AssignAssistantRequest assignRequest) {
        LOG.debug("REST request to assign assistant for appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        // Validate assistant exists and has ASSISTANT role
        userRepository.findOneByLogin(assignRequest.getAssistantLogin())
            .orElseThrow(() -> new BadRequestAlertException("Assistant not found", ENTITY_NAME, "assistantnotfound"));

        // Create appointment action
        AppointmentActionDTO actionDTO = appointmentActionService.createAppointmentAction(
            id, 
            "ASSIGN_ASSISTANT", 
            "PENDING", 
            "Assistant assigned for sample collection", 
            assignRequest.getNotes(), 
            currentUserLogin,
            assignRequest.getAssistantLogin()
        );
        
        return ResponseEntity.ok().body(actionDTO);
    }

    /**
     * {@code POST  /vet/appointments/{id}/request-home-visit} : request home visit.
     *
     * @param id the id of the appointment.
     * @param homeVisitRequest the home visit details.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the created action.
     */
    @PostMapping("/appointments/{id}/request-home-visit")
    public ResponseEntity<AppointmentActionDTO> requestHomeVisit(
        @PathVariable("id") Long id,
        @Valid @RequestBody HomeVisitRequest homeVisitRequest) {
        LOG.debug("REST request to request home visit for appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        // Update appointment location type
        AppointmentDTO appointmentDTO = appointmentService.findOne(id)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found", ENTITY_NAME, "notfound"));
        
        appointmentDTO.setLocationType("AT_HOME");
        appointmentService.update(appointmentDTO);

        // Create appointment action
        AppointmentActionDTO actionDTO = appointmentActionService.createAppointmentAction(
            id, 
            "REQUEST_HOME_VISIT", 
            "PENDING", 
            "Home visit requested", 
            homeVisitRequest.getNotes(), 
            currentUserLogin,
            null
        );
        
        return ResponseEntity.ok().body(actionDTO);
    }

    /**
     * {@code POST  /vet/appointments/{id}/request-lab-test} : request lab test.
     *
     * @param id the id of the appointment.
     * @param labTestRequest the lab test details.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the created lab test.
     */
    @PostMapping("/appointments/{id}/request-lab-test")
    public ResponseEntity<LabTestDTO> requestLabTest(
        @PathVariable("id") Long id,
        @Valid @RequestBody LabTestRequest labTestRequest) {
        LOG.debug("REST request to request lab test for appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        // Create lab test
        LabTestDTO labTestDTO = labTestService.createLabTest(
            id, 
            labTestRequest.getTestName(), 
            labTestRequest.getTestType(), 
            labTestRequest.getDescription(), 
            currentUserLogin,
            null
        );
        
        return ResponseEntity.ok().body(labTestDTO);
    }

    /**
     * {@code GET  /vet/appointments/{id}/actions} : get all actions for appointment.
     *
     * @param id the id of the appointment.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of actions.
     */
    @GetMapping("/appointments/{id}/actions")
    public ResponseEntity<List<AppointmentActionDTO>> getAppointmentActions(@PathVariable("id") Long id) {
        LOG.debug("REST request to get actions for appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        // Validate appointment belongs to current vet
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found", ENTITY_NAME, "notfound"));
        
        if (appointment.getVet() == null || !appointment.getVet().getUser().getLogin().equals(currentUserLogin)) {
            throw new BadRequestAlertException("You can only view actions for your own appointments", ENTITY_NAME, "notauthorized");
        }

        List<AppointmentActionDTO> actions = appointmentActionService.findByAppointmentId(id);
        return ResponseEntity.ok().body(actions);
    }

    /**
     * {@code GET  /vet/appointments/{id}/lab-tests} : get all lab tests for appointment.
     *
     * @param id the id of the appointment.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of lab tests.
     */
    @GetMapping("/appointments/{id}/lab-tests")
    public ResponseEntity<List<LabTestDTO>> getAppointmentLabTests(@PathVariable("id") Long id) {
        LOG.debug("REST request to get lab tests for appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        // Validate appointment belongs to current vet
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found", ENTITY_NAME, "notfound"));
        
        if (appointment.getVet() == null || !appointment.getVet().getUser().getLogin().equals(currentUserLogin)) {
            throw new BadRequestAlertException("You can only view lab tests for your own appointments", ENTITY_NAME, "notauthorized");
        }

        List<LabTestDTO> labTests = labTestService.findByAppointmentId(id);
        return ResponseEntity.ok().body(labTests);
    }

    // Request DTOs
    public static class RescheduleRequest {
        private ZonedDateTime newTimeStart;
        private ZonedDateTime newTimeEnd;
        private String notes;

        // Getters and setters
        public ZonedDateTime getNewTimeStart() { return newTimeStart; }
        public void setNewTimeStart(ZonedDateTime newTimeStart) { this.newTimeStart = newTimeStart; }
        public ZonedDateTime getNewTimeEnd() { return newTimeEnd; }
        public void setNewTimeEnd(ZonedDateTime newTimeEnd) { this.newTimeEnd = newTimeEnd; }
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }

    public static class AssignAssistantRequest {
        private String assistantLogin;
        private String notes;

        // Getters and setters
        public String getAssistantLogin() { return assistantLogin; }
        public void setAssistantLogin(String assistantLogin) { this.assistantLogin = assistantLogin; }
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }

    public static class HomeVisitRequest {
        private String notes;

        // Getters and setters
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }

    public static class LabTestRequest {
        private String testName;
        private String testType;
        private String description;

        // Getters and setters
        public String getTestName() { return testName; }
        public void setTestName(String testName) { this.testName = testName; }
        public String getTestType() { return testType; }
        public void setTestType(String testType) { this.testType = testType; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}

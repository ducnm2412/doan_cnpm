package com.docpet.animalhospital.web.rest;

import com.docpet.animalhospital.domain.Appointment;
import com.docpet.animalhospital.domain.AppointmentAction;
import com.docpet.animalhospital.domain.AppointmentAssistant;
import com.docpet.animalhospital.domain.Assistant;
import com.docpet.animalhospital.repository.AppointmentActionRepository;
import com.docpet.animalhospital.repository.AppointmentAssistantRepository;
import com.docpet.animalhospital.repository.AppointmentRepository;
import com.docpet.animalhospital.repository.AssistantRepository;
import com.docpet.animalhospital.repository.UserRepository;
import com.docpet.animalhospital.security.AuthoritiesConstants;
import com.docpet.animalhospital.security.SecurityUtils;
import com.docpet.animalhospital.service.AppointmentActionService;
import com.docpet.animalhospital.service.AppointmentMessageService;
import com.docpet.animalhospital.service.AppointmentService;
import com.docpet.animalhospital.service.LabTestService;
import com.docpet.animalhospital.service.dto.AppointmentActionDTO;
import com.docpet.animalhospital.service.dto.AppointmentDTO;
import com.docpet.animalhospital.service.dto.LabTestDTO;
import com.docpet.animalhospital.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vet")
@PreAuthorize("hasAuthority('" + AuthoritiesConstants.DOCTOR + "')")
public class VetWorkflowResource {

    private static final Logger LOG = LoggerFactory.getLogger(VetWorkflowResource.class);
    private static final String ENTITY_NAME = "appointment";

    private final AppointmentService appointmentService;
    private final AppointmentActionService appointmentActionService;
    private final AppointmentMessageService appointmentMessageService;
    private final LabTestService labTestService;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentActionRepository appointmentActionRepository;
    private final AppointmentAssistantRepository appointmentAssistantRepository;
    private final UserRepository userRepository;
    private final AssistantRepository assistantRepository;

    public VetWorkflowResource(
        AppointmentService appointmentService,
        AppointmentActionService appointmentActionService,
        AppointmentMessageService appointmentMessageService,
        LabTestService labTestService,
        AppointmentRepository appointmentRepository,
        AppointmentActionRepository appointmentActionRepository,
        AppointmentAssistantRepository appointmentAssistantRepository,
        UserRepository userRepository,
        AssistantRepository assistantRepository
    ) {
        this.appointmentService = appointmentService;
        this.appointmentActionService = appointmentActionService;
        this.appointmentMessageService = appointmentMessageService;
        this.labTestService = labTestService;
        this.appointmentRepository = appointmentRepository;
        this.appointmentActionRepository = appointmentActionRepository;
        this.appointmentAssistantRepository = appointmentAssistantRepository;
        this.userRepository = userRepository;
        this.assistantRepository = assistantRepository;
    }

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
                AppointmentDTO dto = appointmentDTO.get();
                dto.setVet(null); // Không hiển thị thông tin vet trong response
                return ResponseEntity.ok().body(dto);
            } else {
                throw new BadRequestAlertException("You can only view your own appointments", ENTITY_NAME, "notauthorized");
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/appointments/{id}/approve")
    public ResponseEntity<AppointmentDTO> approveAppointment(
        @PathVariable("id") Long id,
        @RequestBody(required = false) String notes) {
        LOG.debug("REST request to approve appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        AppointmentDTO appointmentDTO = appointmentService.updateAppointmentStatus(id, "APPROVED", currentUserLogin);
        appointmentActionService.createAppointmentAction(id, "APPROVE", "COMPLETED", "Appointment approved", notes, currentUserLogin, null);
        
        return ResponseEntity.ok().body(appointmentDTO);
    }

    @PostMapping("/appointments/{id}/reject")
    public ResponseEntity<AppointmentDTO> rejectAppointment(
        @PathVariable("id") Long id,
        @RequestBody String notes) {
        LOG.debug("REST request to reject appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        AppointmentDTO appointmentDTO = appointmentService.updateAppointmentStatus(id, "REJECTED", currentUserLogin);
        appointmentActionService.createAppointmentAction(id, "REJECT", "COMPLETED", "Appointment rejected", notes, currentUserLogin, null);
        
        return ResponseEntity.ok().body(appointmentDTO);
    }

    @PostMapping("/appointments/{id}/reschedule")
    public ResponseEntity<AppointmentDTO> rescheduleAppointment(
        @PathVariable("id") Long id,
        @Valid @RequestBody RescheduleRequest rescheduleRequest) {
        LOG.debug("REST request to reschedule appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        // Load appointment entity với eager loading để giữ nguyên pet, vet, owner
        Appointment appointment = appointmentRepository.findOneWithEagerRelationships(id)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found", ENTITY_NAME, "notfound"));
        
        // Lưu lại thông tin gốc của appointment trước khi thay đổi
        ZonedDateTime originalTimeStart = appointment.getTimeStart();
        ZonedDateTime originalTimeEnd = appointment.getTimeEnd();
        String appointmentType = appointment.getAppointmentType();
        
        // Chỉ thay đổi thời gian bắt đầu
        appointment.setTimeStart(rescheduleRequest.getNewTimeStart());
        
        // Tự động tính endTime dựa trên thời lượng của appointment gốc
        if (originalTimeStart != null && originalTimeEnd != null) {
            // Giữ nguyên thời lượng, chỉ thay đổi thời gian bắt đầu
            java.time.Duration duration = java.time.Duration.between(originalTimeStart, originalTimeEnd);
            appointment.setTimeEnd(rescheduleRequest.getNewTimeStart().plus(duration));
        } else {
            // Nếu appointment gốc không có timeEnd, tính dựa trên appointmentType
            // EMERGENCY: 2 giờ, NORMAL: 1 giờ
            int durationHours = "EMERGENCY".equals(appointmentType) ? 2 : 1;
            appointment.setTimeEnd(rescheduleRequest.getNewTimeStart().plusHours(durationHours));
        }
        
        appointment.setStatus("RESCHEDULED");
        
        // Thêm ghi chú vào appointment nếu có
        if (rescheduleRequest.getNotes() != null && !rescheduleRequest.getNotes().trim().isEmpty()) {
            String currentNotes = appointment.getNotes();
            if (currentNotes != null && !currentNotes.trim().isEmpty()) {
                // Thêm ghi chú mới vào cuối, cách nhau bởi 2 dòng mới để tách biệt rõ ràng
                appointment.setNotes(currentNotes + "\n\n[Đổi lịch] " + rescheduleRequest.getNotes());
            } else {
                // Nếu chưa có ghi chú, thêm mới
                appointment.setNotes("[Đổi lịch] " + rescheduleRequest.getNotes());
            }
        }
        
        // Save appointment (giữ nguyên pet, vet, owner)
        appointment = appointmentRepository.save(appointment);
        
        // Tạo appointment action
        appointmentActionService.createAppointmentAction(id, "RESCHEDULE", "COMPLETED", "Appointment rescheduled", rescheduleRequest.getNotes(), currentUserLogin, null);
        
        // Tự động gửi tin nhắn thông báo đổi lịch cho owner
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String newTimeStartFormatted = appointment.getTimeStart().format(formatter);
            String newTimeEndFormatted = appointment.getTimeEnd() != null ? appointment.getTimeEnd().format(formatter) : "";
            
            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("Lịch hẹn của bạn đã được đổi lịch.\n");
            messageBuilder.append("Thời gian mới: ").append(newTimeStartFormatted);
            if (!newTimeEndFormatted.isEmpty()) {
                messageBuilder.append(" - ").append(newTimeEndFormatted);
            }
            
            if (rescheduleRequest.getNotes() != null && !rescheduleRequest.getNotes().trim().isEmpty()) {
                messageBuilder.append("\nGhi chú: ").append(rescheduleRequest.getNotes());
            }
            
            appointmentMessageService.createMessage(id, messageBuilder.toString(), currentUserLogin);
            LOG.debug("Sent reschedule notification message to owner for appointment: {}", id);
        } catch (Exception e) {
            // Log lỗi nhưng không làm gián đoạn luồng chính
            LOG.warn("Failed to send reschedule notification message: {}", e.getMessage());
        }
        
        // Convert sang DTO để trả về
        AppointmentDTO updatedAppointment = appointmentService.findOne(id)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found after update", ENTITY_NAME, "notfound"));
        
        return ResponseEntity.ok().body(updatedAppointment);
    }

    @PostMapping("/appointments/{id}/assign-assistant")
    public ResponseEntity<AppointmentActionDTO> assignAssistant(
        @PathVariable("id") Long id,
        @Valid @RequestBody AssignAssistantRequest assignRequest) {
        LOG.debug("REST request to assign assistant for appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        // Lấy appointment với eager loading vet và vet.user để kiểm tra
        Appointment appointment = appointmentRepository.findOneWithEagerRelationships(id)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found", ENTITY_NAME, "notfound"));

        // Kiểm tra appointment thuộc về vet hiện tại
        if (appointment.getVet() == null || appointment.getVet().getUser() == null) {
            throw new BadRequestAlertException("Appointment does not have a vet assigned", ENTITY_NAME, "novet");
        }
        
        // Load user nếu chưa được load (lazy loading)
        String vetLogin = appointment.getVet().getUser().getLogin();
        if (!vetLogin.equals(currentUserLogin)) {
            throw new BadRequestAlertException("You can only assign assistant for your own appointments", ENTITY_NAME, "notauthorized");
        }

        // Lấy assistant theo id
        Assistant assistant = assistantRepository.findByIdWithUser(assignRequest.getAssistantId())
            .orElseThrow(() -> new BadRequestAlertException("Assistant not found", ENTITY_NAME, "assistantnotfound"));
        
        if (assistant.getUser() == null) {
            throw new BadRequestAlertException("Assistant does not have a user", ENTITY_NAME, "nouser");
        }
        
        com.docpet.animalhospital.domain.User assistantUser = assistant.getUser();

        // Kiểm tra conflict: timeStart không được trùng nhau và phải cách nhau ít nhất 1 tiếng
        List<AppointmentAction> existingAssignments = appointmentActionRepository
            .findActiveAssignmentsByAssistantAndDate(assistantUser.getId(), appointment.getTimeStart());

        for (AppointmentAction existingAction : existingAssignments) {
            Appointment existingAppointment = existingAction.getAppointment();
            // Bỏ qua appointment hiện tại đang được phân công
            if (existingAppointment.getId().equals(id)) {
                continue;
            }

            ZonedDateTime existingTimeStart = existingAppointment.getTimeStart();
            ZonedDateTime newTimeStart = appointment.getTimeStart();

            // Tính khoảng cách giữa 2 timeStart
            long hoursDiff = Math.abs(java.time.Duration.between(existingTimeStart, newTimeStart).toHours());

            // Kiểm tra: trùng giờ (cùng hour) hoặc cách nhau < 1 tiếng
            if (existingTimeStart.getHour() == newTimeStart.getHour() || hoursDiff < 1) {
                throw new BadRequestAlertException(
                    "Trợ lý đã được phân công cho lịch hẹn khác vào thời gian này. TimeStart không được trùng nhau và phải cách nhau ít nhất 1 tiếng.",
                    ENTITY_NAME,
                    "assistantnotavailable"
                );
            }
        }

        // Tạo appointment action (lưu vào bảng appointment_action)
        AppointmentActionDTO actionDTO = appointmentActionService.createAppointmentAction(
            id, 
            "ASSIGN_ASSISTANT", 
            "PENDING", 
            "Assistant assigned for sample collection", 
            assignRequest.getNotes(), 
            currentUserLogin,
            assistantUser.getLogin()
        );

        // Lưu vào bảng appointment_assistant (quan hệ trực tiếp Appointment - Assistant)
        Optional<AppointmentAssistant> existingAssignment = appointmentAssistantRepository
            .findByAppointmentIdAndAssistantId(id, assistant.getId());
        
        if (existingAssignment.isEmpty()) {
            AppointmentAssistant appointmentAssistant = new AppointmentAssistant();
            appointmentAssistant.setAppointment(appointment);
            appointmentAssistant.setAssistant(assistant);
            appointmentAssistant.setCreatedAt(ZonedDateTime.now());
            appointmentAssistantRepository.save(appointmentAssistant);
            LOG.debug("Saved appointment_assistant record: appointmentId={}, assistantId={}", id, assistant.getId());
        } else {
            LOG.debug("Appointment assistant relationship already exists: appointmentId={}, assistantId={}", id, assistant.getId());
        }
        
        return ResponseEntity.ok().body(actionDTO);
    }

    @PostMapping("/appointments/{id}/request-home-visit")
    public ResponseEntity<AppointmentActionDTO> requestHomeVisit(
        @PathVariable("id") Long id,
        @Valid @RequestBody HomeVisitRequest homeVisitRequest) {
        LOG.debug("REST request to request home visit for appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        // Load appointment với eager loading để giữ nguyên pet, vet, owner
        Appointment appointment = appointmentRepository.findOneWithEagerRelationships(id)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found", ENTITY_NAME, "notfound"));
        
        // Chỉ update locationType, giữ nguyên các relationships
        appointment.setLocationType("AT_HOME");
        appointment = appointmentRepository.save(appointment);

        AppointmentActionDTO actionDTO = appointmentActionService.createAppointmentAction(
            id, 
            "REQUEST_HOME_VISIT", 
            "PENDING", 
            "Home visit requested", 
            homeVisitRequest.getNotes(), 
            currentUserLogin,
            null
        );
        
        // Tự động gửi tin nhắn thông báo yêu cầu khám tại nhà cho owner
        try {
            String messageContent = "Bác sĩ đã yêu cầu thăm khám tại nhà cho lịch hẹn này.";
            if (homeVisitRequest.getNotes() != null && !homeVisitRequest.getNotes().trim().isEmpty()) {
                messageContent += "\nGhi chú: " + homeVisitRequest.getNotes();
            }
            
            appointmentMessageService.createMessage(id, messageContent, currentUserLogin);
            LOG.debug("Sent home visit request notification message to owner for appointment: {}", id);
        } catch (Exception e) {
            // Log lỗi nhưng không làm gián đoạn luồng chính
            LOG.warn("Failed to send home visit request notification message: {}", e.getMessage());
        }
        
        return ResponseEntity.ok().body(actionDTO);
    }

    @PostMapping("/appointments/{id}/request-lab-test")
    public ResponseEntity<LabTestDTO> requestLabTest(
        @PathVariable("id") Long id,
        @Valid @RequestBody LabTestRequest labTestRequest) {
        LOG.debug("REST request to request lab test for appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

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

    @GetMapping("/appointments/{id}/actions")
    public ResponseEntity<List<AppointmentActionDTO>> getAppointmentActions(@PathVariable("id") Long id) {
        LOG.debug("REST request to get actions for appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Appointment not found", ENTITY_NAME, "notfound"));
        
        if (appointment.getVet() == null || !appointment.getVet().getUser().getLogin().equals(currentUserLogin)) {
            throw new BadRequestAlertException("You can only view actions for your own appointments", ENTITY_NAME, "notauthorized");
        }

        List<AppointmentActionDTO> actions = appointmentActionService.findByAppointmentId(id);
        return ResponseEntity.ok().body(actions);
    }

    @GetMapping("/appointments/{id}/lab-tests")
    public ResponseEntity<List<LabTestDTO>> getAppointmentLabTests(@PathVariable("id") Long id) {
        LOG.debug("REST request to get lab tests for appointment: {}", id);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));

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
        private String notes;

        public ZonedDateTime getNewTimeStart() { return newTimeStart; }
        public void setNewTimeStart(ZonedDateTime newTimeStart) { this.newTimeStart = newTimeStart; }
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }

    public static class AssignAssistantRequest {
        private Long assistantId;
        private String notes;

        public Long getAssistantId() { return assistantId; }
        public void setAssistantId(Long assistantId) { this.assistantId = assistantId; }
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }

    public static class HomeVisitRequest {
        private String notes;

        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }

    public static class LabTestRequest {
        private String testName;
        private String testType;
        private String description;

        public String getTestName() { return testName; }
        public void setTestName(String testName) { this.testName = testName; }
        public String getTestType() { return testType; }
        public void setTestType(String testType) { this.testType = testType; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}


package com.docpet.animalhospital.web.rest;

import com.docpet.animalhospital.domain.Assistant;
import com.docpet.animalhospital.domain.User;
import com.docpet.animalhospital.repository.AssistantRepository;
import com.docpet.animalhospital.repository.UserRepository;
import com.docpet.animalhospital.security.AuthoritiesConstants;
import com.docpet.animalhospital.service.AssistantService;
import com.docpet.animalhospital.service.MailService;
import com.docpet.animalhospital.service.UserService;
import com.docpet.animalhospital.service.dto.AdminUserDTO;
import com.docpet.animalhospital.web.rest.errors.BadRequestAlertException;
import com.docpet.animalhospital.web.rest.errors.EmailAlreadyUsedException;
import com.docpet.animalhospital.web.rest.errors.InvalidPasswordException;
import com.docpet.animalhospital.web.rest.errors.LoginAlreadyUsedException;
import com.docpet.animalhospital.web.rest.vm.AssistantRegistrationVM;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vets")
@PreAuthorize("hasAuthority('" + AuthoritiesConstants.DOCTOR + "')")
public class VetAssistantController {

    private static final Logger LOG = LoggerFactory.getLogger(VetAssistantController.class);
    private static final String ENTITY_NAME = "assistant";

    private final UserService userService;
    private final UserRepository userRepository;
    private final AssistantRepository assistantRepository;
    private final AssistantService assistantService;
    private final MailService mailService;

    public VetAssistantController(
        UserService userService,
        UserRepository userRepository,
        AssistantRepository assistantRepository,
        AssistantService assistantService,
        MailService mailService
    ) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.assistantRepository = assistantRepository;
        this.assistantService = assistantService;
        this.mailService = mailService;
    }

    @PostMapping("/assistants")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AdminUserDTO> createAssistant(@Valid @RequestBody AssistantRegistrationVM assistantRegistrationVM) {
        LOG.debug("REST request to create Assistant by Vet: {}", assistantRegistrationVM);
        
        if (isPasswordLengthInvalid(assistantRegistrationVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        
        User user = userService.registerAssistant(assistantRegistrationVM, assistantRegistrationVM.getPassword());
        AdminUserDTO userDTO = new AdminUserDTO(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @GetMapping("/assistants")
    public ResponseEntity<List<AdminUserDTO>> getAllAssistants() {
        LOG.debug("REST request to get all Assistants for current Vet");
        
        // Lấy tất cả assistants từ bảng assistant với eager loading user
        List<Assistant> assistants = assistantRepository.findAllWithUser();
        List<AdminUserDTO> assistantDTOs = assistants.stream()
            .filter(assistant -> assistant.getUser() != null)
            .map(assistant -> new AdminUserDTO(assistant.getUser()))
            .collect(Collectors.toList());
            
        return ResponseEntity.ok().body(assistantDTOs);
    }

    @GetMapping("/assistants/{id}")
    public ResponseEntity<AdminUserDTO> getAssistant(@PathVariable Long id) {
        LOG.debug("REST request to get Assistant : {}", id);
        
        Optional<Assistant> assistant = assistantRepository.findByIdWithUser(id);
        if (assistant.isPresent() && assistant.get().getUser() != null) {
            return ResponseEntity.ok().body(new AdminUserDTO(assistant.get().getUser()));
        }
        
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/assistants/{id}")
    public ResponseEntity<AdminUserDTO> updateAssistant(@PathVariable Long id, @Valid @RequestBody AdminUserDTO userDTO) {
        LOG.debug("REST request to update Assistant : {}", id);
        
        Assistant assistant = assistantRepository.findByIdWithUser(id)
            .orElseThrow(() -> new BadRequestAlertException("Assistant not found", ENTITY_NAME, "notfound"));
        
        if (assistant.getUser() == null) {
            throw new BadRequestAlertException("Assistant does not have a user", ENTITY_NAME, "nouser");
        }
        
        // Update user của assistant
        User user = assistant.getUser();
        if (userDTO.getFirstName() != null) {
            user.setFirstName(userDTO.getFirstName());
        }
        if (userDTO.getLastName() != null) {
            user.setLastName(userDTO.getLastName());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail().toLowerCase());
        }
        if (userDTO.getLangKey() != null) {
            user.setLangKey(userDTO.getLangKey());
        }
        if (userDTO.getImageUrl() != null) {
            user.setImageUrl(userDTO.getImageUrl());
        }
        
        User updatedUser = userRepository.save(user);
        
        return ResponseEntity.ok().body(new AdminUserDTO(updatedUser));
    }

    @DeleteMapping("/assistants/{id}")
    public ResponseEntity<Void> deleteAssistant(@PathVariable Long id) {
        LOG.debug("REST request to delete Assistant : {}", id);
        
        Assistant assistant = assistantRepository.findByIdWithUser(id)
            .orElseThrow(() -> new BadRequestAlertException("Assistant not found", ENTITY_NAME, "notfound"));
        
        // Lưu lại thông tin user trước khi xóa assistant
        String userLogin = null;
        if (assistant.getUser() != null) {
            userLogin = assistant.getUser().getLogin();
        }
        
        // Xóa Assistant trước để tránh lỗi TransientObjectException
        assistantRepository.delete(assistant);
        assistantRepository.flush(); // Đảm bảo xóa assistant trước
        
        // Sau đó mới xóa User (nếu có)
        if (userLogin != null) {
            userService.deleteUser(userLogin);
        }
        
        return ResponseEntity.noContent().build();
    }

    private static boolean isPasswordLengthInvalid(String password) {
        return (
            StringUtils.isEmpty(password) ||
            password.length() < AssistantRegistrationVM.PASSWORD_MIN_LENGTH ||
            password.length() > AssistantRegistrationVM.PASSWORD_MAX_LENGTH
        );
    }
}


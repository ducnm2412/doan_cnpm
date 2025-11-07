package com.docpet.animalhospital.web.rest;

import com.docpet.animalhospital.domain.User;
import com.docpet.animalhospital.repository.UserRepository;
import com.docpet.animalhospital.security.AuthoritiesConstants;
import com.docpet.animalhospital.security.SecurityUtils;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing assistants by vets.
 */
@RestController
@RequestMapping("/api/vets")
@PreAuthorize("hasAuthority('" + AuthoritiesConstants.DOCTOR + "')")
public class VetAssistantController {

    private static final Logger LOG = LoggerFactory.getLogger(VetAssistantController.class);

    private static final String ENTITY_NAME = "assistant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserService userService;
    private final UserRepository userRepository;
    private final MailService mailService;

    public VetAssistantController(UserService userService, UserRepository userRepository, MailService mailService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    /**
     * {@code POST  /vets/assistants} : Create a new assistant.
     *
     * @param assistantRegistrationVM the assistant registration View Model.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assistant info.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already used.
     */
    @PostMapping("/assistants")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AdminUserDTO> createAssistant(@Valid @RequestBody AssistantRegistrationVM assistantRegistrationVM) {
        LOG.debug("REST request to create Assistant by Vet: {}", assistantRegistrationVM);
        
        if (isPasswordLengthInvalid(assistantRegistrationVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        
        User user = userService.registerAssistant(assistantRegistrationVM, assistantRegistrationVM.getPassword());
        
        // Send welcome email to assistant
        mailService.sendWelcomeEmail(user);
        
        AdminUserDTO userDTO = new AdminUserDTO(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    /**
     * {@code GET  /vets/assistants} : get all assistants created by current vet.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assistants in body.
     */
    @GetMapping("/assistants")
    public ResponseEntity<List<AdminUserDTO>> getAllAssistants() {
        LOG.debug("REST request to get all Assistants for current Vet");
        
        List<User> assistants = userRepository.findAllByAuthorities_Name(AuthoritiesConstants.ASSISTANT);
        List<AdminUserDTO> assistantDTOs = assistants.stream()
            .map(AdminUserDTO::new)
            .collect(Collectors.toList());
            
        return ResponseEntity.ok().body(assistantDTOs);
    }

    /**
     * {@code GET  /vets/assistants/{login}} : get the assistant by login.
     *
     * @param login the login of the assistant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assistant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/assistants/{login}")
    public ResponseEntity<AdminUserDTO> getAssistant(@PathVariable String login) {
        LOG.debug("REST request to get Assistant : {}", login);
        
        Optional<User> user = userRepository.findOneByLogin(login);
        if (user.isPresent() && user.get().getAuthorities().stream()
            .anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.ASSISTANT))) {
            return ResponseEntity.ok().body(new AdminUserDTO(user.get()));
        }
        
        return ResponseEntity.notFound().build();
    }

    /**
     * {@code PUT  /vets/assistants/{login}} : Updates an existing assistant.
     *
     * @param login the login of the assistant to update.
     * @param userDTO the assistant information to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assistant.
     */
    @PutMapping("/assistants/{login}")
    public ResponseEntity<AdminUserDTO> updateAssistant(@PathVariable String login, @Valid @RequestBody AdminUserDTO userDTO) {
        LOG.debug("REST request to update Assistant : {}", login);
        
        Optional<User> existingUser = userRepository.findOneByLogin(login);
        if (existingUser.isPresent() && existingUser.get().getAuthorities().stream()
            .anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.ASSISTANT))) {
            
            // Update user information
            userService.updateUser(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail(),
                userDTO.getLangKey(),
                userDTO.getImageUrl()
            );
            
            return ResponseEntity.ok().body(new AdminUserDTO(existingUser.get()));
        }
        
        return ResponseEntity.notFound().build();
    }

    /**
     * {@code DELETE  /vets/assistants/{login}} : delete the assistant.
     *
     * @param login the login of the assistant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @DeleteMapping("/assistants/{login}")
    public ResponseEntity<Void> deleteAssistant(@PathVariable String login) {
        LOG.debug("REST request to delete Assistant : {}", login);
        
        Optional<User> user = userRepository.findOneByLogin(login);
        if (user.isPresent() && user.get().getAuthorities().stream()
            .anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.ASSISTANT))) {
            
            userService.deleteUser(login);
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.notFound().build();
    }

    private static boolean isPasswordLengthInvalid(String password) {
        return (
            StringUtils.isEmpty(password) ||
            password.length() < 4 ||
            password.length() > 100
        );
    }
}





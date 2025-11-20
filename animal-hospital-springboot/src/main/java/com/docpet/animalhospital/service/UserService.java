package com.docpet.animalhospital.service;

import com.docpet.animalhospital.config.Constants;
import com.docpet.animalhospital.domain.Assistant;
import com.docpet.animalhospital.domain.Authority;
import com.docpet.animalhospital.domain.Owner;
import com.docpet.animalhospital.domain.User;
import com.docpet.animalhospital.domain.Vet;
import com.docpet.animalhospital.repository.AssistantRepository;
import com.docpet.animalhospital.repository.AuthorityRepository;
import com.docpet.animalhospital.repository.OwnerRepository;
import com.docpet.animalhospital.repository.UserRepository;
import com.docpet.animalhospital.repository.VetRepository;
import com.docpet.animalhospital.security.AuthoritiesConstants;
import com.docpet.animalhospital.security.SecurityUtils;
import com.docpet.animalhospital.service.dto.AdminUserDTO;
import com.docpet.animalhospital.service.dto.UserDTO;
import com.docpet.animalhospital.util.RandomUtil;
import com.docpet.animalhospital.web.rest.vm.AssistantRegistrationVM;
import com.docpet.animalhospital.web.rest.vm.VetRegistrationVM;
import org.apache.commons.lang3.StringUtils;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final OwnerRepository ownerRepository;
    private final VetRepository vetRepository;
    private final AssistantRepository assistantRepository;

    public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        AuthorityRepository authorityRepository,
        OwnerRepository ownerRepository,
        VetRepository vetRepository,
        AssistantRepository assistantRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.ownerRepository = ownerRepository;
        this.vetRepository = vetRepository;
        this.assistantRepository = assistantRepository;
    }

    public Optional<User> activateRegistration(String key) {
        LOG.debug("Activating user for activation key {}", key);
        return userRepository
            .findOneByActivationKey(key)
            .map(user -> {
                user.setActivated(true);
                user.setActivationKey(null);
                LOG.debug("Activated user: {}", user);
                return user;
            });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        LOG.debug("Reset user password for reset key {}", key);
        return userRepository
            .findOneByResetKey(key)
            .filter(user -> user.getResetDate().isAfter(Instant.now().minus(1, ChronoUnit.DAYS)))
            .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetKey(null);
                user.setResetDate(null);
                return user;
            });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository
            .findOneByEmailIgnoreCase(mail)
            .filter(User::isActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(Instant.now());
                return user;
            });
    }

    public User registerUser(AdminUserDTO userDTO, String password) {
        userRepository
            .findOneByLogin(userDTO.getLogin().toLowerCase())
            .ifPresent(existingUser -> {
                boolean removed = removeNonActivatedUser(existingUser);
                if (!removed) {
                    throw new UsernameAlreadyUsedException();
                }
            });
        userRepository
            .findOneByEmailIgnoreCase(userDTO.getEmail())
            .ifPresent(existingUser -> {
                boolean removed = removeNonActivatedUser(existingUser);
                if (!removed) {
                    throw new EmailAlreadyUsedException();
                }
            });
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(userDTO.getLogin().toLowerCase());
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            newUser.setEmail(userDTO.getEmail().toLowerCase());
        }
        newUser.setImageUrl(userDTO.getImageUrl());
        newUser.setLangKey(userDTO.getLangKey());
        newUser.setActivated(true);
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        // Set auditing fields: user tự tạo chính mình
        // Set trực tiếp vào field để đảm bảo không bị ghi đè
        String userLogin = newUser.getLogin();
        String createdByValue = (userLogin != null && !userLogin.trim().isEmpty()) ? userLogin : "system";
        Instant now = Instant.now();
        
        // Set giá trị
        newUser.setCreatedBy(createdByValue);
        newUser.setLastModifiedBy(createdByValue);
        newUser.setCreatedDate(now);
        newUser.setLastModifiedDate(now);
        
        // Debug: log giá trị trước khi save
        LOG.debug("Before save - createdBy: {}, login: {}", newUser.getCreatedBy(), newUser.getLogin());
        System.out.println("=== UserService Before Save ===");
        System.out.println("createdBy: " + newUser.getCreatedBy());
        System.out.println("login: " + newUser.getLogin());
        
        // Save - @PrePersist sẽ đảm bảo giá trị không null nếu vẫn còn null
        User savedUser = userRepository.saveAndFlush(newUser);
        
        // Debug: log giá trị sau khi save
        LOG.debug("After save - createdBy: {}", savedUser.getCreatedBy());
        System.out.println("=== UserService After Save ===");
        System.out.println("createdBy: " + savedUser.getCreatedBy());

        // Tạo Owner profile
        Owner owner = new Owner();
        String fullName = buildFullName(userDTO.getFirstName(), userDTO.getLastName());
        owner.setName(fullName);
        owner.setPhone("");
        owner.setAddress("");
        owner.setUser(savedUser);
        ownerRepository.save(owner);
        LOG.debug("Created Owner profile for User: {}", savedUser.getLogin());

        return savedUser;
    }

    public User registerVet(VetRegistrationVM vetRegistrationVM, String password) {
        userRepository
            .findOneByLogin(vetRegistrationVM.getLogin().toLowerCase())
            .ifPresent(existingUser -> {
                boolean removed = removeNonActivatedUser(existingUser);
                if (!removed) {
                    throw new UsernameAlreadyUsedException();
                }
            });
        userRepository
            .findOneByEmailIgnoreCase(vetRegistrationVM.getEmail())
            .ifPresent(existingUser -> {
                boolean removed = removeNonActivatedUser(existingUser);
                if (!removed) {
                    throw new EmailAlreadyUsedException();
                }
            });
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(vetRegistrationVM.getLogin().toLowerCase());
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(vetRegistrationVM.getFirstName());
        newUser.setLastName(vetRegistrationVM.getLastName());
        if (vetRegistrationVM.getEmail() != null) {
            newUser.setEmail(vetRegistrationVM.getEmail().toLowerCase());
        }
        newUser.setLangKey(vetRegistrationVM.getLangKey());
        newUser.setActivated(true);
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.DOCTOR).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        // Set auditing fields: user tự tạo chính mình
        String userLogin = newUser.getLogin();
        String createdByValue = (userLogin != null && !userLogin.trim().isEmpty()) ? userLogin : "system";
        Instant now = Instant.now();
        newUser.setCreatedBy(createdByValue);
        newUser.setLastModifiedBy(createdByValue);
        newUser.setCreatedDate(now);
        newUser.setLastModifiedDate(now);
        // Save - @PrePersist sẽ đảm bảo giá trị không null nếu vẫn còn null
        User savedUser = userRepository.saveAndFlush(newUser);

        // Tạo Vet profile
        Vet vet = new Vet();
        vet.setLicenseNo(vetRegistrationVM.getLicenseNo());
        if (vetRegistrationVM.getSpecialization() != null) {
            vet.setSpecialization(vetRegistrationVM.getSpecialization());
        }
        vet.setUser(savedUser);
        vetRepository.save(vet);
        LOG.debug("Created Vet profile for User: {}", savedUser.getLogin());

        return savedUser;
    }

    public User registerAssistant(AssistantRegistrationVM assistantRegistrationVM, String password) {
        userRepository
            .findOneByLogin(assistantRegistrationVM.getLogin().toLowerCase())
            .ifPresent(existingUser -> {
                boolean removed = removeNonActivatedUser(existingUser);
                if (!removed) {
                    throw new UsernameAlreadyUsedException();
                }
            });
        userRepository
            .findOneByEmailIgnoreCase(assistantRegistrationVM.getEmail())
            .ifPresent(existingUser -> {
                boolean removed = removeNonActivatedUser(existingUser);
                if (!removed) {
                    throw new EmailAlreadyUsedException();
                }
            });
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(assistantRegistrationVM.getLogin().toLowerCase());
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(assistantRegistrationVM.getFirstName());
        newUser.setLastName(assistantRegistrationVM.getLastName());
        if (assistantRegistrationVM.getEmail() != null) {
            newUser.setEmail(assistantRegistrationVM.getEmail().toLowerCase());
        }
        newUser.setLangKey(assistantRegistrationVM.getLangKey());
        newUser.setActivated(true);
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.ASSISTANT).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        // Set auditing fields: user tự tạo chính mình
        String userLogin = newUser.getLogin();
        String createdByValue = (userLogin != null && !userLogin.trim().isEmpty()) ? userLogin : "system";
        Instant now = Instant.now();
        newUser.setCreatedBy(createdByValue);
        newUser.setLastModifiedBy(createdByValue);
        newUser.setCreatedDate(now);
        newUser.setLastModifiedDate(now);
        // Save - @PrePersist sẽ đảm bảo giá trị không null nếu vẫn còn null
        User savedUser = userRepository.saveAndFlush(newUser);

        // Tạo Assistant profile
        Assistant assistant = new Assistant();
        // Có thể thêm employeeId và department từ AssistantRegistrationVM nếu cần
        assistant.setUser(savedUser);
        assistantRepository.save(assistant);
        LOG.debug("Created Assistant profile for User: {}", savedUser.getLogin());

        return savedUser;
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.isActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        return true;
    }

    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                if (email != null) {
                    user.setEmail(email.toLowerCase());
                }
                user.setLangKey(langKey);
                user.setImageUrl(imageUrl);
                userRepository.save(user);
                LOG.debug("Changed Information for User: {}", user);
            });
    }

    @Transactional
    public void changePassword(String currentClearTextPassword, String newPassword) {
        User user = SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        String currentEncryptedPassword = user.getPassword();
        if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
            throw new InvalidPasswordException();
        }
        String encryptedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        LOG.debug("Changed password for User: {}", user);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository
            .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
            .forEach(user -> {
                LOG.debug("Deleting not activated user {}", user.getLogin());
                userRepository.delete(user);
            });
    }

    @Transactional(readOnly = true)
    public Page<AdminUserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(AdminUserDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllPublicUsers(Pageable pageable) {
        return userRepository.findAllByIdNotNullAndActivatedIsTrue(pageable).map(UserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository
            .findOneByLogin(login)
            .ifPresent(user -> {
                userRepository.delete(user);
                LOG.debug("Deleted User: {}", user);
            });
    }

    @Transactional(readOnly = true)
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).toList();
    }

    private String buildFullName(String firstName, String lastName) {
        if (StringUtils.isBlank(firstName) && StringUtils.isBlank(lastName)) {
            return "";
        }
        if (StringUtils.isBlank(firstName)) {
            return lastName.trim();
        }
        if (StringUtils.isBlank(lastName)) {
            return firstName.trim();
        }
        return (firstName.trim() + " " + lastName.trim()).trim();
    }
}


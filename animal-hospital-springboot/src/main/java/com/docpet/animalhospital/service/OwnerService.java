package com.docpet.animalhospital.service;

import com.docpet.animalhospital.domain.Owner;
import com.docpet.animalhospital.domain.User;
import com.docpet.animalhospital.repository.OwnerRepository;
import com.docpet.animalhospital.repository.UserRepository;
import com.docpet.animalhospital.security.SecurityUtils;
import com.docpet.animalhospital.service.dto.OwnerDTO;
import com.docpet.animalhospital.service.mapper.OwnerMapper;
import com.docpet.animalhospital.web.rest.errors.BadRequestAlertException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OwnerService {

    private static final Logger LOG = LoggerFactory.getLogger(OwnerService.class);
    private static final String ENTITY_NAME = "owner";

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;
    private final UserRepository userRepository;

    public OwnerService(OwnerRepository ownerRepository, OwnerMapper ownerMapper, UserRepository userRepository) {
        this.ownerRepository = ownerRepository;
        this.ownerMapper = ownerMapper;
        this.userRepository = userRepository;
    }

    public OwnerDTO save(OwnerDTO ownerDTO) {
        LOG.debug("Request to save Owner : {}", ownerDTO);
        
        // Lấy user hiện tại
        User currentUser = SecurityUtils.getCurrentUserLogin()
            .flatMap(login -> userRepository.findOneByLogin(login.toLowerCase()))
            .orElse(null);
        
        // Nếu user đã có owner, update owner hiện tại thay vì tạo mới
        if (currentUser != null) {
            Optional<Owner> existingOwnerOpt = ownerRepository.findFirstByUser_Login(currentUser.getLogin());
            if (existingOwnerOpt.isPresent()) {
                // Update owner hiện tại - combine name từ firstName/lastName nếu cần
                Owner owner = existingOwnerOpt.get();
                if (ownerDTO.getName() != null && !ownerDTO.getName().trim().isEmpty()) {
                    owner.setName(ownerDTO.getName());
                } else if (ownerDTO.getFirstName() != null || ownerDTO.getLastName() != null) {
                    // Combine firstName và lastName
                    String firstName = ownerDTO.getFirstName() != null ? ownerDTO.getFirstName().trim() : "";
                    String lastName = ownerDTO.getLastName() != null ? ownerDTO.getLastName().trim() : "";
                    if (!firstName.isEmpty() || !lastName.isEmpty()) {
                        owner.setName((firstName + " " + lastName).trim());
                    }
                }
                owner.setPhone(ownerDTO.getPhone() != null ? ownerDTO.getPhone() : owner.getPhone());
                owner.setAddress(ownerDTO.getAddress() != null ? ownerDTO.getAddress() : owner.getAddress());
                owner = ownerRepository.save(owner);
                
                // Xóa các owner duplicate (nếu có)
                final Long savedOwnerId = owner.getId(); // Lưu ownerId vào biến final để dùng trong lambda
                java.util.List<Owner> duplicateOwners = ownerRepository.findAllByUser_Login(currentUser.getLogin());
                if (duplicateOwners.size() > 1) {
                    duplicateOwners.stream()
                        .filter(o -> !o.getId().equals(savedOwnerId))
                        .forEach(ownerRepository::delete);
                    LOG.warn("Deleted {} duplicate owners for user {}", duplicateOwners.size() - 1, currentUser.getLogin());
                }
                
                return ownerMapper.toDto(owner);
            }
        }
        
        // Tạo owner mới
        Owner owner = ownerMapper.toEntity(ownerDTO);
        
        // Nếu chưa có user được set, tự động set user hiện tại
        if (owner.getUser() == null && currentUser != null) {
            owner.setUser(currentUser);
        }
        
        owner = ownerRepository.save(owner);
        return ownerMapper.toDto(owner);
    }

    public OwnerDTO update(OwnerDTO ownerDTO) {
        LOG.debug("Request to update Owner : {}", ownerDTO);
        
        // Load owner hiện tại từ database cùng với user để tránh lazy loading issues
        Owner existingOwner = ownerRepository.findByIdWithUser(ownerDTO.getId())
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
        
        // Lấy user hiện tại
        User currentUser = SecurityUtils.getCurrentUserLogin()
            .flatMap(login -> userRepository.findOneByLogin(login.toLowerCase()))
            .orElse(null);
        
        // Nếu owner chưa có user, tự động set user hiện tại
        if (existingOwner.getUser() == null && currentUser != null) {
            existingOwner.setUser(currentUser);
            LOG.debug("Set user {} to owner {}", currentUser.getLogin(), existingOwner.getId());
        } else if (existingOwner.getUser() == null && currentUser == null) {
            LOG.warn("Owner {} has no user and current user is also null. User will remain null.", existingOwner.getId());
        } else if (existingOwner.getUser() != null) {
            LOG.debug("Owner {} already has user {}. Keeping existing user.", existingOwner.getId(), existingOwner.getUser().getLogin());
        }
        
        // Update các field từ DTO
        if (ownerDTO.getName() != null && !ownerDTO.getName().trim().isEmpty()) {
            existingOwner.setName(ownerDTO.getName());
        } else if (ownerDTO.getFirstName() != null || ownerDTO.getLastName() != null) {
            // Combine firstName và lastName
            String firstName = ownerDTO.getFirstName() != null ? ownerDTO.getFirstName().trim() : "";
            String lastName = ownerDTO.getLastName() != null ? ownerDTO.getLastName().trim() : "";
            if (!firstName.isEmpty() || !lastName.isEmpty()) {
                existingOwner.setName((firstName + " " + lastName).trim());
            }
        }
        
        if (ownerDTO.getPhone() != null) {
            existingOwner.setPhone(ownerDTO.getPhone());
        }
        if (ownerDTO.getAddress() != null) {
            existingOwner.setAddress(ownerDTO.getAddress());
        }
        
        // Giữ nguyên user (nếu đã có) hoặc đã set ở trên
        
        Owner savedOwner = ownerRepository.save(existingOwner);
        return ownerMapper.toDto(savedOwner);
    }

    public Optional<OwnerDTO> partialUpdate(OwnerDTO ownerDTO) {
        LOG.debug("Request to partially update Owner : {}", ownerDTO);
        return ownerRepository
            .findById(ownerDTO.getId())
            .map(existingOwner -> {
                if (ownerDTO.getName() != null) {
                    existingOwner.setName(ownerDTO.getName());
                }
                if (ownerDTO.getPhone() != null) {
                    existingOwner.setPhone(ownerDTO.getPhone());
                }
                if (ownerDTO.getAddress() != null) {
                    existingOwner.setAddress(ownerDTO.getAddress());
                }
                return existingOwner;
            })
            .map(ownerRepository::save)
            .map(ownerMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<OwnerDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Owners");
        return ownerRepository.findAll(pageable).map(ownerMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<OwnerDTO> findOne(Long id) {
        LOG.debug("Request to get Owner : {}", id);
        return ownerRepository.findById(id).map(ownerMapper::toDto);
    }

    public void delete(Long id) {
        LOG.debug("Request to delete Owner : {}", id);
        ownerRepository.deleteById(id);
    }
}


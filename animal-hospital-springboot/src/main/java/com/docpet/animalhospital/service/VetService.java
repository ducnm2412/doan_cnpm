package com.docpet.animalhospital.service;

import com.docpet.animalhospital.domain.User;
import com.docpet.animalhospital.domain.Vet;
import com.docpet.animalhospital.repository.UserRepository;
import com.docpet.animalhospital.repository.VetRepository;
import com.docpet.animalhospital.service.dto.VetDTO;
import com.docpet.animalhospital.service.mapper.VetMapper;
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
public class VetService {

    private static final Logger LOG = LoggerFactory.getLogger(VetService.class);
    private static final String ENTITY_NAME = "vet";

    private final VetRepository vetRepository;
    private final VetMapper vetMapper;
    private final UserRepository userRepository;

    public VetService(VetRepository vetRepository, VetMapper vetMapper, UserRepository userRepository) {
        this.vetRepository = vetRepository;
        this.vetMapper = vetMapper;
        this.userRepository = userRepository;
    }

    public VetDTO save(VetDTO vetDTO) {
        LOG.debug("Request to save Vet : {}", vetDTO);
        
        // Nếu có userId, kiểm tra xem user đã có vet chưa
        if (vetDTO.getUserId() != null) {
            User user = userRepository.findById(vetDTO.getUserId())
                .orElseThrow(() -> new BadRequestAlertException("User not found", ENTITY_NAME, "usernotfound"));
            
            // Nếu user đã có vet, update vet hiện tại thay vì tạo mới
            Optional<Vet> existingVetOpt = vetRepository.findFirstByUser_Login(user.getLogin());
            if (existingVetOpt.isPresent()) {
                Vet existingVet = existingVetOpt.get();
                if (vetDTO.getLicenseNo() != null) {
                    existingVet.setLicenseNo(vetDTO.getLicenseNo());
                }
                if (vetDTO.getSpecialization() != null) {
                    existingVet.setSpecialization(vetDTO.getSpecialization());
                }
                // Đảm bảo user được set
                existingVet.setUser(user);
                Vet savedVet = vetRepository.save(existingVet);
                
                // Xóa duplicate vets nếu có
                final Long savedVetId = savedVet.getId();
                java.util.List<Vet> duplicateVets = vetRepository.findAllByUser_Login(user.getLogin());
                if (duplicateVets.size() > 1) {
                    duplicateVets.stream()
                        .filter(v -> !v.getId().equals(savedVetId))
                        .forEach(vetRepository::delete);
                    LOG.warn("Deleted {} duplicate vets for user {}", duplicateVets.size() - 1, user.getLogin());
                }
                
                return vetMapper.toDto(savedVet);
            }
        }
        
        // Tạo vet mới
        Vet vet = vetMapper.toEntity(vetDTO);
        
        // Load và set User từ userId
        if (vetDTO.getUserId() != null) {
            User user = userRepository.findById(vetDTO.getUserId())
                .orElseThrow(() -> new BadRequestAlertException("User not found", ENTITY_NAME, "usernotfound"));
            vet.setUser(user);
            LOG.debug("Set user {} to vet", user.getId());
        }
        
        vet = vetRepository.save(vet);
        return vetMapper.toDto(vet);
    }

    public VetDTO update(VetDTO vetDTO) {
        LOG.debug("Request to update Vet : {}", vetDTO);
        
        // Load vet hiện tại từ database để giữ nguyên user nếu không thay đổi
        Vet existingVet = vetRepository.findById(vetDTO.getId())
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
        
        // Update các field từ DTO
        if (vetDTO.getLicenseNo() != null) {
            existingVet.setLicenseNo(vetDTO.getLicenseNo());
        }
        if (vetDTO.getSpecialization() != null) {
            existingVet.setSpecialization(vetDTO.getSpecialization());
        }
        
        // Update user nếu userId được cung cấp
        if (vetDTO.getUserId() != null) {
            User user = userRepository.findById(vetDTO.getUserId())
                .orElseThrow(() -> new BadRequestAlertException("User not found", ENTITY_NAME, "usernotfound"));
            existingVet.setUser(user);
            LOG.debug("Updated user {} for vet {}", user.getId(), existingVet.getId());
        }
        
        Vet savedVet = vetRepository.save(existingVet);
        return vetMapper.toDto(savedVet);
    }

    public Optional<VetDTO> partialUpdate(VetDTO vetDTO) {
        LOG.debug("Request to partially update Vet : {}", vetDTO);
        return vetRepository
            .findById(vetDTO.getId())
            .map(existingVet -> {
                if (vetDTO.getLicenseNo() != null) {
                    existingVet.setLicenseNo(vetDTO.getLicenseNo());
                }
                if (vetDTO.getSpecialization() != null) {
                    existingVet.setSpecialization(vetDTO.getSpecialization());
                }
                return existingVet;
            })
            .map(vetRepository::save)
            .map(vetMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<VetDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Vets");
        return vetRepository.findAll(pageable).map(vetMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<VetDTO> findOne(Long id) {
        LOG.debug("Request to get Vet : {}", id);
        return vetRepository.findById(id).map(vetMapper::toDto);
    }

    public void delete(Long id) {
        LOG.debug("Request to delete Vet : {}", id);
        vetRepository.deleteById(id);
    }
}


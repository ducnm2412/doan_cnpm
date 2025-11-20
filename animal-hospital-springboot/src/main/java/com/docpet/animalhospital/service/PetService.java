package com.docpet.animalhospital.service;

import com.docpet.animalhospital.domain.Owner;
import com.docpet.animalhospital.domain.Pet;
import com.docpet.animalhospital.repository.OwnerRepository;
import com.docpet.animalhospital.repository.PetRepository;
import com.docpet.animalhospital.service.dto.PetDTO;
import com.docpet.animalhospital.service.mapper.PetMapper;
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
public class PetService {

    private static final Logger LOG = LoggerFactory.getLogger(PetService.class);
    private static final String ENTITY_NAME = "pet";

    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final OwnerRepository ownerRepository;

    public PetService(PetRepository petRepository, PetMapper petMapper, OwnerRepository ownerRepository) {
        this.petRepository = petRepository;
        this.petMapper = petMapper;
        this.ownerRepository = ownerRepository;
    }

    public PetDTO save(PetDTO petDTO) {
        LOG.debug("Request to save Pet : {}", petDTO);
        Pet pet = petMapper.toEntity(petDTO);

        // Load và set Owner từ ownerId
        if (petDTO.getOwnerId() != null) {
            Owner owner = ownerRepository.findById(petDTO.getOwnerId())
                .orElseThrow(() -> new BadRequestAlertException("Owner not found", ENTITY_NAME, "ownernotfound"));
            pet.setOwner(owner);
            LOG.debug("Set owner {} to pet", owner.getId());
        }

        pet = petRepository.save(pet);
        
        // Load lại pet với owner để map đúng ownerId trong DTO
        Pet savedPet = petRepository.findByIdWithOwner(pet.getId())
            .orElse(pet);
        
        return petMapper.toDto(savedPet);
    }

    public PetDTO update(PetDTO petDTO) {
        LOG.debug("Request to update Pet : {}", petDTO);
        Pet pet = petMapper.toEntity(petDTO);
        pet = petRepository.save(pet);
        return petMapper.toDto(pet);
    }

    public Optional<PetDTO> partialUpdate(PetDTO petDTO) {
        LOG.debug("Request to partially update Pet : {}", petDTO);
        return petRepository
            .findById(petDTO.getId())
            .map(existingPet -> {
                if (petDTO.getName() != null) {
                    existingPet.setName(petDTO.getName());
                }
                if (petDTO.getSpecies() != null) {
                    existingPet.setSpecies(petDTO.getSpecies());
                }
                if (petDTO.getBreed() != null) {
                    existingPet.setBreed(petDTO.getBreed());
                }
                if (petDTO.getSex() != null) {
                    existingPet.setSex(petDTO.getSex());
                }
                if (petDTO.getDateOfBirth() != null) {
                    existingPet.setDateOfBirth(petDTO.getDateOfBirth());
                }
                if (petDTO.getWeight() != null) {
                    existingPet.setWeight(petDTO.getWeight());
                }
                if (petDTO.getAllergies() != null) {
                    existingPet.setAllergies(petDTO.getAllergies());
                }
                if (petDTO.getNotes() != null) {
                    existingPet.setNotes(petDTO.getNotes());
                }
                if (petDTO.getImageUrl() != null) {
                    existingPet.setImageUrl(petDTO.getImageUrl());
                }
                return existingPet;
            })
            .map(petRepository::save)
            .map(petMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PetDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Pets");
        return petRepository.findAll(pageable).map(petMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<PetDTO> findOne(Long id) {
        LOG.debug("Request to get Pet : {}", id);
        return petRepository.findById(id).map(petMapper::toDto);
    }

    public void delete(Long id) {
        LOG.debug("Request to delete Pet : {}", id);
        petRepository.deleteById(id);
    }
}


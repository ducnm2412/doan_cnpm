package com.docpet.animalhospital.service;

import com.docpet.animalhospital.domain.Pet;
import com.docpet.animalhospital.repository.PetRepository;
import com.docpet.animalhospital.service.dto.PetDTO;
import com.docpet.animalhospital.service.mapper.PetMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.docpet.animalhospital.domain.Pet}.
 */
@Service
@Transactional
public class PetService {

    private static final Logger LOG = LoggerFactory.getLogger(PetService.class);

    private final PetRepository petRepository;

    private final PetMapper petMapper;

    public PetService(PetRepository petRepository, PetMapper petMapper) {
        this.petRepository = petRepository;
        this.petMapper = petMapper;
    }

    /**
     * Save a pet.
     *
     * @param petDTO the entity to save.
     * @return the persisted entity.
     */
    public PetDTO save(PetDTO petDTO) {
        LOG.debug("Request to save Pet : {}", petDTO);
        Pet pet = petMapper.toEntity(petDTO);
        pet = petRepository.save(pet);
        return petMapper.toDto(pet);
    }

    /**
     * Update a pet.
     *
     * @param petDTO the entity to save.
     * @return the persisted entity.
     */
    public PetDTO update(PetDTO petDTO) {
        LOG.debug("Request to update Pet : {}", petDTO);
        Pet pet = petMapper.toEntity(petDTO);
        pet = petRepository.save(pet);
        return petMapper.toDto(pet);
    }

    /**
     * Partially update a pet.
     *
     * @param petDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PetDTO> partialUpdate(PetDTO petDTO) {
        LOG.debug("Request to partially update Pet : {}", petDTO);

        return petRepository
            .findById(petDTO.getId())
            .map(existingPet -> {
                petMapper.partialUpdate(existingPet, petDTO);

                return existingPet;
            })
            .map(petRepository::save)
            .map(petMapper::toDto);
    }

    /**
     * Get all the pets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PetDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Pets");
        return petRepository.findAll(pageable).map(petMapper::toDto);
    }

    /**
     * Get one pet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PetDTO> findOne(Long id) {
        LOG.debug("Request to get Pet : {}", id);
        return petRepository.findById(id).map(petMapper::toDto);
    }

    /**
     * Delete the pet by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Pet : {}", id);
        petRepository.deleteById(id);
    }
}

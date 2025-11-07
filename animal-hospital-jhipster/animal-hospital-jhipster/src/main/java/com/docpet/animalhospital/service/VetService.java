package com.docpet.animalhospital.service;

import com.docpet.animalhospital.domain.Vet;
import com.docpet.animalhospital.repository.VetRepository;
import com.docpet.animalhospital.service.dto.VetDTO;
import com.docpet.animalhospital.service.mapper.VetMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.docpet.animalhospital.domain.Vet}.
 */
@Service
@Transactional
public class VetService {

    private static final Logger LOG = LoggerFactory.getLogger(VetService.class);

    private final VetRepository vetRepository;

    private final VetMapper vetMapper;

    public VetService(VetRepository vetRepository, VetMapper vetMapper) {
        this.vetRepository = vetRepository;
        this.vetMapper = vetMapper;
    }

    /**
     * Save a vet.
     *
     * @param vetDTO the entity to save.
     * @return the persisted entity.
     */
    public VetDTO save(VetDTO vetDTO) {
        LOG.debug("Request to save Vet : {}", vetDTO);
        Vet vet = vetMapper.toEntity(vetDTO);
        vet = vetRepository.save(vet);
        return vetMapper.toDto(vet);
    }

    /**
     * Update a vet.
     *
     * @param vetDTO the entity to save.
     * @return the persisted entity.
     */
    public VetDTO update(VetDTO vetDTO) {
        LOG.debug("Request to update Vet : {}", vetDTO);
        Vet vet = vetMapper.toEntity(vetDTO);
        vet = vetRepository.save(vet);
        return vetMapper.toDto(vet);
    }

    /**
     * Partially update a vet.
     *
     * @param vetDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VetDTO> partialUpdate(VetDTO vetDTO) {
        LOG.debug("Request to partially update Vet : {}", vetDTO);

        return vetRepository
            .findById(vetDTO.getId())
            .map(existingVet -> {
                vetMapper.partialUpdate(existingVet, vetDTO);

                return existingVet;
            })
            .map(vetRepository::save)
            .map(vetMapper::toDto);
    }

    /**
     * Get all the vets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VetDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Vets");
        return vetRepository.findAll(pageable).map(vetMapper::toDto);
    }

    /**
     * Get one vet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VetDTO> findOne(Long id) {
        LOG.debug("Request to get Vet : {}", id);
        return vetRepository.findById(id).map(vetMapper::toDto);
    }

    /**
     * Delete the vet by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Vet : {}", id);
        vetRepository.deleteById(id);
    }
}

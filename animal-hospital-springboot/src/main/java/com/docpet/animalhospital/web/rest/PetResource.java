package com.docpet.animalhospital.web.rest;

import com.docpet.animalhospital.domain.Owner;
import com.docpet.animalhospital.domain.Pet;
import com.docpet.animalhospital.repository.OwnerRepository;
import com.docpet.animalhospital.repository.PetRepository;
import com.docpet.animalhospital.security.SecurityUtils;
import com.docpet.animalhospital.service.PetService;
import com.docpet.animalhospital.service.dto.OwnerDTO;
import com.docpet.animalhospital.service.dto.PetDTO;
import com.docpet.animalhospital.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pets")
public class PetResource {

    private static final Logger LOG = LoggerFactory.getLogger(PetResource.class);
    private static final String ENTITY_NAME = "pet";

    private final PetService petService;
    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    public PetResource(PetService petService, PetRepository petRepository, OwnerRepository ownerRepository) {
        this.petService = petService;
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
    }

    @PostMapping("")
    public ResponseEntity<PetDTO> createPet(@Valid @RequestBody PetDTO petDTO) throws URISyntaxException {
        LOG.debug("REST request to save Pet : {}", petDTO);
        if (petDTO.getId() != null) {
            throw new BadRequestAlertException("A new pet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));
        
        Owner currentOwner = ownerRepository.findByUser_Login(currentUserLogin)
            .orElseThrow(() -> new BadRequestAlertException("Owner profile not found", ENTITY_NAME, "noowner"));
        
        petDTO.setOwnerId(currentOwner.getId());
        
        petDTO = petService.save(petDTO);
        return ResponseEntity.created(new URI("/api/pets/" + petDTO.getId())).body(petDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDTO> updatePet(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PetDTO petDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Pet : {}, {}", id, petDTO);
        if (petDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, petDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!petRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        petDTO = petService.update(petDTO);
        return ResponseEntity.ok().body(petDTO);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PetDTO> partialUpdatePet(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PetDTO petDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Pet partially : {}, {}", id, petDTO);
        if (petDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, petDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!petRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PetDTO> result = petService.partialUpdate(petDTO);
        return result.map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("")
    public ResponseEntity<List<PetDTO>> getAllPets(Pageable pageable) {
        LOG.debug("REST request to get a page of Pets for current user");
        
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("User not authenticated", ENTITY_NAME, "noauth"));
        
        List<Pet> pets = petRepository.findByOwner_User_Login(currentUserLogin);
        List<PetDTO> petDTOs = pets.stream()
            .map(pet -> petService.findOne(pet.getId()).orElse(null))
            .filter(java.util.Objects::nonNull)
            .collect(java.util.stream.Collectors.toList());
            
        return ResponseEntity.ok().body(petDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> getPet(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Pet : {}", id);
        Optional<PetDTO> petDTO = petService.findOne(id);
        return petDTO.map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Pet : {}", id);
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


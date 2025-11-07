package com.docpet.animalhospital.web.rest;

import static com.docpet.animalhospital.domain.PetAsserts.*;
import static com.docpet.animalhospital.web.rest.TestUtil.createUpdateProxyForBean;
import static com.docpet.animalhospital.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.docpet.animalhospital.IntegrationTest;
import com.docpet.animalhospital.domain.Pet;
import com.docpet.animalhospital.repository.PetRepository;
import com.docpet.animalhospital.service.dto.PetDTO;
import com.docpet.animalhospital.service.mapper.PetMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PetResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIES = "AAAAAAAAAA";
    private static final String UPDATED_SPECIES = "BBBBBBBBBB";

    private static final String DEFAULT_BREED = "AAAAAAAAAA";
    private static final String UPDATED_BREED = "BBBBBBBBBB";

    private static final String DEFAULT_SEX = "AAAAAAAAAA";
    private static final String UPDATED_SEX = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_WEIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_WEIGHT = new BigDecimal(2);

    private static final String DEFAULT_ALLERGIES = "AAAAAAAAAA";
    private static final String UPDATED_ALLERGIES = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetMapper petMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPetMockMvc;

    private Pet pet;

    private Pet insertedPet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pet createEntity() {
        return new Pet()
            .name(DEFAULT_NAME)
            .species(DEFAULT_SPECIES)
            .breed(DEFAULT_BREED)
            .sex(DEFAULT_SEX)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .weight(DEFAULT_WEIGHT)
            .allergies(DEFAULT_ALLERGIES)
            .notes(DEFAULT_NOTES)
            .imageUrl(DEFAULT_IMAGE_URL);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pet createUpdatedEntity() {
        return new Pet()
            .name(UPDATED_NAME)
            .species(UPDATED_SPECIES)
            .breed(UPDATED_BREED)
            .sex(UPDATED_SEX)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .weight(UPDATED_WEIGHT)
            .allergies(UPDATED_ALLERGIES)
            .notes(UPDATED_NOTES)
            .imageUrl(UPDATED_IMAGE_URL);
    }

    @BeforeEach
    void initTest() {
        pet = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPet != null) {
            petRepository.delete(insertedPet);
            insertedPet = null;
        }
    }

    @Test
    @Transactional
    void createPet() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Pet
        PetDTO petDTO = petMapper.toDto(pet);
        var returnedPetDTO = om.readValue(
            restPetMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(petDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PetDTO.class
        );

        // Validate the Pet in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPet = petMapper.toEntity(returnedPetDTO);
        assertPetUpdatableFieldsEquals(returnedPet, getPersistedPet(returnedPet));

        insertedPet = returnedPet;
    }

    @Test
    @Transactional
    void createPetWithExistingId() throws Exception {
        // Create the Pet with an existing ID
        pet.setId(1L);
        PetDTO petDTO = petMapper.toDto(pet);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(petDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pet in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pet.setName(null);

        // Create the Pet, which fails.
        PetDTO petDTO = petMapper.toDto(pet);

        restPetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(petDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSpeciesIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pet.setSpecies(null);

        // Create the Pet, which fails.
        PetDTO petDTO = petMapper.toDto(pet);

        restPetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(petDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPets() throws Exception {
        // Initialize the database
        insertedPet = petRepository.saveAndFlush(pet);

        // Get all the petList
        restPetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].species").value(hasItem(DEFAULT_SPECIES)))
            .andExpect(jsonPath("$.[*].breed").value(hasItem(DEFAULT_BREED)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(sameNumber(DEFAULT_WEIGHT))))
            .andExpect(jsonPath("$.[*].allergies").value(hasItem(DEFAULT_ALLERGIES)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)));
    }

    @Test
    @Transactional
    void getPet() throws Exception {
        // Initialize the database
        insertedPet = petRepository.saveAndFlush(pet);

        // Get the pet
        restPetMockMvc
            .perform(get(ENTITY_API_URL_ID, pet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pet.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.species").value(DEFAULT_SPECIES))
            .andExpect(jsonPath("$.breed").value(DEFAULT_BREED))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.weight").value(sameNumber(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.allergies").value(DEFAULT_ALLERGIES))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL));
    }

    @Test
    @Transactional
    void getNonExistingPet() throws Exception {
        // Get the pet
        restPetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPet() throws Exception {
        // Initialize the database
        insertedPet = petRepository.saveAndFlush(pet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pet
        Pet updatedPet = petRepository.findById(pet.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPet are not directly saved in db
        em.detach(updatedPet);
        updatedPet
            .name(UPDATED_NAME)
            .species(UPDATED_SPECIES)
            .breed(UPDATED_BREED)
            .sex(UPDATED_SEX)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .weight(UPDATED_WEIGHT)
            .allergies(UPDATED_ALLERGIES)
            .notes(UPDATED_NOTES)
            .imageUrl(UPDATED_IMAGE_URL);
        PetDTO petDTO = petMapper.toDto(updatedPet);

        restPetMockMvc
            .perform(put(ENTITY_API_URL_ID, petDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(petDTO)))
            .andExpect(status().isOk());

        // Validate the Pet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPetToMatchAllProperties(updatedPet);
    }

    @Test
    @Transactional
    void putNonExistingPet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pet.setId(longCount.incrementAndGet());

        // Create the Pet
        PetDTO petDTO = petMapper.toDto(pet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPetMockMvc
            .perform(put(ENTITY_API_URL_ID, petDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(petDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pet.setId(longCount.incrementAndGet());

        // Create the Pet
        PetDTO petDTO = petMapper.toDto(pet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(petDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pet.setId(longCount.incrementAndGet());

        // Create the Pet
        PetDTO petDTO = petMapper.toDto(pet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(petDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePetWithPatch() throws Exception {
        // Initialize the database
        insertedPet = petRepository.saveAndFlush(pet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pet using partial update
        Pet partialUpdatedPet = new Pet();
        partialUpdatedPet.setId(pet.getId());

        partialUpdatedPet
            .species(UPDATED_SPECIES)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .allergies(UPDATED_ALLERGIES)
            .notes(UPDATED_NOTES)
            .imageUrl(UPDATED_IMAGE_URL);

        restPetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPet.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPet))
            )
            .andExpect(status().isOk());

        // Validate the Pet in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPetUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPet, pet), getPersistedPet(pet));
    }

    @Test
    @Transactional
    void fullUpdatePetWithPatch() throws Exception {
        // Initialize the database
        insertedPet = petRepository.saveAndFlush(pet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pet using partial update
        Pet partialUpdatedPet = new Pet();
        partialUpdatedPet.setId(pet.getId());

        partialUpdatedPet
            .name(UPDATED_NAME)
            .species(UPDATED_SPECIES)
            .breed(UPDATED_BREED)
            .sex(UPDATED_SEX)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .weight(UPDATED_WEIGHT)
            .allergies(UPDATED_ALLERGIES)
            .notes(UPDATED_NOTES)
            .imageUrl(UPDATED_IMAGE_URL);

        restPetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPet.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPet))
            )
            .andExpect(status().isOk());

        // Validate the Pet in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPetUpdatableFieldsEquals(partialUpdatedPet, getPersistedPet(partialUpdatedPet));
    }

    @Test
    @Transactional
    void patchNonExistingPet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pet.setId(longCount.incrementAndGet());

        // Create the Pet
        PetDTO petDTO = petMapper.toDto(pet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, petDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(petDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pet.setId(longCount.incrementAndGet());

        // Create the Pet
        PetDTO petDTO = petMapper.toDto(pet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(petDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pet.setId(longCount.incrementAndGet());

        // Create the Pet
        PetDTO petDTO = petMapper.toDto(pet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPetMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(petDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePet() throws Exception {
        // Initialize the database
        insertedPet = petRepository.saveAndFlush(pet);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pet
        restPetMockMvc.perform(delete(ENTITY_API_URL_ID, pet.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return petRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Pet getPersistedPet(Pet pet) {
        return petRepository.findById(pet.getId()).orElseThrow();
    }

    protected void assertPersistedPetToMatchAllProperties(Pet expectedPet) {
        assertPetAllPropertiesEquals(expectedPet, getPersistedPet(expectedPet));
    }

    protected void assertPersistedPetToMatchUpdatableProperties(Pet expectedPet) {
        assertPetAllUpdatablePropertiesEquals(expectedPet, getPersistedPet(expectedPet));
    }
}

package com.docpet.animalhospital.web.rest;

import static com.docpet.animalhospital.domain.VetAsserts.*;
import static com.docpet.animalhospital.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.docpet.animalhospital.IntegrationTest;
import com.docpet.animalhospital.domain.Vet;
import com.docpet.animalhospital.repository.UserRepository;
import com.docpet.animalhospital.repository.VetRepository;
import com.docpet.animalhospital.service.dto.VetDTO;
import com.docpet.animalhospital.service.mapper.VetMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link VetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VetResourceIT {

    private static final String DEFAULT_LICENSE_NO = "AAAAAAAAAA";
    private static final String UPDATED_LICENSE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIALIZATION = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALIZATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VetRepository vetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VetMapper vetMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVetMockMvc;

    private Vet vet;

    private Vet insertedVet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vet createEntity() {
        return new Vet().licenseNo(DEFAULT_LICENSE_NO).specialization(DEFAULT_SPECIALIZATION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vet createUpdatedEntity() {
        return new Vet().licenseNo(UPDATED_LICENSE_NO).specialization(UPDATED_SPECIALIZATION);
    }

    @BeforeEach
    void initTest() {
        vet = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedVet != null) {
            vetRepository.delete(insertedVet);
            insertedVet = null;
        }
    }

    @Test
    @Transactional
    void createVet() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vet
        VetDTO vetDTO = vetMapper.toDto(vet);
        var returnedVetDTO = om.readValue(
            restVetMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vetDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            VetDTO.class
        );

        // Validate the Vet in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedVet = vetMapper.toEntity(returnedVetDTO);
        assertVetUpdatableFieldsEquals(returnedVet, getPersistedVet(returnedVet));

        insertedVet = returnedVet;
    }

    @Test
    @Transactional
    void createVetWithExistingId() throws Exception {
        // Create the Vet with an existing ID
        vet.setId(1L);
        VetDTO vetDTO = vetMapper.toDto(vet);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vet in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLicenseNoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        vet.setLicenseNo(null);

        // Create the Vet, which fails.
        VetDTO vetDTO = vetMapper.toDto(vet);

        restVetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vetDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVets() throws Exception {
        // Initialize the database
        insertedVet = vetRepository.saveAndFlush(vet);

        // Get all the vetList
        restVetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vet.getId().intValue())))
            .andExpect(jsonPath("$.[*].licenseNo").value(hasItem(DEFAULT_LICENSE_NO)))
            .andExpect(jsonPath("$.[*].specialization").value(hasItem(DEFAULT_SPECIALIZATION)));
    }

    @Test
    @Transactional
    void getVet() throws Exception {
        // Initialize the database
        insertedVet = vetRepository.saveAndFlush(vet);

        // Get the vet
        restVetMockMvc
            .perform(get(ENTITY_API_URL_ID, vet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vet.getId().intValue()))
            .andExpect(jsonPath("$.licenseNo").value(DEFAULT_LICENSE_NO))
            .andExpect(jsonPath("$.specialization").value(DEFAULT_SPECIALIZATION));
    }

    @Test
    @Transactional
    void getNonExistingVet() throws Exception {
        // Get the vet
        restVetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVet() throws Exception {
        // Initialize the database
        insertedVet = vetRepository.saveAndFlush(vet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vet
        Vet updatedVet = vetRepository.findById(vet.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVet are not directly saved in db
        em.detach(updatedVet);
        updatedVet.licenseNo(UPDATED_LICENSE_NO).specialization(UPDATED_SPECIALIZATION);
        VetDTO vetDTO = vetMapper.toDto(updatedVet);

        restVetMockMvc
            .perform(put(ENTITY_API_URL_ID, vetDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vetDTO)))
            .andExpect(status().isOk());

        // Validate the Vet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVetToMatchAllProperties(updatedVet);
    }

    @Test
    @Transactional
    void putNonExistingVet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vet.setId(longCount.incrementAndGet());

        // Create the Vet
        VetDTO vetDTO = vetMapper.toDto(vet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVetMockMvc
            .perform(put(ENTITY_API_URL_ID, vetDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vet.setId(longCount.incrementAndGet());

        // Create the Vet
        VetDTO vetDTO = vetMapper.toDto(vet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vet.setId(longCount.incrementAndGet());

        // Create the Vet
        VetDTO vetDTO = vetMapper.toDto(vet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vetDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVetWithPatch() throws Exception {
        // Initialize the database
        insertedVet = vetRepository.saveAndFlush(vet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vet using partial update
        Vet partialUpdatedVet = new Vet();
        partialUpdatedVet.setId(vet.getId());

        restVetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVet.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVet))
            )
            .andExpect(status().isOk());

        // Validate the Vet in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVetUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedVet, vet), getPersistedVet(vet));
    }

    @Test
    @Transactional
    void fullUpdateVetWithPatch() throws Exception {
        // Initialize the database
        insertedVet = vetRepository.saveAndFlush(vet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vet using partial update
        Vet partialUpdatedVet = new Vet();
        partialUpdatedVet.setId(vet.getId());

        partialUpdatedVet.licenseNo(UPDATED_LICENSE_NO).specialization(UPDATED_SPECIALIZATION);

        restVetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVet.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVet))
            )
            .andExpect(status().isOk());

        // Validate the Vet in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVetUpdatableFieldsEquals(partialUpdatedVet, getPersistedVet(partialUpdatedVet));
    }

    @Test
    @Transactional
    void patchNonExistingVet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vet.setId(longCount.incrementAndGet());

        // Create the Vet
        VetDTO vetDTO = vetMapper.toDto(vet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vetDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vet.setId(longCount.incrementAndGet());

        // Create the Vet
        VetDTO vetDTO = vetMapper.toDto(vet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vet.setId(longCount.incrementAndGet());

        // Create the Vet
        VetDTO vetDTO = vetMapper.toDto(vet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVetMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vetDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVet() throws Exception {
        // Initialize the database
        insertedVet = vetRepository.saveAndFlush(vet);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vet
        restVetMockMvc.perform(delete(ENTITY_API_URL_ID, vet.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vetRepository.count();
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

    protected Vet getPersistedVet(Vet vet) {
        return vetRepository.findById(vet.getId()).orElseThrow();
    }

    protected void assertPersistedVetToMatchAllProperties(Vet expectedVet) {
        assertVetAllPropertiesEquals(expectedVet, getPersistedVet(expectedVet));
    }

    protected void assertPersistedVetToMatchUpdatableProperties(Vet expectedVet) {
        assertVetAllUpdatablePropertiesEquals(expectedVet, getPersistedVet(expectedVet));
    }
}

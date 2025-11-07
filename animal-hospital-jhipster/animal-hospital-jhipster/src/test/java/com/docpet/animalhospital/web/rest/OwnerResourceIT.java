package com.docpet.animalhospital.web.rest;

import static com.docpet.animalhospital.domain.OwnerAsserts.*;
import static com.docpet.animalhospital.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.docpet.animalhospital.IntegrationTest;
import com.docpet.animalhospital.domain.Owner;
import com.docpet.animalhospital.repository.OwnerRepository;
import com.docpet.animalhospital.repository.UserRepository;
import com.docpet.animalhospital.service.dto.OwnerDTO;
import com.docpet.animalhospital.service.mapper.OwnerMapper;
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
 * Integration tests for the {@link OwnerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OwnerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/owners";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OwnerMapper ownerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOwnerMockMvc;

    private Owner owner;

    private Owner insertedOwner;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Owner createEntity() {
        return new Owner().name(DEFAULT_NAME).phone(DEFAULT_PHONE).address(DEFAULT_ADDRESS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Owner createUpdatedEntity() {
        return new Owner().name(UPDATED_NAME).phone(UPDATED_PHONE).address(UPDATED_ADDRESS);
    }

    @BeforeEach
    void initTest() {
        owner = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedOwner != null) {
            ownerRepository.delete(insertedOwner);
            insertedOwner = null;
        }
    }

    @Test
    @Transactional
    void createOwner() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Owner
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);
        var returnedOwnerDTO = om.readValue(
            restOwnerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ownerDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OwnerDTO.class
        );

        // Validate the Owner in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedOwner = ownerMapper.toEntity(returnedOwnerDTO);
        assertOwnerUpdatableFieldsEquals(returnedOwner, getPersistedOwner(returnedOwner));

        insertedOwner = returnedOwner;
    }

    @Test
    @Transactional
    void createOwnerWithExistingId() throws Exception {
        // Create the Owner with an existing ID
        owner.setId(1L);
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOwnerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ownerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Owner in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        owner.setName(null);

        // Create the Owner, which fails.
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        restOwnerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ownerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhoneIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        owner.setPhone(null);

        // Create the Owner, which fails.
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        restOwnerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ownerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        owner.setAddress(null);

        // Create the Owner, which fails.
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        restOwnerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ownerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOwners() throws Exception {
        // Initialize the database
        insertedOwner = ownerRepository.saveAndFlush(owner);

        // Get all the ownerList
        restOwnerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(owner.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }

    @Test
    @Transactional
    void getOwner() throws Exception {
        // Initialize the database
        insertedOwner = ownerRepository.saveAndFlush(owner);

        // Get the owner
        restOwnerMockMvc
            .perform(get(ENTITY_API_URL_ID, owner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(owner.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    @Transactional
    void getNonExistingOwner() throws Exception {
        // Get the owner
        restOwnerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOwner() throws Exception {
        // Initialize the database
        insertedOwner = ownerRepository.saveAndFlush(owner);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the owner
        Owner updatedOwner = ownerRepository.findById(owner.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOwner are not directly saved in db
        em.detach(updatedOwner);
        updatedOwner.name(UPDATED_NAME).phone(UPDATED_PHONE).address(UPDATED_ADDRESS);
        OwnerDTO ownerDTO = ownerMapper.toDto(updatedOwner);

        restOwnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ownerDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ownerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Owner in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOwnerToMatchAllProperties(updatedOwner);
    }

    @Test
    @Transactional
    void putNonExistingOwner() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        owner.setId(longCount.incrementAndGet());

        // Create the Owner
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOwnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ownerDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ownerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Owner in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOwner() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        owner.setId(longCount.incrementAndGet());

        // Create the Owner
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOwnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ownerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Owner in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOwner() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        owner.setId(longCount.incrementAndGet());

        // Create the Owner
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOwnerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ownerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Owner in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOwnerWithPatch() throws Exception {
        // Initialize the database
        insertedOwner = ownerRepository.saveAndFlush(owner);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the owner using partial update
        Owner partialUpdatedOwner = new Owner();
        partialUpdatedOwner.setId(owner.getId());

        partialUpdatedOwner.name(UPDATED_NAME).address(UPDATED_ADDRESS);

        restOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOwner.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOwner))
            )
            .andExpect(status().isOk());

        // Validate the Owner in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOwnerUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedOwner, owner), getPersistedOwner(owner));
    }

    @Test
    @Transactional
    void fullUpdateOwnerWithPatch() throws Exception {
        // Initialize the database
        insertedOwner = ownerRepository.saveAndFlush(owner);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the owner using partial update
        Owner partialUpdatedOwner = new Owner();
        partialUpdatedOwner.setId(owner.getId());

        partialUpdatedOwner.name(UPDATED_NAME).phone(UPDATED_PHONE).address(UPDATED_ADDRESS);

        restOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOwner.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOwner))
            )
            .andExpect(status().isOk());

        // Validate the Owner in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOwnerUpdatableFieldsEquals(partialUpdatedOwner, getPersistedOwner(partialUpdatedOwner));
    }

    @Test
    @Transactional
    void patchNonExistingOwner() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        owner.setId(longCount.incrementAndGet());

        // Create the Owner
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ownerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ownerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Owner in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOwner() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        owner.setId(longCount.incrementAndGet());

        // Create the Owner
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ownerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Owner in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOwner() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        owner.setId(longCount.incrementAndGet());

        // Create the Owner
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOwnerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ownerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Owner in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOwner() throws Exception {
        // Initialize the database
        insertedOwner = ownerRepository.saveAndFlush(owner);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the owner
        restOwnerMockMvc
            .perform(delete(ENTITY_API_URL_ID, owner.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ownerRepository.count();
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

    protected Owner getPersistedOwner(Owner owner) {
        return ownerRepository.findById(owner.getId()).orElseThrow();
    }

    protected void assertPersistedOwnerToMatchAllProperties(Owner expectedOwner) {
        assertOwnerAllPropertiesEquals(expectedOwner, getPersistedOwner(expectedOwner));
    }

    protected void assertPersistedOwnerToMatchUpdatableProperties(Owner expectedOwner) {
        assertOwnerAllUpdatablePropertiesEquals(expectedOwner, getPersistedOwner(expectedOwner));
    }
}

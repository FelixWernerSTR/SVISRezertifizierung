package de.svi.svis5g.aktuelles.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.svi.svis5g.aktuelles.IntegrationTest;
import de.svi.svis5g.aktuelles.domain.Thementyp;
import de.svi.svis5g.aktuelles.repository.ThementypRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ThementypResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ThementypResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/thementyps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ThementypRepository thementypRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThementypMockMvc;

    private Thementyp thementyp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thementyp createEntity(EntityManager em) {
        Thementyp thementyp = new Thementyp().name(DEFAULT_NAME);
        return thementyp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thementyp createUpdatedEntity(EntityManager em) {
        Thementyp thementyp = new Thementyp().name(UPDATED_NAME);
        return thementyp;
    }

    @BeforeEach
    public void initTest() {
        thementyp = createEntity(em);
    }

    @Test
    @Transactional
    void createThementyp() throws Exception {
        int databaseSizeBeforeCreate = thementypRepository.findAll().size();
        // Create the Thementyp
        restThementypMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thementyp))
            )
            .andExpect(status().isCreated());

        // Validate the Thementyp in the database
        List<Thementyp> thementypList = thementypRepository.findAll();
        assertThat(thementypList).hasSize(databaseSizeBeforeCreate + 1);
        Thementyp testThementyp = thementypList.get(thementypList.size() - 1);
        assertThat(testThementyp.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createThementypWithExistingId() throws Exception {
        // Create the Thementyp with an existing ID
        thementyp.setId(1L);

        int databaseSizeBeforeCreate = thementypRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThementypMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thementyp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thementyp in the database
        List<Thementyp> thementypList = thementypRepository.findAll();
        assertThat(thementypList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = thementypRepository.findAll().size();
        // set the field null
        thementyp.setName(null);

        // Create the Thementyp, which fails.

        restThementypMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thementyp))
            )
            .andExpect(status().isBadRequest());

        List<Thementyp> thementypList = thementypRepository.findAll();
        assertThat(thementypList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllThementyps() throws Exception {
        // Initialize the database
        thementypRepository.saveAndFlush(thementyp);

        // Get all the thementypList
        restThementypMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thementyp.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getThementyp() throws Exception {
        // Initialize the database
        thementypRepository.saveAndFlush(thementyp);

        // Get the thementyp
        restThementypMockMvc
            .perform(get(ENTITY_API_URL_ID, thementyp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thementyp.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingThementyp() throws Exception {
        // Get the thementyp
        restThementypMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewThementyp() throws Exception {
        // Initialize the database
        thementypRepository.saveAndFlush(thementyp);

        int databaseSizeBeforeUpdate = thementypRepository.findAll().size();

        // Update the thementyp
        Thementyp updatedThementyp = thementypRepository.findById(thementyp.getId()).get();
        // Disconnect from session so that the updates on updatedThementyp are not directly saved in db
        em.detach(updatedThementyp);
        updatedThementyp.name(UPDATED_NAME);

        restThementypMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedThementyp.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedThementyp))
            )
            .andExpect(status().isOk());

        // Validate the Thementyp in the database
        List<Thementyp> thementypList = thementypRepository.findAll();
        assertThat(thementypList).hasSize(databaseSizeBeforeUpdate);
        Thementyp testThementyp = thementypList.get(thementypList.size() - 1);
        assertThat(testThementyp.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingThementyp() throws Exception {
        int databaseSizeBeforeUpdate = thementypRepository.findAll().size();
        thementyp.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThementypMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thementyp.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thementyp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thementyp in the database
        List<Thementyp> thementypList = thementypRepository.findAll();
        assertThat(thementypList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchThementyp() throws Exception {
        int databaseSizeBeforeUpdate = thementypRepository.findAll().size();
        thementyp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThementypMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thementyp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thementyp in the database
        List<Thementyp> thementypList = thementypRepository.findAll();
        assertThat(thementypList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamThementyp() throws Exception {
        int databaseSizeBeforeUpdate = thementypRepository.findAll().size();
        thementyp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThementypMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thementyp))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Thementyp in the database
        List<Thementyp> thementypList = thementypRepository.findAll();
        assertThat(thementypList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateThementypWithPatch() throws Exception {
        // Initialize the database
        thementypRepository.saveAndFlush(thementyp);

        int databaseSizeBeforeUpdate = thementypRepository.findAll().size();

        // Update the thementyp using partial update
        Thementyp partialUpdatedThementyp = new Thementyp();
        partialUpdatedThementyp.setId(thementyp.getId());

        restThementypMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThementyp.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThementyp))
            )
            .andExpect(status().isOk());

        // Validate the Thementyp in the database
        List<Thementyp> thementypList = thementypRepository.findAll();
        assertThat(thementypList).hasSize(databaseSizeBeforeUpdate);
        Thementyp testThementyp = thementypList.get(thementypList.size() - 1);
        assertThat(testThementyp.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateThementypWithPatch() throws Exception {
        // Initialize the database
        thementypRepository.saveAndFlush(thementyp);

        int databaseSizeBeforeUpdate = thementypRepository.findAll().size();

        // Update the thementyp using partial update
        Thementyp partialUpdatedThementyp = new Thementyp();
        partialUpdatedThementyp.setId(thementyp.getId());

        partialUpdatedThementyp.name(UPDATED_NAME);

        restThementypMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThementyp.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThementyp))
            )
            .andExpect(status().isOk());

        // Validate the Thementyp in the database
        List<Thementyp> thementypList = thementypRepository.findAll();
        assertThat(thementypList).hasSize(databaseSizeBeforeUpdate);
        Thementyp testThementyp = thementypList.get(thementypList.size() - 1);
        assertThat(testThementyp.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingThementyp() throws Exception {
        int databaseSizeBeforeUpdate = thementypRepository.findAll().size();
        thementyp.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThementypMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, thementyp.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thementyp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thementyp in the database
        List<Thementyp> thementypList = thementypRepository.findAll();
        assertThat(thementypList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchThementyp() throws Exception {
        int databaseSizeBeforeUpdate = thementypRepository.findAll().size();
        thementyp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThementypMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thementyp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thementyp in the database
        List<Thementyp> thementypList = thementypRepository.findAll();
        assertThat(thementypList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamThementyp() throws Exception {
        int databaseSizeBeforeUpdate = thementypRepository.findAll().size();
        thementyp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThementypMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thementyp))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Thementyp in the database
        List<Thementyp> thementypList = thementypRepository.findAll();
        assertThat(thementypList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteThementyp() throws Exception {
        // Initialize the database
        thementypRepository.saveAndFlush(thementyp);

        int databaseSizeBeforeDelete = thementypRepository.findAll().size();

        // Delete the thementyp
        restThementypMockMvc
            .perform(delete(ENTITY_API_URL_ID, thementyp.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Thementyp> thementypList = thementypRepository.findAll();
        assertThat(thementypList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package de.svi.svis5g.aktuelles.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.svi.svis5g.aktuelles.IntegrationTest;
import de.svi.svis5g.aktuelles.domain.Thema;
import de.svi.svis5g.aktuelles.repository.ThemaRepository;
import de.svi.svis5g.aktuelles.service.ThemaService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ThemaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ThemaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RECHTE = "AAAAAAAAAA";
    private static final String UPDATED_RECHTE = "BBBBBBBBBB";

    private static final Long DEFAULT_DISPLAYCOUNT = 0L;
    private static final Long UPDATED_DISPLAYCOUNT = 1L;

    private static final String ENTITY_API_URL = "/api/themas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ThemaRepository themaRepository;

    @Mock
    private ThemaRepository themaRepositoryMock;

    @Mock
    private ThemaService themaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThemaMockMvc;

    private Thema thema;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thema createEntity(EntityManager em) {
        Thema thema = new Thema().name(DEFAULT_NAME).rechte(DEFAULT_RECHTE).displaycount(DEFAULT_DISPLAYCOUNT);
        return thema;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thema createUpdatedEntity(EntityManager em) {
        Thema thema = new Thema().name(UPDATED_NAME).rechte(UPDATED_RECHTE).displaycount(UPDATED_DISPLAYCOUNT);
        return thema;
    }

    @BeforeEach
    public void initTest() {
        thema = createEntity(em);
    }

    @Test
    @Transactional
    void createThema() throws Exception {
        int databaseSizeBeforeCreate = themaRepository.findAll().size();
        // Create the Thema
        restThemaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thema))
            )
            .andExpect(status().isCreated());

        // Validate the Thema in the database
        List<Thema> themaList = themaRepository.findAll();
        assertThat(themaList).hasSize(databaseSizeBeforeCreate + 1);
        Thema testThema = themaList.get(themaList.size() - 1);
        assertThat(testThema.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testThema.getRechte()).isEqualTo(DEFAULT_RECHTE);
        assertThat(testThema.getDisplaycount()).isEqualTo(DEFAULT_DISPLAYCOUNT);
    }

    @Test
    @Transactional
    void createThemaWithExistingId() throws Exception {
        // Create the Thema with an existing ID
        thema.setId(1L);

        int databaseSizeBeforeCreate = themaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThemaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thema in the database
        List<Thema> themaList = themaRepository.findAll();
        assertThat(themaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = themaRepository.findAll().size();
        // set the field null
        thema.setName(null);

        // Create the Thema, which fails.

        restThemaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thema))
            )
            .andExpect(status().isBadRequest());

        List<Thema> themaList = themaRepository.findAll();
        assertThat(themaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllThemas() throws Exception {
        // Initialize the database
        themaRepository.saveAndFlush(thema);

        // Get all the themaList
        restThemaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thema.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].rechte").value(hasItem(DEFAULT_RECHTE)))
            .andExpect(jsonPath("$.[*].displaycount").value(hasItem(DEFAULT_DISPLAYCOUNT.intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllThemasWithEagerRelationshipsIsEnabled() throws Exception {
        when(themaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restThemaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(themaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllThemasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(themaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restThemaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(themaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getThema() throws Exception {
        // Initialize the database
        themaRepository.saveAndFlush(thema);

        // Get the thema
        restThemaMockMvc
            .perform(get(ENTITY_API_URL_ID, thema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thema.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.rechte").value(DEFAULT_RECHTE))
            .andExpect(jsonPath("$.displaycount").value(DEFAULT_DISPLAYCOUNT.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingThema() throws Exception {
        // Get the thema
        restThemaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewThema() throws Exception {
        // Initialize the database
        themaRepository.saveAndFlush(thema);

        int databaseSizeBeforeUpdate = themaRepository.findAll().size();

        // Update the thema
        Thema updatedThema = themaRepository.findById(thema.getId()).get();
        // Disconnect from session so that the updates on updatedThema are not directly saved in db
        em.detach(updatedThema);
        updatedThema.name(UPDATED_NAME).rechte(UPDATED_RECHTE).displaycount(UPDATED_DISPLAYCOUNT);

        restThemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedThema.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedThema))
            )
            .andExpect(status().isOk());

        // Validate the Thema in the database
        List<Thema> themaList = themaRepository.findAll();
        assertThat(themaList).hasSize(databaseSizeBeforeUpdate);
        Thema testThema = themaList.get(themaList.size() - 1);
        assertThat(testThema.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testThema.getRechte()).isEqualTo(UPDATED_RECHTE);
        assertThat(testThema.getDisplaycount()).isEqualTo(UPDATED_DISPLAYCOUNT);
    }

    @Test
    @Transactional
    void putNonExistingThema() throws Exception {
        int databaseSizeBeforeUpdate = themaRepository.findAll().size();
        thema.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thema.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thema in the database
        List<Thema> themaList = themaRepository.findAll();
        assertThat(themaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchThema() throws Exception {
        int databaseSizeBeforeUpdate = themaRepository.findAll().size();
        thema.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thema in the database
        List<Thema> themaList = themaRepository.findAll();
        assertThat(themaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamThema() throws Exception {
        int databaseSizeBeforeUpdate = themaRepository.findAll().size();
        thema.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThemaMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thema))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Thema in the database
        List<Thema> themaList = themaRepository.findAll();
        assertThat(themaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateThemaWithPatch() throws Exception {
        // Initialize the database
        themaRepository.saveAndFlush(thema);

        int databaseSizeBeforeUpdate = themaRepository.findAll().size();

        // Update the thema using partial update
        Thema partialUpdatedThema = new Thema();
        partialUpdatedThema.setId(thema.getId());

        partialUpdatedThema.displaycount(UPDATED_DISPLAYCOUNT);

        restThemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThema.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThema))
            )
            .andExpect(status().isOk());

        // Validate the Thema in the database
        List<Thema> themaList = themaRepository.findAll();
        assertThat(themaList).hasSize(databaseSizeBeforeUpdate);
        Thema testThema = themaList.get(themaList.size() - 1);
        assertThat(testThema.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testThema.getRechte()).isEqualTo(DEFAULT_RECHTE);
        assertThat(testThema.getDisplaycount()).isEqualTo(UPDATED_DISPLAYCOUNT);
    }

    @Test
    @Transactional
    void fullUpdateThemaWithPatch() throws Exception {
        // Initialize the database
        themaRepository.saveAndFlush(thema);

        int databaseSizeBeforeUpdate = themaRepository.findAll().size();

        // Update the thema using partial update
        Thema partialUpdatedThema = new Thema();
        partialUpdatedThema.setId(thema.getId());

        partialUpdatedThema.name(UPDATED_NAME).rechte(UPDATED_RECHTE).displaycount(UPDATED_DISPLAYCOUNT);

        restThemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThema.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThema))
            )
            .andExpect(status().isOk());

        // Validate the Thema in the database
        List<Thema> themaList = themaRepository.findAll();
        assertThat(themaList).hasSize(databaseSizeBeforeUpdate);
        Thema testThema = themaList.get(themaList.size() - 1);
        assertThat(testThema.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testThema.getRechte()).isEqualTo(UPDATED_RECHTE);
        assertThat(testThema.getDisplaycount()).isEqualTo(UPDATED_DISPLAYCOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingThema() throws Exception {
        int databaseSizeBeforeUpdate = themaRepository.findAll().size();
        thema.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, thema.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thema in the database
        List<Thema> themaList = themaRepository.findAll();
        assertThat(themaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchThema() throws Exception {
        int databaseSizeBeforeUpdate = themaRepository.findAll().size();
        thema.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thema in the database
        List<Thema> themaList = themaRepository.findAll();
        assertThat(themaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamThema() throws Exception {
        int databaseSizeBeforeUpdate = themaRepository.findAll().size();
        thema.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThemaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thema))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Thema in the database
        List<Thema> themaList = themaRepository.findAll();
        assertThat(themaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteThema() throws Exception {
        // Initialize the database
        themaRepository.saveAndFlush(thema);

        int databaseSizeBeforeDelete = themaRepository.findAll().size();

        // Delete the thema
        restThemaMockMvc
            .perform(delete(ENTITY_API_URL_ID, thema.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Thema> themaList = themaRepository.findAll();
        assertThat(themaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

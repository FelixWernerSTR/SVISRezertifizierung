package de.svi.svis5g.aktuelles.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.svi.svis5g.aktuelles.IntegrationTest;
import de.svi.svis5g.aktuelles.domain.Beitrag;
import de.svi.svis5g.aktuelles.domain.enumeration.Archiv;
import de.svi.svis5g.aktuelles.repository.BeitragRepository;
import de.svi.svis5g.aktuelles.service.BeitragService;
import java.time.LocalDate;
import java.time.ZoneId;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link BeitragResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BeitragResourceIT {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIB = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIB = "BBBBBBBBBB";

    private static final String DEFAULT_TITEL = "AAAAAAAAAA";
    private static final String UPDATED_TITEL = "BBBBBBBBBB";

    private static final String DEFAULT_RECHTE = "AAAAAAAAAA";
    private static final String UPDATED_RECHTE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PUBLISH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PUBLISH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Archiv DEFAULT_ARCHIV = Archiv.JA;
    private static final Archiv UPDATED_ARCHIV = Archiv.NEIN;

    private static final String ENTITY_API_URL = "/api/beitrags";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BeitragRepository beitragRepository;

    @Mock
    private BeitragRepository beitragRepositoryMock;

    @Mock
    private BeitragService beitragServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBeitragMockMvc;

    private Beitrag beitrag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beitrag createEntity(EntityManager em) {
        Beitrag beitrag = new Beitrag()
            .content(DEFAULT_CONTENT)
            .attrib(DEFAULT_ATTRIB)
            .titel(DEFAULT_TITEL)
            .rechte(DEFAULT_RECHTE)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO)
            .publishDate(DEFAULT_PUBLISH_DATE)
            .archiv(DEFAULT_ARCHIV);
        return beitrag;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beitrag createUpdatedEntity(EntityManager em) {
        Beitrag beitrag = new Beitrag()
            .content(UPDATED_CONTENT)
            .attrib(UPDATED_ATTRIB)
            .titel(UPDATED_TITEL)
            .rechte(UPDATED_RECHTE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .publishDate(UPDATED_PUBLISH_DATE)
            .archiv(UPDATED_ARCHIV);
        return beitrag;
    }

    @BeforeEach
    public void initTest() {
        beitrag = createEntity(em);
    }

    @Test
    @Transactional
    void createBeitrag() throws Exception {
        int databaseSizeBeforeCreate = beitragRepository.findAll().size();
        // Create the Beitrag
        restBeitragMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(beitrag))
            )
            .andExpect(status().isCreated());

        // Validate the Beitrag in the database
        List<Beitrag> beitragList = beitragRepository.findAll();
        assertThat(beitragList).hasSize(databaseSizeBeforeCreate + 1);
        Beitrag testBeitrag = beitragList.get(beitragList.size() - 1);
        assertThat(testBeitrag.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testBeitrag.getAttrib()).isEqualTo(DEFAULT_ATTRIB);
        assertThat(testBeitrag.getTitel()).isEqualTo(DEFAULT_TITEL);
        assertThat(testBeitrag.getRechte()).isEqualTo(DEFAULT_RECHTE);
        assertThat(testBeitrag.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testBeitrag.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testBeitrag.getPublishDate()).isEqualTo(DEFAULT_PUBLISH_DATE);
        assertThat(testBeitrag.getArchiv()).isEqualTo(DEFAULT_ARCHIV);
    }

    @Test
    @Transactional
    void createBeitragWithExistingId() throws Exception {
        // Create the Beitrag with an existing ID
        beitrag.setId(1L);

        int databaseSizeBeforeCreate = beitragRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeitragMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(beitrag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beitrag in the database
        List<Beitrag> beitragList = beitragRepository.findAll();
        assertThat(beitragList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBeitrags() throws Exception {
        // Initialize the database
        beitragRepository.saveAndFlush(beitrag);

        // Get all the beitragList
        restBeitragMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beitrag.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].attrib").value(hasItem(DEFAULT_ATTRIB)))
            .andExpect(jsonPath("$.[*].titel").value(hasItem(DEFAULT_TITEL)))
            .andExpect(jsonPath("$.[*].rechte").value(hasItem(DEFAULT_RECHTE)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].publishDate").value(hasItem(DEFAULT_PUBLISH_DATE.toString())))
            .andExpect(jsonPath("$.[*].archiv").value(hasItem(DEFAULT_ARCHIV.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBeitragsWithEagerRelationshipsIsEnabled() throws Exception {
        when(beitragServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBeitragMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(beitragServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBeitragsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(beitragServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBeitragMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(beitragServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getBeitrag() throws Exception {
        // Initialize the database
        beitragRepository.saveAndFlush(beitrag);

        // Get the beitrag
        restBeitragMockMvc
            .perform(get(ENTITY_API_URL_ID, beitrag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beitrag.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.attrib").value(DEFAULT_ATTRIB))
            .andExpect(jsonPath("$.titel").value(DEFAULT_TITEL))
            .andExpect(jsonPath("$.rechte").value(DEFAULT_RECHTE))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()))
            .andExpect(jsonPath("$.publishDate").value(DEFAULT_PUBLISH_DATE.toString()))
            .andExpect(jsonPath("$.archiv").value(DEFAULT_ARCHIV.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBeitrag() throws Exception {
        // Get the beitrag
        restBeitragMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBeitrag() throws Exception {
        // Initialize the database
        beitragRepository.saveAndFlush(beitrag);

        int databaseSizeBeforeUpdate = beitragRepository.findAll().size();

        // Update the beitrag
        Beitrag updatedBeitrag = beitragRepository.findById(beitrag.getId()).get();
        // Disconnect from session so that the updates on updatedBeitrag are not directly saved in db
        em.detach(updatedBeitrag);
        updatedBeitrag
            .content(UPDATED_CONTENT)
            .attrib(UPDATED_ATTRIB)
            .titel(UPDATED_TITEL)
            .rechte(UPDATED_RECHTE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .publishDate(UPDATED_PUBLISH_DATE)
            .archiv(UPDATED_ARCHIV);

        restBeitragMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBeitrag.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBeitrag))
            )
            .andExpect(status().isOk());

        // Validate the Beitrag in the database
        List<Beitrag> beitragList = beitragRepository.findAll();
        assertThat(beitragList).hasSize(databaseSizeBeforeUpdate);
        Beitrag testBeitrag = beitragList.get(beitragList.size() - 1);
        assertThat(testBeitrag.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testBeitrag.getAttrib()).isEqualTo(UPDATED_ATTRIB);
        assertThat(testBeitrag.getTitel()).isEqualTo(UPDATED_TITEL);
        assertThat(testBeitrag.getRechte()).isEqualTo(UPDATED_RECHTE);
        assertThat(testBeitrag.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testBeitrag.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testBeitrag.getPublishDate()).isEqualTo(UPDATED_PUBLISH_DATE);
        assertThat(testBeitrag.getArchiv()).isEqualTo(UPDATED_ARCHIV);
    }

    @Test
    @Transactional
    void putNonExistingBeitrag() throws Exception {
        int databaseSizeBeforeUpdate = beitragRepository.findAll().size();
        beitrag.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeitragMockMvc
            .perform(
                put(ENTITY_API_URL_ID, beitrag.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(beitrag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beitrag in the database
        List<Beitrag> beitragList = beitragRepository.findAll();
        assertThat(beitragList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBeitrag() throws Exception {
        int databaseSizeBeforeUpdate = beitragRepository.findAll().size();
        beitrag.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeitragMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(beitrag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beitrag in the database
        List<Beitrag> beitragList = beitragRepository.findAll();
        assertThat(beitragList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBeitrag() throws Exception {
        int databaseSizeBeforeUpdate = beitragRepository.findAll().size();
        beitrag.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeitragMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(beitrag))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beitrag in the database
        List<Beitrag> beitragList = beitragRepository.findAll();
        assertThat(beitragList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBeitragWithPatch() throws Exception {
        // Initialize the database
        beitragRepository.saveAndFlush(beitrag);

        int databaseSizeBeforeUpdate = beitragRepository.findAll().size();

        // Update the beitrag using partial update
        Beitrag partialUpdatedBeitrag = new Beitrag();
        partialUpdatedBeitrag.setId(beitrag.getId());

        partialUpdatedBeitrag
            .content(UPDATED_CONTENT)
            .attrib(UPDATED_ATTRIB)
            .titel(UPDATED_TITEL)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);

        restBeitragMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeitrag.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBeitrag))
            )
            .andExpect(status().isOk());

        // Validate the Beitrag in the database
        List<Beitrag> beitragList = beitragRepository.findAll();
        assertThat(beitragList).hasSize(databaseSizeBeforeUpdate);
        Beitrag testBeitrag = beitragList.get(beitragList.size() - 1);
        assertThat(testBeitrag.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testBeitrag.getAttrib()).isEqualTo(UPDATED_ATTRIB);
        assertThat(testBeitrag.getTitel()).isEqualTo(UPDATED_TITEL);
        assertThat(testBeitrag.getRechte()).isEqualTo(DEFAULT_RECHTE);
        assertThat(testBeitrag.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testBeitrag.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testBeitrag.getPublishDate()).isEqualTo(DEFAULT_PUBLISH_DATE);
        assertThat(testBeitrag.getArchiv()).isEqualTo(DEFAULT_ARCHIV);
    }

    @Test
    @Transactional
    void fullUpdateBeitragWithPatch() throws Exception {
        // Initialize the database
        beitragRepository.saveAndFlush(beitrag);

        int databaseSizeBeforeUpdate = beitragRepository.findAll().size();

        // Update the beitrag using partial update
        Beitrag partialUpdatedBeitrag = new Beitrag();
        partialUpdatedBeitrag.setId(beitrag.getId());

        partialUpdatedBeitrag
            .content(UPDATED_CONTENT)
            .attrib(UPDATED_ATTRIB)
            .titel(UPDATED_TITEL)
            .rechte(UPDATED_RECHTE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .publishDate(UPDATED_PUBLISH_DATE)
            .archiv(UPDATED_ARCHIV);

        restBeitragMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeitrag.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBeitrag))
            )
            .andExpect(status().isOk());

        // Validate the Beitrag in the database
        List<Beitrag> beitragList = beitragRepository.findAll();
        assertThat(beitragList).hasSize(databaseSizeBeforeUpdate);
        Beitrag testBeitrag = beitragList.get(beitragList.size() - 1);
        assertThat(testBeitrag.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testBeitrag.getAttrib()).isEqualTo(UPDATED_ATTRIB);
        assertThat(testBeitrag.getTitel()).isEqualTo(UPDATED_TITEL);
        assertThat(testBeitrag.getRechte()).isEqualTo(UPDATED_RECHTE);
        assertThat(testBeitrag.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testBeitrag.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testBeitrag.getPublishDate()).isEqualTo(UPDATED_PUBLISH_DATE);
        assertThat(testBeitrag.getArchiv()).isEqualTo(UPDATED_ARCHIV);
    }

    @Test
    @Transactional
    void patchNonExistingBeitrag() throws Exception {
        int databaseSizeBeforeUpdate = beitragRepository.findAll().size();
        beitrag.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeitragMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, beitrag.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(beitrag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beitrag in the database
        List<Beitrag> beitragList = beitragRepository.findAll();
        assertThat(beitragList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBeitrag() throws Exception {
        int databaseSizeBeforeUpdate = beitragRepository.findAll().size();
        beitrag.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeitragMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(beitrag))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beitrag in the database
        List<Beitrag> beitragList = beitragRepository.findAll();
        assertThat(beitragList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBeitrag() throws Exception {
        int databaseSizeBeforeUpdate = beitragRepository.findAll().size();
        beitrag.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeitragMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(beitrag))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beitrag in the database
        List<Beitrag> beitragList = beitragRepository.findAll();
        assertThat(beitragList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBeitrag() throws Exception {
        // Initialize the database
        beitragRepository.saveAndFlush(beitrag);

        int databaseSizeBeforeDelete = beitragRepository.findAll().size();

        // Delete the beitrag
        restBeitragMockMvc
            .perform(delete(ENTITY_API_URL_ID, beitrag.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Beitrag> beitragList = beitragRepository.findAll();
        assertThat(beitragList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

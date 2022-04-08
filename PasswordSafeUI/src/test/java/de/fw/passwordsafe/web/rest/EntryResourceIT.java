package de.fw.passwordsafe.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import de.fw.passwordsafe.IntegrationTest;
import de.fw.passwordsafe.domain.Entry;
import de.fw.passwordsafe.repository.EntityManager;
import de.fw.passwordsafe.repository.EntryRepository;
import de.fw.passwordsafe.service.EntryService;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Integration tests for the {@link EntryResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class EntryResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORT = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORT = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORT_REPLAY = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORT_REPLAY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/entries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EntryRepository entryRepository;

    @Mock
    private EntryRepository entryRepositoryMock;

    @Mock
    private EntryService entryServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Entry entry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entry createEntity(EntityManager em) {
        Entry entry = new Entry()
            .description(DEFAULT_DESCRIPTION)
            .login(DEFAULT_LOGIN)
            .passwort(DEFAULT_PASSWORT)
            .passwortReplay(DEFAULT_PASSWORT_REPLAY);
        return entry;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entry createUpdatedEntity(EntityManager em) {
        Entry entry = new Entry()
            .description(UPDATED_DESCRIPTION)
            .login(UPDATED_LOGIN)
            .passwort(UPDATED_PASSWORT)
            .passwortReplay(UPDATED_PASSWORT_REPLAY);
        return entry;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Entry.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void setupCsrf() {
        webTestClient = webTestClient.mutateWith(csrf());
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        entry = createEntity(em);
    }

    @Test
    void createEntry() throws Exception {
        int databaseSizeBeforeCreate = entryRepository.findAll().collectList().block().size();
        // Create the Entry
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(entry))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Entry in the database
        List<Entry> entryList = entryRepository.findAll().collectList().block();
        assertThat(entryList).hasSize(databaseSizeBeforeCreate + 1);
        Entry testEntry = entryList.get(entryList.size() - 1);
        assertThat(testEntry.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEntry.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testEntry.getPasswort()).isEqualTo(DEFAULT_PASSWORT);
        assertThat(testEntry.getPasswortReplay()).isEqualTo(DEFAULT_PASSWORT_REPLAY);
    }

    @Test
    void createEntryWithExistingId() throws Exception {
        // Create the Entry with an existing ID
        entry.setId(1L);

        int databaseSizeBeforeCreate = entryRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(entry))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Entry in the database
        List<Entry> entryList = entryRepository.findAll().collectList().block();
        assertThat(entryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = entryRepository.findAll().collectList().block().size();
        // set the field null
        entry.setLogin(null);

        // Create the Entry, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(entry))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Entry> entryList = entryRepository.findAll().collectList().block();
        assertThat(entryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPasswortIsRequired() throws Exception {
        int databaseSizeBeforeTest = entryRepository.findAll().collectList().block().size();
        // set the field null
        entry.setPasswort(null);

        // Create the Entry, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(entry))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Entry> entryList = entryRepository.findAll().collectList().block();
        assertThat(entryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPasswortReplayIsRequired() throws Exception {
        int databaseSizeBeforeTest = entryRepository.findAll().collectList().block().size();
        // set the field null
        entry.setPasswortReplay(null);

        // Create the Entry, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(entry))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Entry> entryList = entryRepository.findAll().collectList().block();
        assertThat(entryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllEntries() {
        // Initialize the database
        entryRepository.save(entry).block();

        // Get all the entryList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(entry.getId().intValue()))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION))
            .jsonPath("$.[*].login")
            .value(hasItem(DEFAULT_LOGIN))
            .jsonPath("$.[*].passwort")
            .value(hasItem(DEFAULT_PASSWORT))
            .jsonPath("$.[*].passwortReplay")
            .value(hasItem(DEFAULT_PASSWORT_REPLAY));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEntriesWithEagerRelationshipsIsEnabled() {
        when(entryServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(entryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEntriesWithEagerRelationshipsIsNotEnabled() {
        when(entryServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(entryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    void getEntry() {
        // Initialize the database
        entryRepository.save(entry).block();

        // Get the entry
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, entry.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(entry.getId().intValue()))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION))
            .jsonPath("$.login")
            .value(is(DEFAULT_LOGIN))
            .jsonPath("$.passwort")
            .value(is(DEFAULT_PASSWORT))
            .jsonPath("$.passwortReplay")
            .value(is(DEFAULT_PASSWORT_REPLAY));
    }

    @Test
    void getNonExistingEntry() {
        // Get the entry
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putNewEntry() throws Exception {
        // Initialize the database
        entryRepository.save(entry).block();

        int databaseSizeBeforeUpdate = entryRepository.findAll().collectList().block().size();

        // Update the entry
        Entry updatedEntry = entryRepository.findById(entry.getId()).block();
        updatedEntry
            .description(UPDATED_DESCRIPTION)
            .login(UPDATED_LOGIN)
            .passwort(UPDATED_PASSWORT)
            .passwortReplay(UPDATED_PASSWORT_REPLAY);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedEntry.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedEntry))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Entry in the database
        List<Entry> entryList = entryRepository.findAll().collectList().block();
        assertThat(entryList).hasSize(databaseSizeBeforeUpdate);
        Entry testEntry = entryList.get(entryList.size() - 1);
        assertThat(testEntry.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEntry.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testEntry.getPasswort()).isEqualTo(UPDATED_PASSWORT);
        assertThat(testEntry.getPasswortReplay()).isEqualTo(UPDATED_PASSWORT_REPLAY);
    }

    @Test
    void putNonExistingEntry() throws Exception {
        int databaseSizeBeforeUpdate = entryRepository.findAll().collectList().block().size();
        entry.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, entry.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(entry))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Entry in the database
        List<Entry> entryList = entryRepository.findAll().collectList().block();
        assertThat(entryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEntry() throws Exception {
        int databaseSizeBeforeUpdate = entryRepository.findAll().collectList().block().size();
        entry.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(entry))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Entry in the database
        List<Entry> entryList = entryRepository.findAll().collectList().block();
        assertThat(entryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEntry() throws Exception {
        int databaseSizeBeforeUpdate = entryRepository.findAll().collectList().block().size();
        entry.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(entry))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Entry in the database
        List<Entry> entryList = entryRepository.findAll().collectList().block();
        assertThat(entryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEntryWithPatch() throws Exception {
        // Initialize the database
        entryRepository.save(entry).block();

        int databaseSizeBeforeUpdate = entryRepository.findAll().collectList().block().size();

        // Update the entry using partial update
        Entry partialUpdatedEntry = new Entry();
        partialUpdatedEntry.setId(entry.getId());

        partialUpdatedEntry.description(UPDATED_DESCRIPTION).passwortReplay(UPDATED_PASSWORT_REPLAY);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedEntry.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedEntry))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Entry in the database
        List<Entry> entryList = entryRepository.findAll().collectList().block();
        assertThat(entryList).hasSize(databaseSizeBeforeUpdate);
        Entry testEntry = entryList.get(entryList.size() - 1);
        assertThat(testEntry.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEntry.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testEntry.getPasswort()).isEqualTo(DEFAULT_PASSWORT);
        assertThat(testEntry.getPasswortReplay()).isEqualTo(UPDATED_PASSWORT_REPLAY);
    }

    @Test
    void fullUpdateEntryWithPatch() throws Exception {
        // Initialize the database
        entryRepository.save(entry).block();

        int databaseSizeBeforeUpdate = entryRepository.findAll().collectList().block().size();

        // Update the entry using partial update
        Entry partialUpdatedEntry = new Entry();
        partialUpdatedEntry.setId(entry.getId());

        partialUpdatedEntry
            .description(UPDATED_DESCRIPTION)
            .login(UPDATED_LOGIN)
            .passwort(UPDATED_PASSWORT)
            .passwortReplay(UPDATED_PASSWORT_REPLAY);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedEntry.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedEntry))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Entry in the database
        List<Entry> entryList = entryRepository.findAll().collectList().block();
        assertThat(entryList).hasSize(databaseSizeBeforeUpdate);
        Entry testEntry = entryList.get(entryList.size() - 1);
        assertThat(testEntry.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEntry.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testEntry.getPasswort()).isEqualTo(UPDATED_PASSWORT);
        assertThat(testEntry.getPasswortReplay()).isEqualTo(UPDATED_PASSWORT_REPLAY);
    }

    @Test
    void patchNonExistingEntry() throws Exception {
        int databaseSizeBeforeUpdate = entryRepository.findAll().collectList().block().size();
        entry.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, entry.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(entry))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Entry in the database
        List<Entry> entryList = entryRepository.findAll().collectList().block();
        assertThat(entryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEntry() throws Exception {
        int databaseSizeBeforeUpdate = entryRepository.findAll().collectList().block().size();
        entry.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(entry))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Entry in the database
        List<Entry> entryList = entryRepository.findAll().collectList().block();
        assertThat(entryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEntry() throws Exception {
        int databaseSizeBeforeUpdate = entryRepository.findAll().collectList().block().size();
        entry.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(entry))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Entry in the database
        List<Entry> entryList = entryRepository.findAll().collectList().block();
        assertThat(entryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEntry() {
        // Initialize the database
        entryRepository.save(entry).block();

        int databaseSizeBeforeDelete = entryRepository.findAll().collectList().block().size();

        // Delete the entry
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, entry.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Entry> entryList = entryRepository.findAll().collectList().block();
        assertThat(entryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

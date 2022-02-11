package de.svi.svis5g.rezertifizierung.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.svi.svis5g.rezertifizierung.IntegrationTest;
import de.svi.svis5g.rezertifizierung.domain.Rezertifizierung;
import de.svi.svis5g.rezertifizierung.repository.RezertifizierungRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link RezertifizierungResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RezertifizierungResourceIT {

    private static final String DEFAULT_LOGIN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NACHNAME = "AAAAAAAAAA";
    private static final String UPDATED_NACHNAME = "BBBBBBBBBB";

    private static final String DEFAULT_VORNAME = "AAAAAAAAAA";
    private static final String UPDATED_VORNAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_VERMITTLER_NUMMER = 1;
    private static final Integer UPDATED_VERMITTLER_NUMMER = 2;

    private static final String DEFAULT_ROLLEN_ZUGEHOERIGKEITEN = "AAAAAAAAAA";
    private static final String UPDATED_ROLLEN_ZUGEHOERIGKEITEN = "BBBBBBBBBB";

    private static final String DEFAULT_DVC_VERTRETER_NUMMER = "AAAAAAAAAA";
    private static final String UPDATED_DVC_VERTRETER_NUMMER = "BBBBBBBBBB";

    private static final String DEFAULT_DVC_BENUTZER_GRUPPE = "AAAAAAAAAA";
    private static final String UPDATED_DVC_BENUTZER_GRUPPE = "BBBBBBBBBB";

    private static final String DEFAULT_ICIS_SR_GEBAUDE = "AAAAAAAAAA";
    private static final String UPDATED_ICIS_SR_GEBAUDE = "BBBBBBBBBB";

    private static final String DEFAULT_ICIS_SR_HAFTPFLICHT = "AAAAAAAAAA";
    private static final String UPDATED_ICIS_SR_HAFTPFLICHT = "BBBBBBBBBB";

    private static final String DEFAULT_ICIS_SR_LEITUNGSWASSER = "AAAAAAAAAA";
    private static final String UPDATED_ICIS_SR_LEITUNGSWASSER = "BBBBBBBBBB";

    private static final String DEFAULT_ICIS_SR_KFZ_KASKO = "AAAAAAAAAA";
    private static final String UPDATED_ICIS_SR_KFZ_KASKO = "BBBBBBBBBB";

    private static final String DEFAULT_BESTANDSSICHT = "AAAAAAAAAA";
    private static final String UPDATED_BESTANDSSICHT = "BBBBBBBBBB";

    private static final String DEFAULT_BEMERKUNG = "AAAAAAAAAA";
    private static final String UPDATED_BEMERKUNG = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRUEFUNG_ERFOLGT = false;
    private static final Boolean UPDATED_PRUEFUNG_ERFOLGT = true;

    private static final String ENTITY_API_URL = "/api/rezertifizierungs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RezertifizierungRepository rezertifizierungRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRezertifizierungMockMvc;

    private Rezertifizierung rezertifizierung;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rezertifizierung createEntity(EntityManager em) {
        Rezertifizierung rezertifizierung = new Rezertifizierung()
            .loginName(DEFAULT_LOGIN_NAME)
            .nachname(DEFAULT_NACHNAME)
            .vorname(DEFAULT_VORNAME)
            .vermittlerNummer(DEFAULT_VERMITTLER_NUMMER)
            .rollenZugehoerigkeiten(DEFAULT_ROLLEN_ZUGEHOERIGKEITEN)
            .dvcVertreterNummer(DEFAULT_DVC_VERTRETER_NUMMER)
            .dvcBenutzerGruppe(DEFAULT_DVC_BENUTZER_GRUPPE)
            .icisSrGebaude(DEFAULT_ICIS_SR_GEBAUDE)
            .icisSrHaftpflicht(DEFAULT_ICIS_SR_HAFTPFLICHT)
            .icisSrLeitungswasser(DEFAULT_ICIS_SR_LEITUNGSWASSER)
            .icisSrKfzKasko(DEFAULT_ICIS_SR_KFZ_KASKO)
            .bestandssicht(DEFAULT_BESTANDSSICHT)
            .bemerkung(DEFAULT_BEMERKUNG)
            .pruefungErfolgt(DEFAULT_PRUEFUNG_ERFOLGT);
        return rezertifizierung;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rezertifizierung createUpdatedEntity(EntityManager em) {
        Rezertifizierung rezertifizierung = new Rezertifizierung()
            .loginName(UPDATED_LOGIN_NAME)
            .nachname(UPDATED_NACHNAME)
            .vorname(UPDATED_VORNAME)
            .vermittlerNummer(UPDATED_VERMITTLER_NUMMER)
            .rollenZugehoerigkeiten(UPDATED_ROLLEN_ZUGEHOERIGKEITEN)
            .dvcVertreterNummer(UPDATED_DVC_VERTRETER_NUMMER)
            .dvcBenutzerGruppe(UPDATED_DVC_BENUTZER_GRUPPE)
            .icisSrGebaude(UPDATED_ICIS_SR_GEBAUDE)
            .icisSrHaftpflicht(UPDATED_ICIS_SR_HAFTPFLICHT)
            .icisSrLeitungswasser(UPDATED_ICIS_SR_LEITUNGSWASSER)
            .icisSrKfzKasko(UPDATED_ICIS_SR_KFZ_KASKO)
            .bestandssicht(UPDATED_BESTANDSSICHT)
            .bemerkung(UPDATED_BEMERKUNG)
            .pruefungErfolgt(UPDATED_PRUEFUNG_ERFOLGT);
        return rezertifizierung;
    }

    @BeforeEach
    public void initTest() {
        rezertifizierung = createEntity(em);
    }

    @Test
    @Transactional
    void createRezertifizierung() throws Exception {
        int databaseSizeBeforeCreate = rezertifizierungRepository.findAll().size();
        // Create the Rezertifizierung
        restRezertifizierungMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rezertifizierung))
            )
            .andExpect(status().isCreated());

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeCreate + 1);
        Rezertifizierung testRezertifizierung = rezertifizierungList.get(rezertifizierungList.size() - 1);
        assertThat(testRezertifizierung.getLoginName()).isEqualTo(DEFAULT_LOGIN_NAME);
        assertThat(testRezertifizierung.getNachname()).isEqualTo(DEFAULT_NACHNAME);
        assertThat(testRezertifizierung.getVorname()).isEqualTo(DEFAULT_VORNAME);
        assertThat(testRezertifizierung.getVermittlerNummer()).isEqualTo(DEFAULT_VERMITTLER_NUMMER);
        assertThat(testRezertifizierung.getRollenZugehoerigkeiten()).isEqualTo(DEFAULT_ROLLEN_ZUGEHOERIGKEITEN);
        assertThat(testRezertifizierung.getDvcVertreterNummer()).isEqualTo(DEFAULT_DVC_VERTRETER_NUMMER);
        assertThat(testRezertifizierung.getDvcBenutzerGruppe()).isEqualTo(DEFAULT_DVC_BENUTZER_GRUPPE);
        assertThat(testRezertifizierung.getIcisSrGebaude()).isEqualTo(DEFAULT_ICIS_SR_GEBAUDE);
        assertThat(testRezertifizierung.getIcisSrHaftpflicht()).isEqualTo(DEFAULT_ICIS_SR_HAFTPFLICHT);
        assertThat(testRezertifizierung.getIcisSrLeitungswasser()).isEqualTo(DEFAULT_ICIS_SR_LEITUNGSWASSER);
        assertThat(testRezertifizierung.getIcisSrKfzKasko()).isEqualTo(DEFAULT_ICIS_SR_KFZ_KASKO);
        assertThat(testRezertifizierung.getBestandssicht()).isEqualTo(DEFAULT_BESTANDSSICHT);
        assertThat(testRezertifizierung.getBemerkung()).isEqualTo(DEFAULT_BEMERKUNG);
        assertThat(testRezertifizierung.getPruefungErfolgt()).isEqualTo(DEFAULT_PRUEFUNG_ERFOLGT);
    }

    @Test
    @Transactional
    void createRezertifizierungWithExistingId() throws Exception {
        // Create the Rezertifizierung with an existing ID
        rezertifizierung.setId(1L);

        int databaseSizeBeforeCreate = rezertifizierungRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRezertifizierungMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rezertifizierung))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVermittlerNummerIsRequired() throws Exception {
        int databaseSizeBeforeTest = rezertifizierungRepository.findAll().size();
        // set the field null
        rezertifizierung.setVermittlerNummer(null);

        // Create the Rezertifizierung, which fails.

        restRezertifizierungMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rezertifizierung))
            )
            .andExpect(status().isBadRequest());

        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRezertifizierungs() throws Exception {
        // Initialize the database
        rezertifizierungRepository.saveAndFlush(rezertifizierung);

        // Get all the rezertifizierungList
        restRezertifizierungMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rezertifizierung.getId().intValue())))
            .andExpect(jsonPath("$.[*].loginName").value(hasItem(DEFAULT_LOGIN_NAME)))
            .andExpect(jsonPath("$.[*].nachname").value(hasItem(DEFAULT_NACHNAME)))
            .andExpect(jsonPath("$.[*].vorname").value(hasItem(DEFAULT_VORNAME)))
            .andExpect(jsonPath("$.[*].vermittlerNummer").value(hasItem(DEFAULT_VERMITTLER_NUMMER)))
            .andExpect(jsonPath("$.[*].rollenZugehoerigkeiten").value(hasItem(DEFAULT_ROLLEN_ZUGEHOERIGKEITEN.toString())))
            .andExpect(jsonPath("$.[*].dvcVertreterNummer").value(hasItem(DEFAULT_DVC_VERTRETER_NUMMER)))
            .andExpect(jsonPath("$.[*].dvcBenutzerGruppe").value(hasItem(DEFAULT_DVC_BENUTZER_GRUPPE)))
            .andExpect(jsonPath("$.[*].icisSrGebaude").value(hasItem(DEFAULT_ICIS_SR_GEBAUDE)))
            .andExpect(jsonPath("$.[*].icisSrHaftpflicht").value(hasItem(DEFAULT_ICIS_SR_HAFTPFLICHT)))
            .andExpect(jsonPath("$.[*].icisSrLeitungswasser").value(hasItem(DEFAULT_ICIS_SR_LEITUNGSWASSER)))
            .andExpect(jsonPath("$.[*].icisSrKfzKasko").value(hasItem(DEFAULT_ICIS_SR_KFZ_KASKO)))
            .andExpect(jsonPath("$.[*].bestandssicht").value(hasItem(DEFAULT_BESTANDSSICHT.toString())))
            .andExpect(jsonPath("$.[*].bemerkung").value(hasItem(DEFAULT_BEMERKUNG.toString())))
            .andExpect(jsonPath("$.[*].pruefungErfolgt").value(hasItem(DEFAULT_PRUEFUNG_ERFOLGT.booleanValue())));
    }

    @Test
    @Transactional
    void getRezertifizierung() throws Exception {
        // Initialize the database
        rezertifizierungRepository.saveAndFlush(rezertifizierung);

        // Get the rezertifizierung
        restRezertifizierungMockMvc
            .perform(get(ENTITY_API_URL_ID, rezertifizierung.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rezertifizierung.getId().intValue()))
            .andExpect(jsonPath("$.loginName").value(DEFAULT_LOGIN_NAME))
            .andExpect(jsonPath("$.nachname").value(DEFAULT_NACHNAME))
            .andExpect(jsonPath("$.vorname").value(DEFAULT_VORNAME))
            .andExpect(jsonPath("$.vermittlerNummer").value(DEFAULT_VERMITTLER_NUMMER))
            .andExpect(jsonPath("$.rollenZugehoerigkeiten").value(DEFAULT_ROLLEN_ZUGEHOERIGKEITEN.toString()))
            .andExpect(jsonPath("$.dvcVertreterNummer").value(DEFAULT_DVC_VERTRETER_NUMMER))
            .andExpect(jsonPath("$.dvcBenutzerGruppe").value(DEFAULT_DVC_BENUTZER_GRUPPE))
            .andExpect(jsonPath("$.icisSrGebaude").value(DEFAULT_ICIS_SR_GEBAUDE))
            .andExpect(jsonPath("$.icisSrHaftpflicht").value(DEFAULT_ICIS_SR_HAFTPFLICHT))
            .andExpect(jsonPath("$.icisSrLeitungswasser").value(DEFAULT_ICIS_SR_LEITUNGSWASSER))
            .andExpect(jsonPath("$.icisSrKfzKasko").value(DEFAULT_ICIS_SR_KFZ_KASKO))
            .andExpect(jsonPath("$.bestandssicht").value(DEFAULT_BESTANDSSICHT.toString()))
            .andExpect(jsonPath("$.bemerkung").value(DEFAULT_BEMERKUNG.toString()))
            .andExpect(jsonPath("$.pruefungErfolgt").value(DEFAULT_PRUEFUNG_ERFOLGT.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingRezertifizierung() throws Exception {
        // Get the rezertifizierung
        restRezertifizierungMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRezertifizierung() throws Exception {
        // Initialize the database
        rezertifizierungRepository.saveAndFlush(rezertifizierung);

        int databaseSizeBeforeUpdate = rezertifizierungRepository.findAll().size();

        // Update the rezertifizierung
        Rezertifizierung updatedRezertifizierung = rezertifizierungRepository.findById(rezertifizierung.getId()).get();
        // Disconnect from session so that the updates on updatedRezertifizierung are not directly saved in db
        em.detach(updatedRezertifizierung);
        updatedRezertifizierung
            .loginName(UPDATED_LOGIN_NAME)
            .nachname(UPDATED_NACHNAME)
            .vorname(UPDATED_VORNAME)
            .vermittlerNummer(UPDATED_VERMITTLER_NUMMER)
            .rollenZugehoerigkeiten(UPDATED_ROLLEN_ZUGEHOERIGKEITEN)
            .dvcVertreterNummer(UPDATED_DVC_VERTRETER_NUMMER)
            .dvcBenutzerGruppe(UPDATED_DVC_BENUTZER_GRUPPE)
            .icisSrGebaude(UPDATED_ICIS_SR_GEBAUDE)
            .icisSrHaftpflicht(UPDATED_ICIS_SR_HAFTPFLICHT)
            .icisSrLeitungswasser(UPDATED_ICIS_SR_LEITUNGSWASSER)
            .icisSrKfzKasko(UPDATED_ICIS_SR_KFZ_KASKO)
            .bestandssicht(UPDATED_BESTANDSSICHT)
            .bemerkung(UPDATED_BEMERKUNG)
            .pruefungErfolgt(UPDATED_PRUEFUNG_ERFOLGT);

        restRezertifizierungMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRezertifizierung.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRezertifizierung))
            )
            .andExpect(status().isOk());

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeUpdate);
        Rezertifizierung testRezertifizierung = rezertifizierungList.get(rezertifizierungList.size() - 1);
        assertThat(testRezertifizierung.getLoginName()).isEqualTo(UPDATED_LOGIN_NAME);
        assertThat(testRezertifizierung.getNachname()).isEqualTo(UPDATED_NACHNAME);
        assertThat(testRezertifizierung.getVorname()).isEqualTo(UPDATED_VORNAME);
        assertThat(testRezertifizierung.getVermittlerNummer()).isEqualTo(UPDATED_VERMITTLER_NUMMER);
        assertThat(testRezertifizierung.getRollenZugehoerigkeiten()).isEqualTo(UPDATED_ROLLEN_ZUGEHOERIGKEITEN);
        assertThat(testRezertifizierung.getDvcVertreterNummer()).isEqualTo(UPDATED_DVC_VERTRETER_NUMMER);
        assertThat(testRezertifizierung.getDvcBenutzerGruppe()).isEqualTo(UPDATED_DVC_BENUTZER_GRUPPE);
        assertThat(testRezertifizierung.getIcisSrGebaude()).isEqualTo(UPDATED_ICIS_SR_GEBAUDE);
        assertThat(testRezertifizierung.getIcisSrHaftpflicht()).isEqualTo(UPDATED_ICIS_SR_HAFTPFLICHT);
        assertThat(testRezertifizierung.getIcisSrLeitungswasser()).isEqualTo(UPDATED_ICIS_SR_LEITUNGSWASSER);
        assertThat(testRezertifizierung.getIcisSrKfzKasko()).isEqualTo(UPDATED_ICIS_SR_KFZ_KASKO);
        assertThat(testRezertifizierung.getBestandssicht()).isEqualTo(UPDATED_BESTANDSSICHT);
        assertThat(testRezertifizierung.getBemerkung()).isEqualTo(UPDATED_BEMERKUNG);
        assertThat(testRezertifizierung.getPruefungErfolgt()).isEqualTo(UPDATED_PRUEFUNG_ERFOLGT);
    }

    @Test
    @Transactional
    void putNonExistingRezertifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezertifizierungRepository.findAll().size();
        rezertifizierung.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRezertifizierungMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rezertifizierung.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rezertifizierung))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRezertifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezertifizierungRepository.findAll().size();
        rezertifizierung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRezertifizierungMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rezertifizierung))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRezertifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezertifizierungRepository.findAll().size();
        rezertifizierung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRezertifizierungMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rezertifizierung))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRezertifizierungWithPatch() throws Exception {
        // Initialize the database
        rezertifizierungRepository.saveAndFlush(rezertifizierung);

        int databaseSizeBeforeUpdate = rezertifizierungRepository.findAll().size();

        // Update the rezertifizierung using partial update
        Rezertifizierung partialUpdatedRezertifizierung = new Rezertifizierung();
        partialUpdatedRezertifizierung.setId(rezertifizierung.getId());

        partialUpdatedRezertifizierung
            .vorname(UPDATED_VORNAME)
            .icisSrGebaude(UPDATED_ICIS_SR_GEBAUDE)
            .icisSrLeitungswasser(UPDATED_ICIS_SR_LEITUNGSWASSER)
            .icisSrKfzKasko(UPDATED_ICIS_SR_KFZ_KASKO);

        restRezertifizierungMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRezertifizierung.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRezertifizierung))
            )
            .andExpect(status().isOk());

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeUpdate);
        Rezertifizierung testRezertifizierung = rezertifizierungList.get(rezertifizierungList.size() - 1);
        assertThat(testRezertifizierung.getLoginName()).isEqualTo(DEFAULT_LOGIN_NAME);
        assertThat(testRezertifizierung.getNachname()).isEqualTo(DEFAULT_NACHNAME);
        assertThat(testRezertifizierung.getVorname()).isEqualTo(UPDATED_VORNAME);
        assertThat(testRezertifizierung.getVermittlerNummer()).isEqualTo(DEFAULT_VERMITTLER_NUMMER);
        assertThat(testRezertifizierung.getRollenZugehoerigkeiten()).isEqualTo(DEFAULT_ROLLEN_ZUGEHOERIGKEITEN);
        assertThat(testRezertifizierung.getDvcVertreterNummer()).isEqualTo(DEFAULT_DVC_VERTRETER_NUMMER);
        assertThat(testRezertifizierung.getDvcBenutzerGruppe()).isEqualTo(DEFAULT_DVC_BENUTZER_GRUPPE);
        assertThat(testRezertifizierung.getIcisSrGebaude()).isEqualTo(UPDATED_ICIS_SR_GEBAUDE);
        assertThat(testRezertifizierung.getIcisSrHaftpflicht()).isEqualTo(DEFAULT_ICIS_SR_HAFTPFLICHT);
        assertThat(testRezertifizierung.getIcisSrLeitungswasser()).isEqualTo(UPDATED_ICIS_SR_LEITUNGSWASSER);
        assertThat(testRezertifizierung.getIcisSrKfzKasko()).isEqualTo(UPDATED_ICIS_SR_KFZ_KASKO);
        assertThat(testRezertifizierung.getBestandssicht()).isEqualTo(DEFAULT_BESTANDSSICHT);
        assertThat(testRezertifizierung.getBemerkung()).isEqualTo(DEFAULT_BEMERKUNG);
        assertThat(testRezertifizierung.getPruefungErfolgt()).isEqualTo(DEFAULT_PRUEFUNG_ERFOLGT);
    }

    @Test
    @Transactional
    void fullUpdateRezertifizierungWithPatch() throws Exception {
        // Initialize the database
        rezertifizierungRepository.saveAndFlush(rezertifizierung);

        int databaseSizeBeforeUpdate = rezertifizierungRepository.findAll().size();

        // Update the rezertifizierung using partial update
        Rezertifizierung partialUpdatedRezertifizierung = new Rezertifizierung();
        partialUpdatedRezertifizierung.setId(rezertifizierung.getId());

        partialUpdatedRezertifizierung
            .loginName(UPDATED_LOGIN_NAME)
            .nachname(UPDATED_NACHNAME)
            .vorname(UPDATED_VORNAME)
            .vermittlerNummer(UPDATED_VERMITTLER_NUMMER)
            .rollenZugehoerigkeiten(UPDATED_ROLLEN_ZUGEHOERIGKEITEN)
            .dvcVertreterNummer(UPDATED_DVC_VERTRETER_NUMMER)
            .dvcBenutzerGruppe(UPDATED_DVC_BENUTZER_GRUPPE)
            .icisSrGebaude(UPDATED_ICIS_SR_GEBAUDE)
            .icisSrHaftpflicht(UPDATED_ICIS_SR_HAFTPFLICHT)
            .icisSrLeitungswasser(UPDATED_ICIS_SR_LEITUNGSWASSER)
            .icisSrKfzKasko(UPDATED_ICIS_SR_KFZ_KASKO)
            .bestandssicht(UPDATED_BESTANDSSICHT)
            .bemerkung(UPDATED_BEMERKUNG)
            .pruefungErfolgt(UPDATED_PRUEFUNG_ERFOLGT);

        restRezertifizierungMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRezertifizierung.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRezertifizierung))
            )
            .andExpect(status().isOk());

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeUpdate);
        Rezertifizierung testRezertifizierung = rezertifizierungList.get(rezertifizierungList.size() - 1);
        assertThat(testRezertifizierung.getLoginName()).isEqualTo(UPDATED_LOGIN_NAME);
        assertThat(testRezertifizierung.getNachname()).isEqualTo(UPDATED_NACHNAME);
        assertThat(testRezertifizierung.getVorname()).isEqualTo(UPDATED_VORNAME);
        assertThat(testRezertifizierung.getVermittlerNummer()).isEqualTo(UPDATED_VERMITTLER_NUMMER);
        assertThat(testRezertifizierung.getRollenZugehoerigkeiten()).isEqualTo(UPDATED_ROLLEN_ZUGEHOERIGKEITEN);
        assertThat(testRezertifizierung.getDvcVertreterNummer()).isEqualTo(UPDATED_DVC_VERTRETER_NUMMER);
        assertThat(testRezertifizierung.getDvcBenutzerGruppe()).isEqualTo(UPDATED_DVC_BENUTZER_GRUPPE);
        assertThat(testRezertifizierung.getIcisSrGebaude()).isEqualTo(UPDATED_ICIS_SR_GEBAUDE);
        assertThat(testRezertifizierung.getIcisSrHaftpflicht()).isEqualTo(UPDATED_ICIS_SR_HAFTPFLICHT);
        assertThat(testRezertifizierung.getIcisSrLeitungswasser()).isEqualTo(UPDATED_ICIS_SR_LEITUNGSWASSER);
        assertThat(testRezertifizierung.getIcisSrKfzKasko()).isEqualTo(UPDATED_ICIS_SR_KFZ_KASKO);
        assertThat(testRezertifizierung.getBestandssicht()).isEqualTo(UPDATED_BESTANDSSICHT);
        assertThat(testRezertifizierung.getBemerkung()).isEqualTo(UPDATED_BEMERKUNG);
        assertThat(testRezertifizierung.getPruefungErfolgt()).isEqualTo(UPDATED_PRUEFUNG_ERFOLGT);
    }

    @Test
    @Transactional
    void patchNonExistingRezertifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezertifizierungRepository.findAll().size();
        rezertifizierung.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRezertifizierungMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rezertifizierung.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rezertifizierung))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRezertifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezertifizierungRepository.findAll().size();
        rezertifizierung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRezertifizierungMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rezertifizierung))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRezertifizierung() throws Exception {
        int databaseSizeBeforeUpdate = rezertifizierungRepository.findAll().size();
        rezertifizierung.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRezertifizierungMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rezertifizierung))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rezertifizierung in the database
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRezertifizierung() throws Exception {
        // Initialize the database
        rezertifizierungRepository.saveAndFlush(rezertifizierung);

        int databaseSizeBeforeDelete = rezertifizierungRepository.findAll().size();

        // Delete the rezertifizierung
        restRezertifizierungMockMvc
            .perform(delete(ENTITY_API_URL_ID, rezertifizierung.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rezertifizierung> rezertifizierungList = rezertifizierungRepository.findAll();
        assertThat(rezertifizierungList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

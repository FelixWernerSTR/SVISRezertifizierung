package de.svi.svis5g.rezertifizierung.service;

import de.svi.svis5g.rezertifizierung.domain.Rezertifizierung;
import de.svi.svis5g.rezertifizierung.repository.RezertifizierungRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Rezertifizierung}.
 */
@Service
@Transactional
public class RezertifizierungService {

    private final Logger log = LoggerFactory.getLogger(RezertifizierungService.class);

    private final RezertifizierungRepository rezertifizierungRepository;

    public RezertifizierungService(RezertifizierungRepository rezertifizierungRepository) {
        this.rezertifizierungRepository = rezertifizierungRepository;
    }

    /**
     * Save a rezertifizierung.
     *
     * @param rezertifizierung the entity to save.
     * @return the persisted entity.
     */
    public Rezertifizierung save(Rezertifizierung rezertifizierung) {
        log.debug("Request to save Rezertifizierung : {}", rezertifizierung);
        return rezertifizierungRepository.save(rezertifizierung);
    }

    /**
     * Partially update a rezertifizierung.
     *
     * @param rezertifizierung the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Rezertifizierung> partialUpdate(Rezertifizierung rezertifizierung) {
        log.debug("Request to partially update Rezertifizierung : {}", rezertifizierung);

        return rezertifizierungRepository
            .findById(rezertifizierung.getId())
            .map(
                existingRezertifizierung -> {
                    if (rezertifizierung.getLoginName() != null) {
                        existingRezertifizierung.setLoginName(rezertifizierung.getLoginName());
                    }
                    if (rezertifizierung.getNachname() != null) {
                        existingRezertifizierung.setNachname(rezertifizierung.getNachname());
                    }
                    if (rezertifizierung.getVorname() != null) {
                        existingRezertifizierung.setVorname(rezertifizierung.getVorname());
                    }
                    if (rezertifizierung.getVermittlerNummer() != null) {
                        existingRezertifizierung.setVermittlerNummer(rezertifizierung.getVermittlerNummer());
                    }
                    if (rezertifizierung.getRollenZugehoerigkeiten() != null) {
                        existingRezertifizierung.setRollenZugehoerigkeiten(rezertifizierung.getRollenZugehoerigkeiten());
                    }
                    if (rezertifizierung.getDvcVertreterNummer() != null) {
                        existingRezertifizierung.setDvcVertreterNummer(rezertifizierung.getDvcVertreterNummer());
                    }
                    if (rezertifizierung.getDvcBenutzerGruppe() != null) {
                        existingRezertifizierung.setDvcBenutzerGruppe(rezertifizierung.getDvcBenutzerGruppe());
                    }
                    if (rezertifizierung.getIcisSrGebaude() != null) {
                        existingRezertifizierung.setIcisSrGebaude(rezertifizierung.getIcisSrGebaude());
                    }
                    if (rezertifizierung.getIcisSrHaftpflicht() != null) {
                        existingRezertifizierung.setIcisSrHaftpflicht(rezertifizierung.getIcisSrHaftpflicht());
                    }
                    if (rezertifizierung.getIcisSrLeitungswasser() != null) {
                        existingRezertifizierung.setIcisSrLeitungswasser(rezertifizierung.getIcisSrLeitungswasser());
                    }
                    if (rezertifizierung.getIcisSrKfzKasko() != null) {
                        existingRezertifizierung.setIcisSrKfzKasko(rezertifizierung.getIcisSrKfzKasko());
                    }

                    return existingRezertifizierung;
                }
            )
            .map(rezertifizierungRepository::save);
    }

    /**
     * Get all the rezertifizierungs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Rezertifizierung> findAll(Pageable pageable) {
        log.debug("Request to get all Rezertifizierungs");
        return rezertifizierungRepository.findAll(pageable);
    }

    /**
     * Get one rezertifizierung by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Rezertifizierung> findOne(Long id) {
        log.debug("Request to get Rezertifizierung : {}", id);
        return rezertifizierungRepository.findById(id);
    }

    /**
     * Delete the rezertifizierung by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Rezertifizierung : {}", id);
        rezertifizierungRepository.deleteById(id);
    }
}

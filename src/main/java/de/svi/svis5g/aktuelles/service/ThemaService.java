package de.svi.svis5g.aktuelles.service;

import de.svi.svis5g.aktuelles.domain.Thema;
import de.svi.svis5g.aktuelles.repository.ThemaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Thema}.
 */
@Service
@Transactional
public class ThemaService {

    private final Logger log = LoggerFactory.getLogger(ThemaService.class);

    private final ThemaRepository themaRepository;

    public ThemaService(ThemaRepository themaRepository) {
        this.themaRepository = themaRepository;
    }

    /**
     * Save a thema.
     *
     * @param thema the entity to save.
     * @return the persisted entity.
     */
    public Thema save(Thema thema) {
        log.debug("Request to save Thema : {}", thema);
        return themaRepository.save(thema);
    }

    /**
     * Update a thema.
     *
     * @param thema the entity to save.
     * @return the persisted entity.
     */
    public Thema update(Thema thema) {
        log.debug("Request to save Thema : {}", thema);
        return themaRepository.save(thema);
    }

    /**
     * Partially update a thema.
     *
     * @param thema the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Thema> partialUpdate(Thema thema) {
        log.debug("Request to partially update Thema : {}", thema);

        return themaRepository
            .findById(thema.getId())
            .map(existingThema -> {
                if (thema.getName() != null) {
                    existingThema.setName(thema.getName());
                }
                if (thema.getRechte() != null) {
                    existingThema.setRechte(thema.getRechte());
                }
                if (thema.getDisplaycount() != null) {
                    existingThema.setDisplaycount(thema.getDisplaycount());
                }

                return existingThema;
            })
            .map(themaRepository::save);
    }

    /**
     * Get all the themas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Thema> findAll(Pageable pageable) {
        log.debug("Request to get all Themas");
        return themaRepository.findAll(pageable);
    }

    /**
     * Get all the themas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Thema> findAllWithEagerRelationships(Pageable pageable) {
        return themaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one thema by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Thema> findOne(Long id) {
        log.debug("Request to get Thema : {}", id);
        return themaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the thema by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Thema : {}", id);
        themaRepository.deleteById(id);
    }
}

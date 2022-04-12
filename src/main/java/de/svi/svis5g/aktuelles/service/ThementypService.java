package de.svi.svis5g.aktuelles.service;

import de.svi.svis5g.aktuelles.domain.Thementyp;
import de.svi.svis5g.aktuelles.repository.ThementypRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Thementyp}.
 */
@Service
@Transactional
public class ThementypService {

    private final Logger log = LoggerFactory.getLogger(ThementypService.class);

    private final ThementypRepository thementypRepository;

    public ThementypService(ThementypRepository thementypRepository) {
        this.thementypRepository = thementypRepository;
    }

    /**
     * Save a thementyp.
     *
     * @param thementyp the entity to save.
     * @return the persisted entity.
     */
    public Thementyp save(Thementyp thementyp) {
        log.debug("Request to save Thementyp : {}", thementyp);
        return thementypRepository.save(thementyp);
    }

    /**
     * Update a thementyp.
     *
     * @param thementyp the entity to save.
     * @return the persisted entity.
     */
    public Thementyp update(Thementyp thementyp) {
        log.debug("Request to save Thementyp : {}", thementyp);
        return thementypRepository.save(thementyp);
    }

    /**
     * Partially update a thementyp.
     *
     * @param thementyp the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Thementyp> partialUpdate(Thementyp thementyp) {
        log.debug("Request to partially update Thementyp : {}", thementyp);

        return thementypRepository
            .findById(thementyp.getId())
            .map(existingThementyp -> {
                if (thementyp.getName() != null) {
                    existingThementyp.setName(thementyp.getName());
                }

                return existingThementyp;
            })
            .map(thementypRepository::save);
    }

    /**
     * Get all the thementyps.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Thementyp> findAll() {
        log.debug("Request to get all Thementyps");
        return thementypRepository.findAll();
    }

    /**
     * Get one thementyp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Thementyp> findOne(Long id) {
        log.debug("Request to get Thementyp : {}", id);
        return thementypRepository.findById(id);
    }

    /**
     * Delete the thementyp by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Thementyp : {}", id);
        thementypRepository.deleteById(id);
    }
}

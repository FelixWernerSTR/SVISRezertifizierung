package de.svi.svis5g.aktuelles.service;

import de.svi.svis5g.aktuelles.domain.Beitrag;
import de.svi.svis5g.aktuelles.repository.BeitragRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Beitrag}.
 */
@Service
@Transactional
public class BeitragService {

    private final Logger log = LoggerFactory.getLogger(BeitragService.class);

    private final BeitragRepository beitragRepository;

    public BeitragService(BeitragRepository beitragRepository) {
        this.beitragRepository = beitragRepository;
    }

    /**
     * Save a beitrag.
     *
     * @param beitrag the entity to save.
     * @return the persisted entity.
     */
    public Beitrag save(Beitrag beitrag) {
        log.debug("Request to save Beitrag : {}", beitrag);
        return beitragRepository.save(beitrag);
    }

    /**
     * Update a beitrag.
     *
     * @param beitrag the entity to save.
     * @return the persisted entity.
     */
    public Beitrag update(Beitrag beitrag) {
        log.debug("Request to save Beitrag : {}", beitrag);
        return beitragRepository.save(beitrag);
    }

    /**
     * Partially update a beitrag.
     *
     * @param beitrag the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Beitrag> partialUpdate(Beitrag beitrag) {
        log.debug("Request to partially update Beitrag : {}", beitrag);

        return beitragRepository
            .findById(beitrag.getId())
            .map(existingBeitrag -> {
                if (beitrag.getContent() != null) {
                    existingBeitrag.setContent(beitrag.getContent());
                }
                if (beitrag.getAttrib() != null) {
                    existingBeitrag.setAttrib(beitrag.getAttrib());
                }
                if (beitrag.getTitel() != null) {
                    existingBeitrag.setTitel(beitrag.getTitel());
                }
                if (beitrag.getRechte() != null) {
                    existingBeitrag.setRechte(beitrag.getRechte());
                }
                if (beitrag.getValidFrom() != null) {
                    existingBeitrag.setValidFrom(beitrag.getValidFrom());
                }
                if (beitrag.getValidTo() != null) {
                    existingBeitrag.setValidTo(beitrag.getValidTo());
                }
                if (beitrag.getPublishDate() != null) {
                    existingBeitrag.setPublishDate(beitrag.getPublishDate());
                }
                if (beitrag.getArchiv() != null) {
                    existingBeitrag.setArchiv(beitrag.getArchiv());
                }

                return existingBeitrag;
            })
            .map(beitragRepository::save);
    }

    /**
     * Get all the beitrags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Beitrag> findAll(Pageable pageable) {
        log.debug("Request to get all Beitrags");
        return beitragRepository.findAll(pageable);
    }

    /**
     * Get all the beitrags with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Beitrag> findAllWithEagerRelationships(Pageable pageable) {
        return beitragRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one beitrag by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Beitrag> findOne(Long id) {
        log.debug("Request to get Beitrag : {}", id);
        return beitragRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the beitrag by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Beitrag : {}", id);
        beitragRepository.deleteById(id);
    }
}

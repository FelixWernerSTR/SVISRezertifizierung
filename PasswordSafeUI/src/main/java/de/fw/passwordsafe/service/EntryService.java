package de.fw.passwordsafe.service;

import de.fw.passwordsafe.domain.Entry;
import de.fw.passwordsafe.repository.EntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Entry}.
 */
@Service
@Transactional
public class EntryService {

    private final Logger log = LoggerFactory.getLogger(EntryService.class);

    private final EntryRepository entryRepository;

    public EntryService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    /**
     * Save a entry.
     *
     * @param entry the entity to save.
     * @return the persisted entity.
     */
    public Mono<Entry> save(Entry entry) {
        log.debug("Request to save Entry : {}", entry);
        return entryRepository.save(entry);
    }

    /**
     * Update a entry.
     *
     * @param entry the entity to save.
     * @return the persisted entity.
     */
    public Mono<Entry> update(Entry entry) {
        log.debug("Request to save Entry : {}", entry);
        return entryRepository.save(entry);
    }

    /**
     * Partially update a entry.
     *
     * @param entry the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<Entry> partialUpdate(Entry entry) {
        log.debug("Request to partially update Entry : {}", entry);

        return entryRepository
            .findById(entry.getId())
            .map(existingEntry -> {
                if (entry.getDescription() != null) {
                    existingEntry.setDescription(entry.getDescription());
                }
                if (entry.getLogin() != null) {
                    existingEntry.setLogin(entry.getLogin());
                }
                if (entry.getPasswort() != null) {
                    existingEntry.setPasswort(entry.getPasswort());
                }
                if (entry.getPasswortReplay() != null) {
                    existingEntry.setPasswortReplay(entry.getPasswortReplay());
                }

                return existingEntry;
            })
            .flatMap(entryRepository::save);
    }

    /**
     * Get all the entries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<Entry> findAll(Pageable pageable) {
        log.debug("Request to get all Entries");
        return entryRepository.findAllBy(pageable);
    }

    /**
     * Get all the entries with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Flux<Entry> findAllWithEagerRelationships(Pageable pageable) {
        return entryRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Returns the number of entries available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return entryRepository.count();
    }

    /**
     * Get one entry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<Entry> findOne(Long id) {
        log.debug("Request to get Entry : {}", id);
        return entryRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the entry by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Entry : {}", id);
        return entryRepository.deleteById(id);
    }
}

package de.fw.passwordsafe.repository;

import de.fw.passwordsafe.domain.Entry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Entry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntryRepository extends ReactiveCrudRepository<Entry, Long>, EntryRepositoryInternal {
    Flux<Entry> findAllBy(Pageable pageable);

    @Override
    Mono<Entry> findOneWithEagerRelationships(Long id);

    @Override
    Flux<Entry> findAllWithEagerRelationships();

    @Override
    Flux<Entry> findAllWithEagerRelationships(Pageable page);

    @Query("SELECT * FROM entry entity WHERE entity.group_id = :id")
    Flux<Entry> findByGroup(Long id);

    @Query("SELECT * FROM entry entity WHERE entity.group_id IS NULL")
    Flux<Entry> findAllWhereGroupIsNull();

    @Override
    <S extends Entry> Mono<S> save(S entity);

    @Override
    Flux<Entry> findAll();

    @Override
    Mono<Entry> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface EntryRepositoryInternal {
    <S extends Entry> Mono<S> save(S entity);

    Flux<Entry> findAllBy(Pageable pageable);

    Flux<Entry> findAll();

    Mono<Entry> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Entry> findAllBy(Pageable pageable, Criteria criteria);

    Mono<Entry> findOneWithEagerRelationships(Long id);

    Flux<Entry> findAllWithEagerRelationships();

    Flux<Entry> findAllWithEagerRelationships(Pageable page);

    Mono<Void> deleteById(Long id);
}

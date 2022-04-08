package de.fw.passwordsafe.repository;

import static org.springframework.data.relational.core.query.Criteria.where;

import de.fw.passwordsafe.domain.Entry;
import de.fw.passwordsafe.repository.rowmapper.EntryRowMapper;
import de.fw.passwordsafe.repository.rowmapper.GroupRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiFunction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Comparison;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Conditions;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoinCondition;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive custom repository implementation for the Entry entity.
 */
@SuppressWarnings("unused")
class EntryRepositoryInternalImpl extends SimpleR2dbcRepository<Entry, Long> implements EntryRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final GroupRowMapper groupMapper;
    private final EntryRowMapper entryMapper;

    private static final Table entityTable = Table.aliased("entry", EntityManager.ENTITY_ALIAS);
    private static final Table groupTable = Table.aliased("jhi_group", "e_group");

    public EntryRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        GroupRowMapper groupMapper,
        EntryRowMapper entryMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Entry.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.groupMapper = groupMapper;
        this.entryMapper = entryMapper;
    }

    @Override
    public Flux<Entry> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Entry> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = EntrySqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(GroupSqlHelper.getColumns(groupTable, "group"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(groupTable)
            .on(Column.create("group_id", entityTable))
            .equals(Column.create("id", groupTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Entry.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Entry> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Entry> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    @Override
    public Mono<Entry> findOneWithEagerRelationships(Long id) {
        return findById(id);
    }

    @Override
    public Flux<Entry> findAllWithEagerRelationships() {
        return findAll();
    }

    @Override
    public Flux<Entry> findAllWithEagerRelationships(Pageable page) {
        return findAllBy(page);
    }

    private Entry process(Row row, RowMetadata metadata) {
        Entry entity = entryMapper.apply(row, "e");
        entity.setGroup(groupMapper.apply(row, "group"));
        return entity;
    }

    @Override
    public <S extends Entry> Mono<S> save(S entity) {
        return super.save(entity);
    }
}

package de.fw.passwordsafe.repository.rowmapper;

import de.fw.passwordsafe.domain.Entry;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Entry}, with proper type conversions.
 */
@Service
public class EntryRowMapper implements BiFunction<Row, String, Entry> {

    private final ColumnConverter converter;

    public EntryRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Entry} stored in the database.
     */
    @Override
    public Entry apply(Row row, String prefix) {
        Entry entity = new Entry();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setLogin(converter.fromRow(row, prefix + "_login", String.class));
        entity.setPasswort(converter.fromRow(row, prefix + "_passwort", String.class));
        entity.setPasswortReplay(converter.fromRow(row, prefix + "_passwort_replay", String.class));
        entity.setGroupId(converter.fromRow(row, prefix + "_group_id", Long.class));
        return entity;
    }
}

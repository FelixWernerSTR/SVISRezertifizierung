package de.svi.svis5g.aktuelles.repository;

import de.svi.svis5g.aktuelles.domain.Thema;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Thema entity.
 */
@Repository
public interface ThemaRepository extends JpaRepository<Thema, Long> {
    default Optional<Thema> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Thema> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Thema> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct thema from Thema thema left join fetch thema.thementyp",
        countQuery = "select count(distinct thema) from Thema thema"
    )
    Page<Thema> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct thema from Thema thema left join fetch thema.thementyp")
    List<Thema> findAllWithToOneRelationships();

    @Query("select thema from Thema thema left join fetch thema.thementyp where thema.id =:id")
    Optional<Thema> findOneWithToOneRelationships(@Param("id") Long id);
}

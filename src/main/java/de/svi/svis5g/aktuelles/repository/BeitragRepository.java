package de.svi.svis5g.aktuelles.repository;

import de.svi.svis5g.aktuelles.domain.Beitrag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Beitrag entity.
 */
@Repository
public interface BeitragRepository extends JpaRepository<Beitrag, Long> {
    default Optional<Beitrag> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Beitrag> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Beitrag> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct beitrag from Beitrag beitrag left join fetch beitrag.thema",
        countQuery = "select count(distinct beitrag) from Beitrag beitrag"
    )
    Page<Beitrag> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct beitrag from Beitrag beitrag left join fetch beitrag.thema")
    List<Beitrag> findAllWithToOneRelationships();

    @Query("select beitrag from Beitrag beitrag left join fetch beitrag.thema where beitrag.id =:id")
    Optional<Beitrag> findOneWithToOneRelationships(@Param("id") Long id);
}

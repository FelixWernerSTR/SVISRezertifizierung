package de.svi.svis5g.rezertifizierung.repository;

import de.svi.svis5g.rezertifizierung.domain.Rezertifizierung;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Rezertifizierung entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RezertifizierungRepository extends JpaRepository<Rezertifizierung, Long> {}

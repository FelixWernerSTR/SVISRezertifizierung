package de.svi.svis5g.aktuelles.domain;

import static org.assertj.core.api.Assertions.assertThat;

import de.svi.svis5g.aktuelles.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BeitragTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beitrag.class);
        Beitrag beitrag1 = new Beitrag();
        beitrag1.setId(1L);
        Beitrag beitrag2 = new Beitrag();
        beitrag2.setId(beitrag1.getId());
        assertThat(beitrag1).isEqualTo(beitrag2);
        beitrag2.setId(2L);
        assertThat(beitrag1).isNotEqualTo(beitrag2);
        beitrag1.setId(null);
        assertThat(beitrag1).isNotEqualTo(beitrag2);
    }
}

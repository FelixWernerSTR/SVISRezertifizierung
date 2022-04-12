package de.svi.svis5g.aktuelles.domain;

import static org.assertj.core.api.Assertions.assertThat;

import de.svi.svis5g.aktuelles.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThemaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Thema.class);
        Thema thema1 = new Thema();
        thema1.setId(1L);
        Thema thema2 = new Thema();
        thema2.setId(thema1.getId());
        assertThat(thema1).isEqualTo(thema2);
        thema2.setId(2L);
        assertThat(thema1).isNotEqualTo(thema2);
        thema1.setId(null);
        assertThat(thema1).isNotEqualTo(thema2);
    }
}

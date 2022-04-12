package de.svi.svis5g.aktuelles.domain;

import static org.assertj.core.api.Assertions.assertThat;

import de.svi.svis5g.aktuelles.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThementypTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Thementyp.class);
        Thementyp thementyp1 = new Thementyp();
        thementyp1.setId(1L);
        Thementyp thementyp2 = new Thementyp();
        thementyp2.setId(thementyp1.getId());
        assertThat(thementyp1).isEqualTo(thementyp2);
        thementyp2.setId(2L);
        assertThat(thementyp1).isNotEqualTo(thementyp2);
        thementyp1.setId(null);
        assertThat(thementyp1).isNotEqualTo(thementyp2);
    }
}

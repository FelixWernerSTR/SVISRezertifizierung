package de.svi.svis5g.rezertifizierung.domain;

import static org.assertj.core.api.Assertions.assertThat;

import de.svi.svis5g.rezertifizierung.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RezertifizierungTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rezertifizierung.class);
        Rezertifizierung rezertifizierung1 = new Rezertifizierung();
        rezertifizierung1.setId(1L);
        Rezertifizierung rezertifizierung2 = new Rezertifizierung();
        rezertifizierung2.setId(rezertifizierung1.getId());
        assertThat(rezertifizierung1).isEqualTo(rezertifizierung2);
        rezertifizierung2.setId(2L);
        assertThat(rezertifizierung1).isNotEqualTo(rezertifizierung2);
        rezertifizierung1.setId(null);
        assertThat(rezertifizierung1).isNotEqualTo(rezertifizierung2);
    }
}

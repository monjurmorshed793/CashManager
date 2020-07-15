package org.cash.manager.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.cash.manager.web.rest.TestUtil;

public class ExpanseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Expanse.class);
        Expanse expanse1 = new Expanse();
        expanse1.setId(1L);
        Expanse expanse2 = new Expanse();
        expanse2.setId(expanse1.getId());
        assertThat(expanse1).isEqualTo(expanse2);
        expanse2.setId(2L);
        assertThat(expanse1).isNotEqualTo(expanse2);
        expanse1.setId(null);
        assertThat(expanse1).isNotEqualTo(expanse2);
    }
}

package org.cash.manager.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.cash.manager.web.rest.TestUtil;

public class ExpanseDtlTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpanseDtl.class);
        ExpanseDtl expanseDtl1 = new ExpanseDtl();
        expanseDtl1.setId(1L);
        ExpanseDtl expanseDtl2 = new ExpanseDtl();
        expanseDtl2.setId(expanseDtl1.getId());
        assertThat(expanseDtl1).isEqualTo(expanseDtl2);
        expanseDtl2.setId(2L);
        assertThat(expanseDtl1).isNotEqualTo(expanseDtl2);
        expanseDtl1.setId(null);
        assertThat(expanseDtl1).isNotEqualTo(expanseDtl2);
    }
}

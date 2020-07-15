package org.cash.manager.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.cash.manager.web.rest.TestUtil;

public class ExpanseDtlDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpanseDtlDTO.class);
        ExpanseDtlDTO expanseDtlDTO1 = new ExpanseDtlDTO();
        expanseDtlDTO1.setId(1L);
        ExpanseDtlDTO expanseDtlDTO2 = new ExpanseDtlDTO();
        assertThat(expanseDtlDTO1).isNotEqualTo(expanseDtlDTO2);
        expanseDtlDTO2.setId(expanseDtlDTO1.getId());
        assertThat(expanseDtlDTO1).isEqualTo(expanseDtlDTO2);
        expanseDtlDTO2.setId(2L);
        assertThat(expanseDtlDTO1).isNotEqualTo(expanseDtlDTO2);
        expanseDtlDTO1.setId(null);
        assertThat(expanseDtlDTO1).isNotEqualTo(expanseDtlDTO2);
    }
}

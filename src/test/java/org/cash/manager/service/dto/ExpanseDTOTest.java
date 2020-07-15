package org.cash.manager.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.cash.manager.web.rest.TestUtil;

public class ExpanseDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpanseDTO.class);
        ExpanseDTO expanseDTO1 = new ExpanseDTO();
        expanseDTO1.setId(1L);
        ExpanseDTO expanseDTO2 = new ExpanseDTO();
        assertThat(expanseDTO1).isNotEqualTo(expanseDTO2);
        expanseDTO2.setId(expanseDTO1.getId());
        assertThat(expanseDTO1).isEqualTo(expanseDTO2);
        expanseDTO2.setId(2L);
        assertThat(expanseDTO1).isNotEqualTo(expanseDTO2);
        expanseDTO1.setId(null);
        assertThat(expanseDTO1).isNotEqualTo(expanseDTO2);
    }
}

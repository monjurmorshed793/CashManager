package org.cash.manager.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ExpanseDtlMapperTest {

    private ExpanseDtlMapper expanseDtlMapper;

    @BeforeEach
    public void setUp() {
        expanseDtlMapper = new ExpanseDtlMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(expanseDtlMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(expanseDtlMapper.fromId(null)).isNull();
    }
}

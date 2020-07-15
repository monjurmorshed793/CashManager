package org.cash.manager.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ExpanseMapperTest {

    private ExpanseMapper expanseMapper;

    @BeforeEach
    public void setUp() {
        expanseMapper = new ExpanseMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(expanseMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(expanseMapper.fromId(null)).isNull();
    }
}

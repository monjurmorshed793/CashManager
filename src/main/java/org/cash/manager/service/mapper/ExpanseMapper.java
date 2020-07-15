package org.cash.manager.service.mapper;


import org.cash.manager.domain.*;
import org.cash.manager.service.dto.ExpanseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Expanse} and its DTO {@link ExpanseDTO}.
 */
@Mapper(componentModel = "spring", uses = {PayToMapper.class})
public interface ExpanseMapper extends EntityMapper<ExpanseDTO, Expanse> {

    @Mapping(source = "payTo.id", target = "payToId")
    @Mapping(source = "payTo.name", target = "payToName")
    ExpanseDTO toDto(Expanse expanse);

    @Mapping(source = "payToId", target = "payTo")
    Expanse toEntity(ExpanseDTO expanseDTO);

    default Expanse fromId(Long id) {
        if (id == null) {
            return null;
        }
        Expanse expanse = new Expanse();
        expanse.setId(id);
        return expanse;
    }
}

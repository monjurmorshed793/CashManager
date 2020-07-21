package org.cash.manager.service.mapper;


import org.cash.manager.domain.*;
import org.cash.manager.service.dto.ExpanseDtlDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExpanseDtl} and its DTO {@link ExpanseDtlDTO}.
 */
@Mapper(componentModel = "spring", uses = {ItemMapper.class, ExpanseMapper.class})
public interface ExpanseDtlMapper extends EntityMapper<ExpanseDtlDTO, ExpanseDtl> {

    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "item.name", target = "itemName")
    @Mapping(source = "expanse.id", target = "expanseId")
    @Mapping(source = "expanse.voucherNo", target = "expanseVoucherNo")
    ExpanseDtlDTO toDto(ExpanseDtl expanseDtl);

    @Mapping(source = "itemId", target = "item")
    @Mapping(source = "expanseId", target = "expanse")
    ExpanseDtl toEntity(ExpanseDtlDTO expanseDtlDTO);

    default ExpanseDtl fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExpanseDtl expanseDtl = new ExpanseDtl();
        expanseDtl.setId(id);
        return expanseDtl;
    }
}

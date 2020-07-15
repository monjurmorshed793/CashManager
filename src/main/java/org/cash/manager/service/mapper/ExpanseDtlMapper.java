package org.cash.manager.service.mapper;


import org.cash.manager.domain.*;
import org.cash.manager.service.dto.ExpanseDtlDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExpanseDtl} and its DTO {@link ExpanseDtlDTO}.
 */
@Mapper(componentModel = "spring", uses = {ExpanseMapper.class, ItemMapper.class})
public interface ExpanseDtlMapper extends EntityMapper<ExpanseDtlDTO, ExpanseDtl> {

    @Mapping(source = "expanse.id", target = "expanseId")
    @Mapping(source = "expanse.voucherNo", target = "expanseVoucherNo")
    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "item.name", target = "itemName")
    ExpanseDtlDTO toDto(ExpanseDtl expanseDtl);

    @Mapping(source = "expanseId", target = "expanse")
    @Mapping(source = "itemId", target = "item")
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

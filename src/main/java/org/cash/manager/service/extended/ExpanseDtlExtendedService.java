package org.cash.manager.service.extended;

import org.cash.manager.domain.ExpanseDtl;
import org.cash.manager.repository.ExpanseDtlRepository;
import org.cash.manager.repository.ExpanseRepository;
import org.cash.manager.repository.extended.ExpanseDtlExtendedRepository;
import org.cash.manager.service.ExpanseDtlService;
import org.cash.manager.service.ExpanseService;
import org.cash.manager.service.dto.ExpanseDTO;
import org.cash.manager.service.dto.ExpanseDtlDTO;
import org.cash.manager.service.mapper.ExpanseDtlMapper;
import org.cash.manager.service.mapper.ExpanseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
@Transactional
public class ExpanseDtlExtendedService extends ExpanseDtlService {
    private final ExpanseService expanseService;
    private final ExpanseDtlExtendedRepository expanseDtlExtendedRepository;

    public ExpanseDtlExtendedService(ExpanseDtlRepository expanseDtlRepository, ExpanseDtlMapper expanseDtlMapper, ExpanseService expanseService, ExpanseDtlExtendedRepository expanseDtlExtendedRepository) {
        super(expanseDtlRepository, expanseDtlMapper);
        this.expanseService = expanseService;
        this.expanseDtlExtendedRepository = expanseDtlExtendedRepository;
    }

    @Override
    public ExpanseDtlDTO save(ExpanseDtlDTO expanseDtlDTO) {
        expanseDtlDTO = super.save(expanseDtlDTO);

        ExpanseDTO expanseDTO = expanseService.findOne(expanseDtlDTO.getExpanseId()).get();
        List<ExpanseDtl> expanseDtls = expanseDtlExtendedRepository.findAllByExpanse_Id(expanseDTO.getId());
        BigDecimal totalAmount = BigDecimal.ZERO;
        for(ExpanseDtl expanseDtl: expanseDtls){
            totalAmount = totalAmount.add(expanseDtl.getAmount());
        }
        expanseDTO.setTotalAmount(totalAmount);
        expanseService.save(expanseDTO);
        return expanseDtlDTO;
    }
}

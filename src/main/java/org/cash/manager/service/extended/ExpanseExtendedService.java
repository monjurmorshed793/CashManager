package org.cash.manager.service.extended;

import org.cash.manager.repository.ExpanseRepository;
import org.cash.manager.repository.extended.ExpanseExtendedRepository;
import org.cash.manager.service.ExpanseService;
import org.cash.manager.service.dto.ExpanseDTO;
import org.cash.manager.service.mapper.ExpanseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class ExpanseExtendedService extends ExpanseService {
    private final ExpanseExtendedRepository expanseExtendedRepository;

    public ExpanseExtendedService(ExpanseRepository expanseRepository, ExpanseMapper expanseMapper, ExpanseExtendedRepository expanseExtendedRepository) {
        super(expanseRepository, expanseMapper);
        this.expanseExtendedRepository = expanseExtendedRepository;
    }

    @Override
    public ExpanseDTO save(ExpanseDTO expanseDTO) {
        if(expanseDTO.getId()==null){
            Integer year = LocalDate.now().getYear();
            String voucherNumberStr = (year.toString()).substring(2,3)+String.format("%08d", expanseExtendedRepository.count());
            expanseDTO.setVoucherNo(Integer.parseInt(voucherNumberStr));
        }
        return super.save(expanseDTO);
    }
}

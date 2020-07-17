package org.cash.manager.service.extended;

import org.cash.manager.domain.ExpanseSeq;
import org.cash.manager.repository.ExpanseRepository;
import org.cash.manager.repository.ExpanseSeqRepository;
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
    private ExpanseSeqRepository expanseSeqRepository;

    public ExpanseExtendedService(ExpanseRepository expanseRepository,
                                  ExpanseMapper expanseMapper,
                                  ExpanseExtendedRepository expanseExtendedRepository,
                                  ExpanseSeqRepository expanseSeqRepository) {
        super(expanseRepository, expanseMapper);
        this.expanseExtendedRepository = expanseExtendedRepository;
        this.expanseSeqRepository = expanseSeqRepository;
    }

    @Override
    public ExpanseDTO save(ExpanseDTO expanseDTO) {
        if(expanseDTO.getId()==null){
            Integer year = LocalDate.now().getYear();
            ExpanseSeq expanseSeq = expanseSeqRepository.save(new ExpanseSeq());
            String voucherNumberStr = (year.toString()).substring(2,3)+String.format("%08d", expanseSeq.getId());
            expanseDTO.setVoucherNo(Integer.parseInt(voucherNumberStr));
        }
        return super.save(expanseDTO);
    }
}

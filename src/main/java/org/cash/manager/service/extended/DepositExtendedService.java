package org.cash.manager.service.extended;

import org.cash.manager.domain.DepositSeq;
import org.cash.manager.repository.DepositRepository;
import org.cash.manager.repository.DepositSeqRepository;
import org.cash.manager.repository.extended.DepositExtendedRepository;
import org.cash.manager.service.DepositService;
import org.cash.manager.service.dto.DepositDTO;
import org.cash.manager.service.mapper.DepositMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;

@Service
@Transactional
public class DepositExtendedService extends DepositService {

    private final DepositExtendedRepository depositExtendedRepository;
    private DepositSeqRepository depositSeqRepository;

    public DepositExtendedService(DepositRepository depositRepository,
                                  DepositMapper depositMapper,
                                  DepositExtendedRepository depositExtendedRepository,
                                  DepositSeqRepository depositSeqRepository) {
        super(depositRepository, depositMapper);
        this.depositExtendedRepository = depositExtendedRepository;
        this.depositSeqRepository = depositSeqRepository;
    }

    @Override
    public DepositDTO save(DepositDTO depositDTO) {
        if(depositDTO.getDepositNo()==null){
            Integer year = LocalDate.now().getYear();
            DepositSeq depositSeq = depositSeqRepository.save(new DepositSeq());
            String voucherNumberStr = (year.toString()).substring(2,3)+String.format("%08d", depositSeq.getId());
            depositDTO.setDepositNo(Integer.parseInt(voucherNumberStr));
        }
        if(depositDTO.isIsPosted() && depositDTO.getPostDate()==null)
            depositDTO.setPostDate(Instant.now());
        return super.save(depositDTO);
    }
}

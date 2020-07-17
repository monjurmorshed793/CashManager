package org.cash.manager.service.extended;

import org.cash.manager.repository.DepositRepository;
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

    public DepositExtendedService(DepositRepository depositRepository, DepositMapper depositMapper, DepositExtendedRepository depositExtendedRepository) {
        super(depositRepository, depositMapper);
        this.depositExtendedRepository = depositExtendedRepository;
    }

    @Override
    public DepositDTO save(DepositDTO depositDTO) {
        if(depositDTO.getDepositNo()==null){
            Integer year = LocalDate.now().getYear();
            String voucherNumberStr = (year.toString()).substring(2,3)+String.format("%08d", depositExtendedRepository.count());
            depositDTO.setDepositNo(Integer.parseInt(voucherNumberStr));
        }
        if(depositDTO.isIsPosted() && depositDTO.getPostDate()==null)
            depositDTO.setPostDate(Instant.now());
        return super.save(depositDTO);
    }
}

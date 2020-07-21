package org.cash.manager.repository.extended;

import org.cash.manager.domain.ExpanseDtl;
import org.cash.manager.repository.ExpanseDtlRepository;

import java.util.List;

public interface ExpanseDtlExtendedRepository extends ExpanseDtlRepository {
    List<ExpanseDtl> findAllByExpanse_Id(Long expanseId);
}

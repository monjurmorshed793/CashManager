package org.cash.manager.repository;

import org.cash.manager.domain.ExpanseDtl;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ExpanseDtl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExpanseDtlRepository extends JpaRepository<ExpanseDtl, Long>, JpaSpecificationExecutor<ExpanseDtl> {
}

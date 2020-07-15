package org.cash.manager.repository;

import org.cash.manager.domain.Expanse;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Expanse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExpanseRepository extends JpaRepository<Expanse, Long>, JpaSpecificationExecutor<Expanse> {
}

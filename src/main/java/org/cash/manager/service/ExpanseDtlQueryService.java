package org.cash.manager.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import org.cash.manager.domain.ExpanseDtl;
import org.cash.manager.domain.*; // for static metamodels
import org.cash.manager.repository.ExpanseDtlRepository;
import org.cash.manager.service.dto.ExpanseDtlCriteria;
import org.cash.manager.service.dto.ExpanseDtlDTO;
import org.cash.manager.service.mapper.ExpanseDtlMapper;

/**
 * Service for executing complex queries for {@link ExpanseDtl} entities in the database.
 * The main input is a {@link ExpanseDtlCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExpanseDtlDTO} or a {@link Page} of {@link ExpanseDtlDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExpanseDtlQueryService extends QueryService<ExpanseDtl> {

    private final Logger log = LoggerFactory.getLogger(ExpanseDtlQueryService.class);

    private final ExpanseDtlRepository expanseDtlRepository;

    private final ExpanseDtlMapper expanseDtlMapper;

    public ExpanseDtlQueryService(ExpanseDtlRepository expanseDtlRepository, ExpanseDtlMapper expanseDtlMapper) {
        this.expanseDtlRepository = expanseDtlRepository;
        this.expanseDtlMapper = expanseDtlMapper;
    }

    /**
     * Return a {@link List} of {@link ExpanseDtlDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExpanseDtlDTO> findByCriteria(ExpanseDtlCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ExpanseDtl> specification = createSpecification(criteria);
        return expanseDtlMapper.toDto(expanseDtlRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ExpanseDtlDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExpanseDtlDTO> findByCriteria(ExpanseDtlCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ExpanseDtl> specification = createSpecification(criteria);
        return expanseDtlRepository.findAll(specification, page)
            .map(expanseDtlMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExpanseDtlCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ExpanseDtl> specification = createSpecification(criteria);
        return expanseDtlRepository.count(specification);
    }

    /**
     * Function to convert {@link ExpanseDtlCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ExpanseDtl> createSpecification(ExpanseDtlCriteria criteria) {
        Specification<ExpanseDtl> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ExpanseDtl_.id));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), ExpanseDtl_.quantity));
            }
            if (criteria.getUnitPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitPrice(), ExpanseDtl_.unitPrice));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), ExpanseDtl_.amount));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ExpanseDtl_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), ExpanseDtl_.createdOn));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ExpanseDtl_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), ExpanseDtl_.modifiedOn));
            }
            if (criteria.getExpanseId() != null) {
                specification = specification.and(buildSpecification(criteria.getExpanseId(),
                    root -> root.join(ExpanseDtl_.expanse, JoinType.LEFT).get(Expanse_.id)));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemId(),
                    root -> root.join(ExpanseDtl_.item, JoinType.LEFT).get(Item_.id)));
            }
        }
        return specification;
    }
}

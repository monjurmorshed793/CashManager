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

import org.cash.manager.domain.Expanse;
import org.cash.manager.domain.*; // for static metamodels
import org.cash.manager.repository.ExpanseRepository;
import org.cash.manager.service.dto.ExpanseCriteria;
import org.cash.manager.service.dto.ExpanseDTO;
import org.cash.manager.service.mapper.ExpanseMapper;

/**
 * Service for executing complex queries for {@link Expanse} entities in the database.
 * The main input is a {@link ExpanseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExpanseDTO} or a {@link Page} of {@link ExpanseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExpanseQueryService extends QueryService<Expanse> {

    private final Logger log = LoggerFactory.getLogger(ExpanseQueryService.class);

    private final ExpanseRepository expanseRepository;

    private final ExpanseMapper expanseMapper;

    public ExpanseQueryService(ExpanseRepository expanseRepository, ExpanseMapper expanseMapper) {
        this.expanseRepository = expanseRepository;
        this.expanseMapper = expanseMapper;
    }

    /**
     * Return a {@link List} of {@link ExpanseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExpanseDTO> findByCriteria(ExpanseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Expanse> specification = createSpecification(criteria);
        return expanseMapper.toDto(expanseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ExpanseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExpanseDTO> findByCriteria(ExpanseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Expanse> specification = createSpecification(criteria);
        return expanseRepository.findAll(specification, page)
            .map(expanseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExpanseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Expanse> specification = createSpecification(criteria);
        return expanseRepository.count(specification);
    }

    /**
     * Function to convert {@link ExpanseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Expanse> createSpecification(ExpanseCriteria criteria) {
        Specification<Expanse> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Expanse_.id));
            }
            if (criteria.getLoginId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoginId(), Expanse_.loginId));
            }
            if (criteria.getVoucherNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVoucherNo(), Expanse_.voucherNo));
            }
            if (criteria.getVoucherDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVoucherDate(), Expanse_.voucherDate));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildSpecification(criteria.getMonth(), Expanse_.month));
            }
            if (criteria.getTotalAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalAmount(), Expanse_.totalAmount));
            }
            if (criteria.getIsPosted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPosted(), Expanse_.isPosted));
            }
            if (criteria.getPostDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostDate(), Expanse_.postDate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Expanse_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), Expanse_.createdOn));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), Expanse_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), Expanse_.modifiedOn));
            }
            if (criteria.getExpanseDtlId() != null) {
                specification = specification.and(buildSpecification(criteria.getExpanseDtlId(),
                    root -> root.join(Expanse_.expanseDtls, JoinType.LEFT).get(ExpanseDtl_.id)));
            }
            if (criteria.getPayToId() != null) {
                specification = specification.and(buildSpecification(criteria.getPayToId(),
                    root -> root.join(Expanse_.payTo, JoinType.LEFT).get(PayTo_.id)));
            }
            if(criteria.getItemId() !=null){
                specification = specification.and(buildSpecification(criteria.getItemId(),
                    root -> root.joinSet("expanseDtls", JoinType.LEFT).get("item").get("id")));
            }
        }
        return specification;
    }
}

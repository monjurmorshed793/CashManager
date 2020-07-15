package org.cash.manager.service;

import org.cash.manager.domain.ExpanseDtl;
import org.cash.manager.repository.ExpanseDtlRepository;
import org.cash.manager.service.dto.ExpanseDtlDTO;
import org.cash.manager.service.mapper.ExpanseDtlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ExpanseDtl}.
 */
@Service
@Transactional
public class ExpanseDtlService {

    private final Logger log = LoggerFactory.getLogger(ExpanseDtlService.class);

    private final ExpanseDtlRepository expanseDtlRepository;

    private final ExpanseDtlMapper expanseDtlMapper;

    public ExpanseDtlService(ExpanseDtlRepository expanseDtlRepository, ExpanseDtlMapper expanseDtlMapper) {
        this.expanseDtlRepository = expanseDtlRepository;
        this.expanseDtlMapper = expanseDtlMapper;
    }

    /**
     * Save a expanseDtl.
     *
     * @param expanseDtlDTO the entity to save.
     * @return the persisted entity.
     */
    public ExpanseDtlDTO save(ExpanseDtlDTO expanseDtlDTO) {
        log.debug("Request to save ExpanseDtl : {}", expanseDtlDTO);
        ExpanseDtl expanseDtl = expanseDtlMapper.toEntity(expanseDtlDTO);
        expanseDtl = expanseDtlRepository.save(expanseDtl);
        return expanseDtlMapper.toDto(expanseDtl);
    }

    /**
     * Get all the expanseDtls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ExpanseDtlDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExpanseDtls");
        return expanseDtlRepository.findAll(pageable)
            .map(expanseDtlMapper::toDto);
    }


    /**
     * Get one expanseDtl by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExpanseDtlDTO> findOne(Long id) {
        log.debug("Request to get ExpanseDtl : {}", id);
        return expanseDtlRepository.findById(id)
            .map(expanseDtlMapper::toDto);
    }

    /**
     * Delete the expanseDtl by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ExpanseDtl : {}", id);
        expanseDtlRepository.deleteById(id);
    }
}

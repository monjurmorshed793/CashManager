package org.cash.manager.service;

import org.cash.manager.domain.Expanse;
import org.cash.manager.repository.ExpanseRepository;
import org.cash.manager.service.dto.ExpanseDTO;
import org.cash.manager.service.mapper.ExpanseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Expanse}.
 */
@Service
@Transactional
public class ExpanseService {

    private final Logger log = LoggerFactory.getLogger(ExpanseService.class);

    private final ExpanseRepository expanseRepository;

    private final ExpanseMapper expanseMapper;

    public ExpanseService(ExpanseRepository expanseRepository, ExpanseMapper expanseMapper) {
        this.expanseRepository = expanseRepository;
        this.expanseMapper = expanseMapper;
    }

    /**
     * Save a expanse.
     *
     * @param expanseDTO the entity to save.
     * @return the persisted entity.
     */
    public ExpanseDTO save(ExpanseDTO expanseDTO) {
        log.debug("Request to save Expanse : {}", expanseDTO);
        Expanse expanse = expanseMapper.toEntity(expanseDTO);
        expanse = expanseRepository.save(expanse);
        return expanseMapper.toDto(expanse);
    }

    /**
     * Get all the expanses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ExpanseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Expanses");
        return expanseRepository.findAll(pageable)
            .map(expanseMapper::toDto);
    }


    /**
     * Get one expanse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExpanseDTO> findOne(Long id) {
        log.debug("Request to get Expanse : {}", id);
        return expanseRepository.findById(id)
            .map(expanseMapper::toDto);
    }

    /**
     * Delete the expanse by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Expanse : {}", id);
        expanseRepository.deleteById(id);
    }
}

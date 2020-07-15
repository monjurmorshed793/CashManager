package org.cash.manager.web.rest;

import org.cash.manager.service.ExpanseService;
import org.cash.manager.web.rest.errors.BadRequestAlertException;
import org.cash.manager.service.dto.ExpanseDTO;
import org.cash.manager.service.dto.ExpanseCriteria;
import org.cash.manager.service.ExpanseQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.cash.manager.domain.Expanse}.
 */
@RestController
@RequestMapping("/api")
public class ExpanseResource {

    private final Logger log = LoggerFactory.getLogger(ExpanseResource.class);

    private static final String ENTITY_NAME = "expanse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExpanseService expanseService;

    private final ExpanseQueryService expanseQueryService;

    public ExpanseResource(ExpanseService expanseService, ExpanseQueryService expanseQueryService) {
        this.expanseService = expanseService;
        this.expanseQueryService = expanseQueryService;
    }

    /**
     * {@code POST  /expanses} : Create a new expanse.
     *
     * @param expanseDTO the expanseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new expanseDTO, or with status {@code 400 (Bad Request)} if the expanse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/expanses")
    public ResponseEntity<ExpanseDTO> createExpanse(@RequestBody ExpanseDTO expanseDTO) throws URISyntaxException {
        log.debug("REST request to save Expanse : {}", expanseDTO);
        if (expanseDTO.getId() != null) {
            throw new BadRequestAlertException("A new expanse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExpanseDTO result = expanseService.save(expanseDTO);
        return ResponseEntity.created(new URI("/api/expanses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /expanses} : Updates an existing expanse.
     *
     * @param expanseDTO the expanseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expanseDTO,
     * or with status {@code 400 (Bad Request)} if the expanseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the expanseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/expanses")
    public ResponseEntity<ExpanseDTO> updateExpanse(@RequestBody ExpanseDTO expanseDTO) throws URISyntaxException {
        log.debug("REST request to update Expanse : {}", expanseDTO);
        if (expanseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExpanseDTO result = expanseService.save(expanseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, expanseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /expanses} : get all the expanses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expanses in body.
     */
    @GetMapping("/expanses")
    public ResponseEntity<List<ExpanseDTO>> getAllExpanses(ExpanseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Expanses by criteria: {}", criteria);
        Page<ExpanseDTO> page = expanseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /expanses/count} : count all the expanses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/expanses/count")
    public ResponseEntity<Long> countExpanses(ExpanseCriteria criteria) {
        log.debug("REST request to count Expanses by criteria: {}", criteria);
        return ResponseEntity.ok().body(expanseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /expanses/:id} : get the "id" expanse.
     *
     * @param id the id of the expanseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the expanseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/expanses/{id}")
    public ResponseEntity<ExpanseDTO> getExpanse(@PathVariable Long id) {
        log.debug("REST request to get Expanse : {}", id);
        Optional<ExpanseDTO> expanseDTO = expanseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(expanseDTO);
    }

    /**
     * {@code DELETE  /expanses/:id} : delete the "id" expanse.
     *
     * @param id the id of the expanseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/expanses/{id}")
    public ResponseEntity<Void> deleteExpanse(@PathVariable Long id) {
        log.debug("REST request to delete Expanse : {}", id);
        expanseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

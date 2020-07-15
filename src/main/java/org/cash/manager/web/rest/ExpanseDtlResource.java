package org.cash.manager.web.rest;

import org.cash.manager.service.ExpanseDtlService;
import org.cash.manager.web.rest.errors.BadRequestAlertException;
import org.cash.manager.service.dto.ExpanseDtlDTO;
import org.cash.manager.service.dto.ExpanseDtlCriteria;
import org.cash.manager.service.ExpanseDtlQueryService;

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
 * REST controller for managing {@link org.cash.manager.domain.ExpanseDtl}.
 */
@RestController
@RequestMapping("/api")
public class ExpanseDtlResource {

    private final Logger log = LoggerFactory.getLogger(ExpanseDtlResource.class);

    private static final String ENTITY_NAME = "expanseDtl";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExpanseDtlService expanseDtlService;

    private final ExpanseDtlQueryService expanseDtlQueryService;

    public ExpanseDtlResource(ExpanseDtlService expanseDtlService, ExpanseDtlQueryService expanseDtlQueryService) {
        this.expanseDtlService = expanseDtlService;
        this.expanseDtlQueryService = expanseDtlQueryService;
    }

    /**
     * {@code POST  /expanse-dtls} : Create a new expanseDtl.
     *
     * @param expanseDtlDTO the expanseDtlDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new expanseDtlDTO, or with status {@code 400 (Bad Request)} if the expanseDtl has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/expanse-dtls")
    public ResponseEntity<ExpanseDtlDTO> createExpanseDtl(@RequestBody ExpanseDtlDTO expanseDtlDTO) throws URISyntaxException {
        log.debug("REST request to save ExpanseDtl : {}", expanseDtlDTO);
        if (expanseDtlDTO.getId() != null) {
            throw new BadRequestAlertException("A new expanseDtl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExpanseDtlDTO result = expanseDtlService.save(expanseDtlDTO);
        return ResponseEntity.created(new URI("/api/expanse-dtls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /expanse-dtls} : Updates an existing expanseDtl.
     *
     * @param expanseDtlDTO the expanseDtlDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expanseDtlDTO,
     * or with status {@code 400 (Bad Request)} if the expanseDtlDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the expanseDtlDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/expanse-dtls")
    public ResponseEntity<ExpanseDtlDTO> updateExpanseDtl(@RequestBody ExpanseDtlDTO expanseDtlDTO) throws URISyntaxException {
        log.debug("REST request to update ExpanseDtl : {}", expanseDtlDTO);
        if (expanseDtlDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExpanseDtlDTO result = expanseDtlService.save(expanseDtlDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, expanseDtlDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /expanse-dtls} : get all the expanseDtls.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expanseDtls in body.
     */
    @GetMapping("/expanse-dtls")
    public ResponseEntity<List<ExpanseDtlDTO>> getAllExpanseDtls(ExpanseDtlCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ExpanseDtls by criteria: {}", criteria);
        Page<ExpanseDtlDTO> page = expanseDtlQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /expanse-dtls/count} : count all the expanseDtls.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/expanse-dtls/count")
    public ResponseEntity<Long> countExpanseDtls(ExpanseDtlCriteria criteria) {
        log.debug("REST request to count ExpanseDtls by criteria: {}", criteria);
        return ResponseEntity.ok().body(expanseDtlQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /expanse-dtls/:id} : get the "id" expanseDtl.
     *
     * @param id the id of the expanseDtlDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the expanseDtlDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/expanse-dtls/{id}")
    public ResponseEntity<ExpanseDtlDTO> getExpanseDtl(@PathVariable Long id) {
        log.debug("REST request to get ExpanseDtl : {}", id);
        Optional<ExpanseDtlDTO> expanseDtlDTO = expanseDtlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(expanseDtlDTO);
    }

    /**
     * {@code DELETE  /expanse-dtls/:id} : delete the "id" expanseDtl.
     *
     * @param id the id of the expanseDtlDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/expanse-dtls/{id}")
    public ResponseEntity<Void> deleteExpanseDtl(@PathVariable Long id) {
        log.debug("REST request to delete ExpanseDtl : {}", id);
        expanseDtlService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

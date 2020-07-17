package org.cash.manager.web.rest.extended;

import io.github.jhipster.web.util.HeaderUtil;
import org.cash.manager.service.ExpanseQueryService;
import org.cash.manager.service.ExpanseService;
import org.cash.manager.service.dto.ExpanseDTO;
import org.cash.manager.service.extended.ExpanseExtendedService;
import org.cash.manager.web.rest.ExpanseResource;
import org.cash.manager.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/extended")
public class ExpanseExtendedResource extends ExpanseResource {
    private final Logger log = LoggerFactory.getLogger(ExpanseExtendedResource.class);

    private static final String ENTITY_NAME = "expanse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExpanseExtendedService expanseService;

    private final ExpanseQueryService expanseQueryService;

    public ExpanseExtendedResource(ExpanseService expanseService, ExpanseQueryService expanseQueryService, ExpanseExtendedService expanseService1, ExpanseQueryService expanseQueryService1) {
        super(expanseService, expanseQueryService);
        this.expanseService = expanseService1;
        this.expanseQueryService = expanseQueryService1;
    }

    @PostMapping("/expanses")
    public ResponseEntity<ExpanseDTO> createExpanse(@Valid @RequestBody ExpanseDTO expanseDTO) throws URISyntaxException {
        log.debug("REST request to save Expanse : {}", expanseDTO);
        if (expanseDTO.getId() != null) {
            throw new BadRequestAlertException("A new expanse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExpanseDTO result = expanseService.save(expanseDTO);
        return ResponseEntity.created(new URI("/api/expanses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/expanses")
    public ResponseEntity<ExpanseDTO> updateExpanse(@Valid @RequestBody ExpanseDTO expanseDTO) throws URISyntaxException {
        log.debug("REST request to update Expanse : {}", expanseDTO);
        if (expanseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExpanseDTO result = expanseService.save(expanseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, expanseDTO.getId().toString()))
            .body(result);
    }
}

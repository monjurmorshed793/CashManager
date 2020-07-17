package org.cash.manager.web.rest.extended;

import io.github.jhipster.web.util.HeaderUtil;
import org.cash.manager.service.DepositQueryService;
import org.cash.manager.service.DepositService;
import org.cash.manager.service.dto.DepositDTO;
import org.cash.manager.service.extended.DepositExtendedService;
import org.cash.manager.web.rest.DepositResource;
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
public class DepositExtendedResource extends DepositResource {
    private final Logger log = LoggerFactory.getLogger(DepositExtendedResource.class);

    private static final String ENTITY_NAME = "deposit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepositExtendedService depositService;

    private final DepositQueryService depositQueryService;

    public DepositExtendedResource(DepositService depositService, DepositQueryService depositQueryService, DepositExtendedService depositService1, DepositQueryService depositQueryService1) {
        super(depositService, depositQueryService);
        this.depositService = depositService1;
        this.depositQueryService = depositQueryService1;
    }
    @PostMapping("/deposits")
    public ResponseEntity<DepositDTO> createDeposit(@Valid @RequestBody DepositDTO depositDTO) throws URISyntaxException {
        log.debug("REST request to save Deposit : {}", depositDTO);
        if (depositDTO.getId() != null) {
            throw new BadRequestAlertException("A new deposit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepositDTO result = depositService.save(depositDTO);
        return ResponseEntity.created(new URI("/api/deposits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/deposits")
    public ResponseEntity<DepositDTO> updateDeposit(@Valid @RequestBody DepositDTO depositDTO) throws URISyntaxException {
        log.debug("REST request to update Deposit : {}", depositDTO);
        if (depositDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DepositDTO result = depositService.save(depositDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, depositDTO.getId().toString()))
            .body(result);
    }
}

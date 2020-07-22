package org.cash.manager.web.rest;

import org.cash.manager.CashManagerApp;
import org.cash.manager.domain.ExpanseDtl;
import org.cash.manager.domain.Item;
import org.cash.manager.domain.Expanse;
import org.cash.manager.repository.ExpanseDtlRepository;
import org.cash.manager.service.ExpanseDtlService;
import org.cash.manager.service.dto.ExpanseDtlDTO;
import org.cash.manager.service.mapper.ExpanseDtlMapper;
import org.cash.manager.service.dto.ExpanseDtlCriteria;
import org.cash.manager.service.ExpanseDtlQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ExpanseDtlResource} REST controller.
 */
@SpringBootTest(classes = CashManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExpanseDtlResourceIT {

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUANTITY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_UNIT_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT = new BigDecimal(1 - 1);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFIED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ExpanseDtlRepository expanseDtlRepository;

    @Autowired
    private ExpanseDtlMapper expanseDtlMapper;

    @Autowired
    private ExpanseDtlService expanseDtlService;

    @Autowired
    private ExpanseDtlQueryService expanseDtlQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExpanseDtlMockMvc;

    private ExpanseDtl expanseDtl;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExpanseDtl createEntity(EntityManager em) {
        ExpanseDtl expanseDtl = new ExpanseDtl()
            .quantity(DEFAULT_QUANTITY)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .amount(DEFAULT_AMOUNT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        // Add required entity
        Item item;
        if (TestUtil.findAll(em, Item.class).isEmpty()) {
            item = ItemResourceIT.createEntity(em);
            em.persist(item);
            em.flush();
        } else {
            item = TestUtil.findAll(em, Item.class).get(0);
        }
        expanseDtl.setItem(item);
        return expanseDtl;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExpanseDtl createUpdatedEntity(EntityManager em) {
        ExpanseDtl expanseDtl = new ExpanseDtl()
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .amount(UPDATED_AMOUNT)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        // Add required entity
        Item item;
        if (TestUtil.findAll(em, Item.class).isEmpty()) {
            item = ItemResourceIT.createUpdatedEntity(em);
            em.persist(item);
            em.flush();
        } else {
            item = TestUtil.findAll(em, Item.class).get(0);
        }
        expanseDtl.setItem(item);
        return expanseDtl;
    }

    @BeforeEach
    public void initTest() {
        expanseDtl = createEntity(em);
    }

    @Test
    @Transactional
    public void createExpanseDtl() throws Exception {
        int databaseSizeBeforeCreate = expanseDtlRepository.findAll().size();
        // Create the ExpanseDtl
        ExpanseDtlDTO expanseDtlDTO = expanseDtlMapper.toDto(expanseDtl);
        restExpanseDtlMockMvc.perform(post("/api/expanse-dtls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expanseDtlDTO)))
            .andExpect(status().isCreated());

        // Validate the ExpanseDtl in the database
        List<ExpanseDtl> expanseDtlList = expanseDtlRepository.findAll();
        assertThat(expanseDtlList).hasSize(databaseSizeBeforeCreate + 1);
        ExpanseDtl testExpanseDtl = expanseDtlList.get(expanseDtlList.size() - 1);
        assertThat(testExpanseDtl.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testExpanseDtl.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testExpanseDtl.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testExpanseDtl.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testExpanseDtl.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testExpanseDtl.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testExpanseDtl.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void createExpanseDtlWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = expanseDtlRepository.findAll().size();

        // Create the ExpanseDtl with an existing ID
        expanseDtl.setId(1L);
        ExpanseDtlDTO expanseDtlDTO = expanseDtlMapper.toDto(expanseDtl);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpanseDtlMockMvc.perform(post("/api/expanse-dtls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expanseDtlDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExpanseDtl in the database
        List<ExpanseDtl> expanseDtlList = expanseDtlRepository.findAll();
        assertThat(expanseDtlList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllExpanseDtls() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList
        restExpanseDtlMockMvc.perform(get("/api/expanse-dtls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expanseDtl.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getExpanseDtl() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get the expanseDtl
        restExpanseDtlMockMvc.perform(get("/api/expanse-dtls/{id}", expanseDtl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(expanseDtl.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }


    @Test
    @Transactional
    public void getExpanseDtlsByIdFiltering() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        Long id = expanseDtl.getId();

        defaultExpanseDtlShouldBeFound("id.equals=" + id);
        defaultExpanseDtlShouldNotBeFound("id.notEquals=" + id);

        defaultExpanseDtlShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultExpanseDtlShouldNotBeFound("id.greaterThan=" + id);

        defaultExpanseDtlShouldBeFound("id.lessThanOrEqual=" + id);
        defaultExpanseDtlShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllExpanseDtlsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where quantity equals to DEFAULT_QUANTITY
        defaultExpanseDtlShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the expanseDtlList where quantity equals to UPDATED_QUANTITY
        defaultExpanseDtlShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where quantity not equals to DEFAULT_QUANTITY
        defaultExpanseDtlShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the expanseDtlList where quantity not equals to UPDATED_QUANTITY
        defaultExpanseDtlShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultExpanseDtlShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the expanseDtlList where quantity equals to UPDATED_QUANTITY
        defaultExpanseDtlShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where quantity is not null
        defaultExpanseDtlShouldBeFound("quantity.specified=true");

        // Get all the expanseDtlList where quantity is null
        defaultExpanseDtlShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultExpanseDtlShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the expanseDtlList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultExpanseDtlShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultExpanseDtlShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the expanseDtlList where quantity is less than or equal to SMALLER_QUANTITY
        defaultExpanseDtlShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where quantity is less than DEFAULT_QUANTITY
        defaultExpanseDtlShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the expanseDtlList where quantity is less than UPDATED_QUANTITY
        defaultExpanseDtlShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where quantity is greater than DEFAULT_QUANTITY
        defaultExpanseDtlShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the expanseDtlList where quantity is greater than SMALLER_QUANTITY
        defaultExpanseDtlShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllExpanseDtlsByUnitPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where unitPrice equals to DEFAULT_UNIT_PRICE
        defaultExpanseDtlShouldBeFound("unitPrice.equals=" + DEFAULT_UNIT_PRICE);

        // Get all the expanseDtlList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultExpanseDtlShouldNotBeFound("unitPrice.equals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByUnitPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where unitPrice not equals to DEFAULT_UNIT_PRICE
        defaultExpanseDtlShouldNotBeFound("unitPrice.notEquals=" + DEFAULT_UNIT_PRICE);

        // Get all the expanseDtlList where unitPrice not equals to UPDATED_UNIT_PRICE
        defaultExpanseDtlShouldBeFound("unitPrice.notEquals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByUnitPriceIsInShouldWork() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where unitPrice in DEFAULT_UNIT_PRICE or UPDATED_UNIT_PRICE
        defaultExpanseDtlShouldBeFound("unitPrice.in=" + DEFAULT_UNIT_PRICE + "," + UPDATED_UNIT_PRICE);

        // Get all the expanseDtlList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultExpanseDtlShouldNotBeFound("unitPrice.in=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByUnitPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where unitPrice is not null
        defaultExpanseDtlShouldBeFound("unitPrice.specified=true");

        // Get all the expanseDtlList where unitPrice is null
        defaultExpanseDtlShouldNotBeFound("unitPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByUnitPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where unitPrice is greater than or equal to DEFAULT_UNIT_PRICE
        defaultExpanseDtlShouldBeFound("unitPrice.greaterThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the expanseDtlList where unitPrice is greater than or equal to UPDATED_UNIT_PRICE
        defaultExpanseDtlShouldNotBeFound("unitPrice.greaterThanOrEqual=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByUnitPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where unitPrice is less than or equal to DEFAULT_UNIT_PRICE
        defaultExpanseDtlShouldBeFound("unitPrice.lessThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the expanseDtlList where unitPrice is less than or equal to SMALLER_UNIT_PRICE
        defaultExpanseDtlShouldNotBeFound("unitPrice.lessThanOrEqual=" + SMALLER_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByUnitPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where unitPrice is less than DEFAULT_UNIT_PRICE
        defaultExpanseDtlShouldNotBeFound("unitPrice.lessThan=" + DEFAULT_UNIT_PRICE);

        // Get all the expanseDtlList where unitPrice is less than UPDATED_UNIT_PRICE
        defaultExpanseDtlShouldBeFound("unitPrice.lessThan=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByUnitPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where unitPrice is greater than DEFAULT_UNIT_PRICE
        defaultExpanseDtlShouldNotBeFound("unitPrice.greaterThan=" + DEFAULT_UNIT_PRICE);

        // Get all the expanseDtlList where unitPrice is greater than SMALLER_UNIT_PRICE
        defaultExpanseDtlShouldBeFound("unitPrice.greaterThan=" + SMALLER_UNIT_PRICE);
    }


    @Test
    @Transactional
    public void getAllExpanseDtlsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where amount equals to DEFAULT_AMOUNT
        defaultExpanseDtlShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the expanseDtlList where amount equals to UPDATED_AMOUNT
        defaultExpanseDtlShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where amount not equals to DEFAULT_AMOUNT
        defaultExpanseDtlShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the expanseDtlList where amount not equals to UPDATED_AMOUNT
        defaultExpanseDtlShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultExpanseDtlShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the expanseDtlList where amount equals to UPDATED_AMOUNT
        defaultExpanseDtlShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where amount is not null
        defaultExpanseDtlShouldBeFound("amount.specified=true");

        // Get all the expanseDtlList where amount is null
        defaultExpanseDtlShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultExpanseDtlShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the expanseDtlList where amount is greater than or equal to UPDATED_AMOUNT
        defaultExpanseDtlShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where amount is less than or equal to DEFAULT_AMOUNT
        defaultExpanseDtlShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the expanseDtlList where amount is less than or equal to SMALLER_AMOUNT
        defaultExpanseDtlShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where amount is less than DEFAULT_AMOUNT
        defaultExpanseDtlShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the expanseDtlList where amount is less than UPDATED_AMOUNT
        defaultExpanseDtlShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where amount is greater than DEFAULT_AMOUNT
        defaultExpanseDtlShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the expanseDtlList where amount is greater than SMALLER_AMOUNT
        defaultExpanseDtlShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllExpanseDtlsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where createdBy equals to DEFAULT_CREATED_BY
        defaultExpanseDtlShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the expanseDtlList where createdBy equals to UPDATED_CREATED_BY
        defaultExpanseDtlShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where createdBy not equals to DEFAULT_CREATED_BY
        defaultExpanseDtlShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the expanseDtlList where createdBy not equals to UPDATED_CREATED_BY
        defaultExpanseDtlShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultExpanseDtlShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the expanseDtlList where createdBy equals to UPDATED_CREATED_BY
        defaultExpanseDtlShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where createdBy is not null
        defaultExpanseDtlShouldBeFound("createdBy.specified=true");

        // Get all the expanseDtlList where createdBy is null
        defaultExpanseDtlShouldNotBeFound("createdBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllExpanseDtlsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where createdBy contains DEFAULT_CREATED_BY
        defaultExpanseDtlShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the expanseDtlList where createdBy contains UPDATED_CREATED_BY
        defaultExpanseDtlShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where createdBy does not contain DEFAULT_CREATED_BY
        defaultExpanseDtlShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the expanseDtlList where createdBy does not contain UPDATED_CREATED_BY
        defaultExpanseDtlShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllExpanseDtlsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where createdOn equals to DEFAULT_CREATED_ON
        defaultExpanseDtlShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the expanseDtlList where createdOn equals to UPDATED_CREATED_ON
        defaultExpanseDtlShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByCreatedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where createdOn not equals to DEFAULT_CREATED_ON
        defaultExpanseDtlShouldNotBeFound("createdOn.notEquals=" + DEFAULT_CREATED_ON);

        // Get all the expanseDtlList where createdOn not equals to UPDATED_CREATED_ON
        defaultExpanseDtlShouldBeFound("createdOn.notEquals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultExpanseDtlShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the expanseDtlList where createdOn equals to UPDATED_CREATED_ON
        defaultExpanseDtlShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where createdOn is not null
        defaultExpanseDtlShouldBeFound("createdOn.specified=true");

        // Get all the expanseDtlList where createdOn is null
        defaultExpanseDtlShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultExpanseDtlShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the expanseDtlList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultExpanseDtlShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where modifiedBy not equals to DEFAULT_MODIFIED_BY
        defaultExpanseDtlShouldNotBeFound("modifiedBy.notEquals=" + DEFAULT_MODIFIED_BY);

        // Get all the expanseDtlList where modifiedBy not equals to UPDATED_MODIFIED_BY
        defaultExpanseDtlShouldBeFound("modifiedBy.notEquals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultExpanseDtlShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the expanseDtlList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultExpanseDtlShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where modifiedBy is not null
        defaultExpanseDtlShouldBeFound("modifiedBy.specified=true");

        // Get all the expanseDtlList where modifiedBy is null
        defaultExpanseDtlShouldNotBeFound("modifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllExpanseDtlsByModifiedByContainsSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where modifiedBy contains DEFAULT_MODIFIED_BY
        defaultExpanseDtlShouldBeFound("modifiedBy.contains=" + DEFAULT_MODIFIED_BY);

        // Get all the expanseDtlList where modifiedBy contains UPDATED_MODIFIED_BY
        defaultExpanseDtlShouldNotBeFound("modifiedBy.contains=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where modifiedBy does not contain DEFAULT_MODIFIED_BY
        defaultExpanseDtlShouldNotBeFound("modifiedBy.doesNotContain=" + DEFAULT_MODIFIED_BY);

        // Get all the expanseDtlList where modifiedBy does not contain UPDATED_MODIFIED_BY
        defaultExpanseDtlShouldBeFound("modifiedBy.doesNotContain=" + UPDATED_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllExpanseDtlsByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultExpanseDtlShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the expanseDtlList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultExpanseDtlShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByModifiedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where modifiedOn not equals to DEFAULT_MODIFIED_ON
        defaultExpanseDtlShouldNotBeFound("modifiedOn.notEquals=" + DEFAULT_MODIFIED_ON);

        // Get all the expanseDtlList where modifiedOn not equals to UPDATED_MODIFIED_ON
        defaultExpanseDtlShouldBeFound("modifiedOn.notEquals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultExpanseDtlShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the expanseDtlList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultExpanseDtlShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        // Get all the expanseDtlList where modifiedOn is not null
        defaultExpanseDtlShouldBeFound("modifiedOn.specified=true");

        // Get all the expanseDtlList where modifiedOn is null
        defaultExpanseDtlShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpanseDtlsByItemIsEqualToSomething() throws Exception {
        // Get already existing entity
        Item item = expanseDtl.getItem();
        expanseDtlRepository.saveAndFlush(expanseDtl);
        Long itemId = item.getId();

        // Get all the expanseDtlList where item equals to itemId
        defaultExpanseDtlShouldBeFound("itemId.equals=" + itemId);

        // Get all the expanseDtlList where item equals to itemId + 1
        defaultExpanseDtlShouldNotBeFound("itemId.equals=" + (itemId + 1));
    }


    @Test
    @Transactional
    public void getAllExpanseDtlsByExpanseIsEqualToSomething() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);
        Expanse expanse = ExpanseResourceIT.createEntity(em);
        em.persist(expanse);
        em.flush();
        expanseDtl.setExpanse(expanse);
        expanseDtlRepository.saveAndFlush(expanseDtl);
        Long expanseId = expanse.getId();

        // Get all the expanseDtlList where expanse equals to expanseId
        defaultExpanseDtlShouldBeFound("expanseId.equals=" + expanseId);

        // Get all the expanseDtlList where expanse equals to expanseId + 1
        defaultExpanseDtlShouldNotBeFound("expanseId.equals=" + (expanseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultExpanseDtlShouldBeFound(String filter) throws Exception {
        restExpanseDtlMockMvc.perform(get("/api/expanse-dtls?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expanseDtl.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restExpanseDtlMockMvc.perform(get("/api/expanse-dtls/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultExpanseDtlShouldNotBeFound(String filter) throws Exception {
        restExpanseDtlMockMvc.perform(get("/api/expanse-dtls?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExpanseDtlMockMvc.perform(get("/api/expanse-dtls/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingExpanseDtl() throws Exception {
        // Get the expanseDtl
        restExpanseDtlMockMvc.perform(get("/api/expanse-dtls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExpanseDtl() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        int databaseSizeBeforeUpdate = expanseDtlRepository.findAll().size();

        // Update the expanseDtl
        ExpanseDtl updatedExpanseDtl = expanseDtlRepository.findById(expanseDtl.getId()).get();
        // Disconnect from session so that the updates on updatedExpanseDtl are not directly saved in db
        em.detach(updatedExpanseDtl);
        updatedExpanseDtl
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .amount(UPDATED_AMOUNT)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        ExpanseDtlDTO expanseDtlDTO = expanseDtlMapper.toDto(updatedExpanseDtl);

        restExpanseDtlMockMvc.perform(put("/api/expanse-dtls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expanseDtlDTO)))
            .andExpect(status().isOk());

        // Validate the ExpanseDtl in the database
        List<ExpanseDtl> expanseDtlList = expanseDtlRepository.findAll();
        assertThat(expanseDtlList).hasSize(databaseSizeBeforeUpdate);
        ExpanseDtl testExpanseDtl = expanseDtlList.get(expanseDtlList.size() - 1);
        assertThat(testExpanseDtl.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testExpanseDtl.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testExpanseDtl.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testExpanseDtl.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testExpanseDtl.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testExpanseDtl.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testExpanseDtl.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingExpanseDtl() throws Exception {
        int databaseSizeBeforeUpdate = expanseDtlRepository.findAll().size();

        // Create the ExpanseDtl
        ExpanseDtlDTO expanseDtlDTO = expanseDtlMapper.toDto(expanseDtl);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpanseDtlMockMvc.perform(put("/api/expanse-dtls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expanseDtlDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExpanseDtl in the database
        List<ExpanseDtl> expanseDtlList = expanseDtlRepository.findAll();
        assertThat(expanseDtlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExpanseDtl() throws Exception {
        // Initialize the database
        expanseDtlRepository.saveAndFlush(expanseDtl);

        int databaseSizeBeforeDelete = expanseDtlRepository.findAll().size();

        // Delete the expanseDtl
        restExpanseDtlMockMvc.perform(delete("/api/expanse-dtls/{id}", expanseDtl.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExpanseDtl> expanseDtlList = expanseDtlRepository.findAll();
        assertThat(expanseDtlList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

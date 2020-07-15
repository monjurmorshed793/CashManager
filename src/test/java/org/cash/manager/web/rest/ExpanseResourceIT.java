package org.cash.manager.web.rest;

import org.cash.manager.CashManagerApp;
import org.cash.manager.domain.Expanse;
import org.cash.manager.domain.PayTo;
import org.cash.manager.repository.ExpanseRepository;
import org.cash.manager.service.ExpanseService;
import org.cash.manager.service.dto.ExpanseDTO;
import org.cash.manager.service.mapper.ExpanseMapper;
import org.cash.manager.service.dto.ExpanseCriteria;
import org.cash.manager.service.ExpanseQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.cash.manager.domain.enumeration.MonthType;
/**
 * Integration tests for the {@link ExpanseResource} REST controller.
 */
@SpringBootTest(classes = CashManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExpanseResourceIT {

    private static final String DEFAULT_LOGIN_ID = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_VOUCHER_NO = 1;
    private static final Integer UPDATED_VOUCHER_NO = 2;
    private static final Integer SMALLER_VOUCHER_NO = 1 - 1;

    private static final LocalDate DEFAULT_VOUCHER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VOUCHER_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VOUCHER_DATE = LocalDate.ofEpochDay(-1L);

    private static final MonthType DEFAULT_MONTH = MonthType.JANUARY;
    private static final MonthType UPDATED_MONTH = MonthType.FEBRUARY;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_POSTED = false;
    private static final Boolean UPDATED_IS_POSTED = true;

    private static final Instant DEFAULT_POST_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_POST_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFIED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ExpanseRepository expanseRepository;

    @Autowired
    private ExpanseMapper expanseMapper;

    @Autowired
    private ExpanseService expanseService;

    @Autowired
    private ExpanseQueryService expanseQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExpanseMockMvc;

    private Expanse expanse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Expanse createEntity(EntityManager em) {
        Expanse expanse = new Expanse()
            .loginId(DEFAULT_LOGIN_ID)
            .voucherNo(DEFAULT_VOUCHER_NO)
            .voucherDate(DEFAULT_VOUCHER_DATE)
            .month(DEFAULT_MONTH)
            .notes(DEFAULT_NOTES)
            .isPosted(DEFAULT_IS_POSTED)
            .postDate(DEFAULT_POST_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        // Add required entity
        PayTo payTo;
        if (TestUtil.findAll(em, PayTo.class).isEmpty()) {
            payTo = PayToResourceIT.createEntity(em);
            em.persist(payTo);
            em.flush();
        } else {
            payTo = TestUtil.findAll(em, PayTo.class).get(0);
        }
        expanse.setPayTo(payTo);
        return expanse;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Expanse createUpdatedEntity(EntityManager em) {
        Expanse expanse = new Expanse()
            .loginId(UPDATED_LOGIN_ID)
            .voucherNo(UPDATED_VOUCHER_NO)
            .voucherDate(UPDATED_VOUCHER_DATE)
            .month(UPDATED_MONTH)
            .notes(UPDATED_NOTES)
            .isPosted(UPDATED_IS_POSTED)
            .postDate(UPDATED_POST_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        // Add required entity
        PayTo payTo;
        if (TestUtil.findAll(em, PayTo.class).isEmpty()) {
            payTo = PayToResourceIT.createUpdatedEntity(em);
            em.persist(payTo);
            em.flush();
        } else {
            payTo = TestUtil.findAll(em, PayTo.class).get(0);
        }
        expanse.setPayTo(payTo);
        return expanse;
    }

    @BeforeEach
    public void initTest() {
        expanse = createEntity(em);
    }

    @Test
    @Transactional
    public void createExpanse() throws Exception {
        int databaseSizeBeforeCreate = expanseRepository.findAll().size();
        // Create the Expanse
        ExpanseDTO expanseDTO = expanseMapper.toDto(expanse);
        restExpanseMockMvc.perform(post("/api/expanses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expanseDTO)))
            .andExpect(status().isCreated());

        // Validate the Expanse in the database
        List<Expanse> expanseList = expanseRepository.findAll();
        assertThat(expanseList).hasSize(databaseSizeBeforeCreate + 1);
        Expanse testExpanse = expanseList.get(expanseList.size() - 1);
        assertThat(testExpanse.getLoginId()).isEqualTo(DEFAULT_LOGIN_ID);
        assertThat(testExpanse.getVoucherNo()).isEqualTo(DEFAULT_VOUCHER_NO);
        assertThat(testExpanse.getVoucherDate()).isEqualTo(DEFAULT_VOUCHER_DATE);
        assertThat(testExpanse.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testExpanse.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testExpanse.isIsPosted()).isEqualTo(DEFAULT_IS_POSTED);
        assertThat(testExpanse.getPostDate()).isEqualTo(DEFAULT_POST_DATE);
        assertThat(testExpanse.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testExpanse.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testExpanse.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testExpanse.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void createExpanseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = expanseRepository.findAll().size();

        // Create the Expanse with an existing ID
        expanse.setId(1L);
        ExpanseDTO expanseDTO = expanseMapper.toDto(expanse);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpanseMockMvc.perform(post("/api/expanses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expanseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Expanse in the database
        List<Expanse> expanseList = expanseRepository.findAll();
        assertThat(expanseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLoginIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = expanseRepository.findAll().size();
        // set the field null
        expanse.setLoginId(null);

        // Create the Expanse, which fails.
        ExpanseDTO expanseDTO = expanseMapper.toDto(expanse);


        restExpanseMockMvc.perform(post("/api/expanses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expanseDTO)))
            .andExpect(status().isBadRequest());

        List<Expanse> expanseList = expanseRepository.findAll();
        assertThat(expanseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVoucherDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = expanseRepository.findAll().size();
        // set the field null
        expanse.setVoucherDate(null);

        // Create the Expanse, which fails.
        ExpanseDTO expanseDTO = expanseMapper.toDto(expanse);


        restExpanseMockMvc.perform(post("/api/expanses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expanseDTO)))
            .andExpect(status().isBadRequest());

        List<Expanse> expanseList = expanseRepository.findAll();
        assertThat(expanseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = expanseRepository.findAll().size();
        // set the field null
        expanse.setMonth(null);

        // Create the Expanse, which fails.
        ExpanseDTO expanseDTO = expanseMapper.toDto(expanse);


        restExpanseMockMvc.perform(post("/api/expanses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expanseDTO)))
            .andExpect(status().isBadRequest());

        List<Expanse> expanseList = expanseRepository.findAll();
        assertThat(expanseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExpanses() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList
        restExpanseMockMvc.perform(get("/api/expanses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expanse.getId().intValue())))
            .andExpect(jsonPath("$.[*].loginId").value(hasItem(DEFAULT_LOGIN_ID)))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].voucherDate").value(hasItem(DEFAULT_VOUCHER_DATE.toString())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].isPosted").value(hasItem(DEFAULT_IS_POSTED.booleanValue())))
            .andExpect(jsonPath("$.[*].postDate").value(hasItem(DEFAULT_POST_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getExpanse() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get the expanse
        restExpanseMockMvc.perform(get("/api/expanses/{id}", expanse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(expanse.getId().intValue()))
            .andExpect(jsonPath("$.loginId").value(DEFAULT_LOGIN_ID))
            .andExpect(jsonPath("$.voucherNo").value(DEFAULT_VOUCHER_NO))
            .andExpect(jsonPath("$.voucherDate").value(DEFAULT_VOUCHER_DATE.toString()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.isPosted").value(DEFAULT_IS_POSTED.booleanValue()))
            .andExpect(jsonPath("$.postDate").value(DEFAULT_POST_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }


    @Test
    @Transactional
    public void getExpansesByIdFiltering() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        Long id = expanse.getId();

        defaultExpanseShouldBeFound("id.equals=" + id);
        defaultExpanseShouldNotBeFound("id.notEquals=" + id);

        defaultExpanseShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultExpanseShouldNotBeFound("id.greaterThan=" + id);

        defaultExpanseShouldBeFound("id.lessThanOrEqual=" + id);
        defaultExpanseShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllExpansesByLoginIdIsEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where loginId equals to DEFAULT_LOGIN_ID
        defaultExpanseShouldBeFound("loginId.equals=" + DEFAULT_LOGIN_ID);

        // Get all the expanseList where loginId equals to UPDATED_LOGIN_ID
        defaultExpanseShouldNotBeFound("loginId.equals=" + UPDATED_LOGIN_ID);
    }

    @Test
    @Transactional
    public void getAllExpansesByLoginIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where loginId not equals to DEFAULT_LOGIN_ID
        defaultExpanseShouldNotBeFound("loginId.notEquals=" + DEFAULT_LOGIN_ID);

        // Get all the expanseList where loginId not equals to UPDATED_LOGIN_ID
        defaultExpanseShouldBeFound("loginId.notEquals=" + UPDATED_LOGIN_ID);
    }

    @Test
    @Transactional
    public void getAllExpansesByLoginIdIsInShouldWork() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where loginId in DEFAULT_LOGIN_ID or UPDATED_LOGIN_ID
        defaultExpanseShouldBeFound("loginId.in=" + DEFAULT_LOGIN_ID + "," + UPDATED_LOGIN_ID);

        // Get all the expanseList where loginId equals to UPDATED_LOGIN_ID
        defaultExpanseShouldNotBeFound("loginId.in=" + UPDATED_LOGIN_ID);
    }

    @Test
    @Transactional
    public void getAllExpansesByLoginIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where loginId is not null
        defaultExpanseShouldBeFound("loginId.specified=true");

        // Get all the expanseList where loginId is null
        defaultExpanseShouldNotBeFound("loginId.specified=false");
    }
                @Test
    @Transactional
    public void getAllExpansesByLoginIdContainsSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where loginId contains DEFAULT_LOGIN_ID
        defaultExpanseShouldBeFound("loginId.contains=" + DEFAULT_LOGIN_ID);

        // Get all the expanseList where loginId contains UPDATED_LOGIN_ID
        defaultExpanseShouldNotBeFound("loginId.contains=" + UPDATED_LOGIN_ID);
    }

    @Test
    @Transactional
    public void getAllExpansesByLoginIdNotContainsSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where loginId does not contain DEFAULT_LOGIN_ID
        defaultExpanseShouldNotBeFound("loginId.doesNotContain=" + DEFAULT_LOGIN_ID);

        // Get all the expanseList where loginId does not contain UPDATED_LOGIN_ID
        defaultExpanseShouldBeFound("loginId.doesNotContain=" + UPDATED_LOGIN_ID);
    }


    @Test
    @Transactional
    public void getAllExpansesByVoucherNoIsEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where voucherNo equals to DEFAULT_VOUCHER_NO
        defaultExpanseShouldBeFound("voucherNo.equals=" + DEFAULT_VOUCHER_NO);

        // Get all the expanseList where voucherNo equals to UPDATED_VOUCHER_NO
        defaultExpanseShouldNotBeFound("voucherNo.equals=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllExpansesByVoucherNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where voucherNo not equals to DEFAULT_VOUCHER_NO
        defaultExpanseShouldNotBeFound("voucherNo.notEquals=" + DEFAULT_VOUCHER_NO);

        // Get all the expanseList where voucherNo not equals to UPDATED_VOUCHER_NO
        defaultExpanseShouldBeFound("voucherNo.notEquals=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllExpansesByVoucherNoIsInShouldWork() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where voucherNo in DEFAULT_VOUCHER_NO or UPDATED_VOUCHER_NO
        defaultExpanseShouldBeFound("voucherNo.in=" + DEFAULT_VOUCHER_NO + "," + UPDATED_VOUCHER_NO);

        // Get all the expanseList where voucherNo equals to UPDATED_VOUCHER_NO
        defaultExpanseShouldNotBeFound("voucherNo.in=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllExpansesByVoucherNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where voucherNo is not null
        defaultExpanseShouldBeFound("voucherNo.specified=true");

        // Get all the expanseList where voucherNo is null
        defaultExpanseShouldNotBeFound("voucherNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpansesByVoucherNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where voucherNo is greater than or equal to DEFAULT_VOUCHER_NO
        defaultExpanseShouldBeFound("voucherNo.greaterThanOrEqual=" + DEFAULT_VOUCHER_NO);

        // Get all the expanseList where voucherNo is greater than or equal to UPDATED_VOUCHER_NO
        defaultExpanseShouldNotBeFound("voucherNo.greaterThanOrEqual=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllExpansesByVoucherNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where voucherNo is less than or equal to DEFAULT_VOUCHER_NO
        defaultExpanseShouldBeFound("voucherNo.lessThanOrEqual=" + DEFAULT_VOUCHER_NO);

        // Get all the expanseList where voucherNo is less than or equal to SMALLER_VOUCHER_NO
        defaultExpanseShouldNotBeFound("voucherNo.lessThanOrEqual=" + SMALLER_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllExpansesByVoucherNoIsLessThanSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where voucherNo is less than DEFAULT_VOUCHER_NO
        defaultExpanseShouldNotBeFound("voucherNo.lessThan=" + DEFAULT_VOUCHER_NO);

        // Get all the expanseList where voucherNo is less than UPDATED_VOUCHER_NO
        defaultExpanseShouldBeFound("voucherNo.lessThan=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllExpansesByVoucherNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where voucherNo is greater than DEFAULT_VOUCHER_NO
        defaultExpanseShouldNotBeFound("voucherNo.greaterThan=" + DEFAULT_VOUCHER_NO);

        // Get all the expanseList where voucherNo is greater than SMALLER_VOUCHER_NO
        defaultExpanseShouldBeFound("voucherNo.greaterThan=" + SMALLER_VOUCHER_NO);
    }


    @Test
    @Transactional
    public void getAllExpansesByVoucherDateIsEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where voucherDate equals to DEFAULT_VOUCHER_DATE
        defaultExpanseShouldBeFound("voucherDate.equals=" + DEFAULT_VOUCHER_DATE);

        // Get all the expanseList where voucherDate equals to UPDATED_VOUCHER_DATE
        defaultExpanseShouldNotBeFound("voucherDate.equals=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    public void getAllExpansesByVoucherDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where voucherDate not equals to DEFAULT_VOUCHER_DATE
        defaultExpanseShouldNotBeFound("voucherDate.notEquals=" + DEFAULT_VOUCHER_DATE);

        // Get all the expanseList where voucherDate not equals to UPDATED_VOUCHER_DATE
        defaultExpanseShouldBeFound("voucherDate.notEquals=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    public void getAllExpansesByVoucherDateIsInShouldWork() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where voucherDate in DEFAULT_VOUCHER_DATE or UPDATED_VOUCHER_DATE
        defaultExpanseShouldBeFound("voucherDate.in=" + DEFAULT_VOUCHER_DATE + "," + UPDATED_VOUCHER_DATE);

        // Get all the expanseList where voucherDate equals to UPDATED_VOUCHER_DATE
        defaultExpanseShouldNotBeFound("voucherDate.in=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    public void getAllExpansesByVoucherDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where voucherDate is not null
        defaultExpanseShouldBeFound("voucherDate.specified=true");

        // Get all the expanseList where voucherDate is null
        defaultExpanseShouldNotBeFound("voucherDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpansesByVoucherDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where voucherDate is greater than or equal to DEFAULT_VOUCHER_DATE
        defaultExpanseShouldBeFound("voucherDate.greaterThanOrEqual=" + DEFAULT_VOUCHER_DATE);

        // Get all the expanseList where voucherDate is greater than or equal to UPDATED_VOUCHER_DATE
        defaultExpanseShouldNotBeFound("voucherDate.greaterThanOrEqual=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    public void getAllExpansesByVoucherDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where voucherDate is less than or equal to DEFAULT_VOUCHER_DATE
        defaultExpanseShouldBeFound("voucherDate.lessThanOrEqual=" + DEFAULT_VOUCHER_DATE);

        // Get all the expanseList where voucherDate is less than or equal to SMALLER_VOUCHER_DATE
        defaultExpanseShouldNotBeFound("voucherDate.lessThanOrEqual=" + SMALLER_VOUCHER_DATE);
    }

    @Test
    @Transactional
    public void getAllExpansesByVoucherDateIsLessThanSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where voucherDate is less than DEFAULT_VOUCHER_DATE
        defaultExpanseShouldNotBeFound("voucherDate.lessThan=" + DEFAULT_VOUCHER_DATE);

        // Get all the expanseList where voucherDate is less than UPDATED_VOUCHER_DATE
        defaultExpanseShouldBeFound("voucherDate.lessThan=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    public void getAllExpansesByVoucherDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where voucherDate is greater than DEFAULT_VOUCHER_DATE
        defaultExpanseShouldNotBeFound("voucherDate.greaterThan=" + DEFAULT_VOUCHER_DATE);

        // Get all the expanseList where voucherDate is greater than SMALLER_VOUCHER_DATE
        defaultExpanseShouldBeFound("voucherDate.greaterThan=" + SMALLER_VOUCHER_DATE);
    }


    @Test
    @Transactional
    public void getAllExpansesByMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where month equals to DEFAULT_MONTH
        defaultExpanseShouldBeFound("month.equals=" + DEFAULT_MONTH);

        // Get all the expanseList where month equals to UPDATED_MONTH
        defaultExpanseShouldNotBeFound("month.equals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllExpansesByMonthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where month not equals to DEFAULT_MONTH
        defaultExpanseShouldNotBeFound("month.notEquals=" + DEFAULT_MONTH);

        // Get all the expanseList where month not equals to UPDATED_MONTH
        defaultExpanseShouldBeFound("month.notEquals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllExpansesByMonthIsInShouldWork() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where month in DEFAULT_MONTH or UPDATED_MONTH
        defaultExpanseShouldBeFound("month.in=" + DEFAULT_MONTH + "," + UPDATED_MONTH);

        // Get all the expanseList where month equals to UPDATED_MONTH
        defaultExpanseShouldNotBeFound("month.in=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllExpansesByMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where month is not null
        defaultExpanseShouldBeFound("month.specified=true");

        // Get all the expanseList where month is null
        defaultExpanseShouldNotBeFound("month.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpansesByIsPostedIsEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where isPosted equals to DEFAULT_IS_POSTED
        defaultExpanseShouldBeFound("isPosted.equals=" + DEFAULT_IS_POSTED);

        // Get all the expanseList where isPosted equals to UPDATED_IS_POSTED
        defaultExpanseShouldNotBeFound("isPosted.equals=" + UPDATED_IS_POSTED);
    }

    @Test
    @Transactional
    public void getAllExpansesByIsPostedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where isPosted not equals to DEFAULT_IS_POSTED
        defaultExpanseShouldNotBeFound("isPosted.notEquals=" + DEFAULT_IS_POSTED);

        // Get all the expanseList where isPosted not equals to UPDATED_IS_POSTED
        defaultExpanseShouldBeFound("isPosted.notEquals=" + UPDATED_IS_POSTED);
    }

    @Test
    @Transactional
    public void getAllExpansesByIsPostedIsInShouldWork() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where isPosted in DEFAULT_IS_POSTED or UPDATED_IS_POSTED
        defaultExpanseShouldBeFound("isPosted.in=" + DEFAULT_IS_POSTED + "," + UPDATED_IS_POSTED);

        // Get all the expanseList where isPosted equals to UPDATED_IS_POSTED
        defaultExpanseShouldNotBeFound("isPosted.in=" + UPDATED_IS_POSTED);
    }

    @Test
    @Transactional
    public void getAllExpansesByIsPostedIsNullOrNotNull() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where isPosted is not null
        defaultExpanseShouldBeFound("isPosted.specified=true");

        // Get all the expanseList where isPosted is null
        defaultExpanseShouldNotBeFound("isPosted.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpansesByPostDateIsEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where postDate equals to DEFAULT_POST_DATE
        defaultExpanseShouldBeFound("postDate.equals=" + DEFAULT_POST_DATE);

        // Get all the expanseList where postDate equals to UPDATED_POST_DATE
        defaultExpanseShouldNotBeFound("postDate.equals=" + UPDATED_POST_DATE);
    }

    @Test
    @Transactional
    public void getAllExpansesByPostDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where postDate not equals to DEFAULT_POST_DATE
        defaultExpanseShouldNotBeFound("postDate.notEquals=" + DEFAULT_POST_DATE);

        // Get all the expanseList where postDate not equals to UPDATED_POST_DATE
        defaultExpanseShouldBeFound("postDate.notEquals=" + UPDATED_POST_DATE);
    }

    @Test
    @Transactional
    public void getAllExpansesByPostDateIsInShouldWork() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where postDate in DEFAULT_POST_DATE or UPDATED_POST_DATE
        defaultExpanseShouldBeFound("postDate.in=" + DEFAULT_POST_DATE + "," + UPDATED_POST_DATE);

        // Get all the expanseList where postDate equals to UPDATED_POST_DATE
        defaultExpanseShouldNotBeFound("postDate.in=" + UPDATED_POST_DATE);
    }

    @Test
    @Transactional
    public void getAllExpansesByPostDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where postDate is not null
        defaultExpanseShouldBeFound("postDate.specified=true");

        // Get all the expanseList where postDate is null
        defaultExpanseShouldNotBeFound("postDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpansesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where createdBy equals to DEFAULT_CREATED_BY
        defaultExpanseShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the expanseList where createdBy equals to UPDATED_CREATED_BY
        defaultExpanseShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllExpansesByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where createdBy not equals to DEFAULT_CREATED_BY
        defaultExpanseShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the expanseList where createdBy not equals to UPDATED_CREATED_BY
        defaultExpanseShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllExpansesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultExpanseShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the expanseList where createdBy equals to UPDATED_CREATED_BY
        defaultExpanseShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllExpansesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where createdBy is not null
        defaultExpanseShouldBeFound("createdBy.specified=true");

        // Get all the expanseList where createdBy is null
        defaultExpanseShouldNotBeFound("createdBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllExpansesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where createdBy contains DEFAULT_CREATED_BY
        defaultExpanseShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the expanseList where createdBy contains UPDATED_CREATED_BY
        defaultExpanseShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllExpansesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where createdBy does not contain DEFAULT_CREATED_BY
        defaultExpanseShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the expanseList where createdBy does not contain UPDATED_CREATED_BY
        defaultExpanseShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllExpansesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where createdOn equals to DEFAULT_CREATED_ON
        defaultExpanseShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the expanseList where createdOn equals to UPDATED_CREATED_ON
        defaultExpanseShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllExpansesByCreatedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where createdOn not equals to DEFAULT_CREATED_ON
        defaultExpanseShouldNotBeFound("createdOn.notEquals=" + DEFAULT_CREATED_ON);

        // Get all the expanseList where createdOn not equals to UPDATED_CREATED_ON
        defaultExpanseShouldBeFound("createdOn.notEquals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllExpansesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultExpanseShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the expanseList where createdOn equals to UPDATED_CREATED_ON
        defaultExpanseShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllExpansesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where createdOn is not null
        defaultExpanseShouldBeFound("createdOn.specified=true");

        // Get all the expanseList where createdOn is null
        defaultExpanseShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpansesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultExpanseShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the expanseList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultExpanseShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllExpansesByModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where modifiedBy not equals to DEFAULT_MODIFIED_BY
        defaultExpanseShouldNotBeFound("modifiedBy.notEquals=" + DEFAULT_MODIFIED_BY);

        // Get all the expanseList where modifiedBy not equals to UPDATED_MODIFIED_BY
        defaultExpanseShouldBeFound("modifiedBy.notEquals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllExpansesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultExpanseShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the expanseList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultExpanseShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllExpansesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where modifiedBy is not null
        defaultExpanseShouldBeFound("modifiedBy.specified=true");

        // Get all the expanseList where modifiedBy is null
        defaultExpanseShouldNotBeFound("modifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllExpansesByModifiedByContainsSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where modifiedBy contains DEFAULT_MODIFIED_BY
        defaultExpanseShouldBeFound("modifiedBy.contains=" + DEFAULT_MODIFIED_BY);

        // Get all the expanseList where modifiedBy contains UPDATED_MODIFIED_BY
        defaultExpanseShouldNotBeFound("modifiedBy.contains=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllExpansesByModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where modifiedBy does not contain DEFAULT_MODIFIED_BY
        defaultExpanseShouldNotBeFound("modifiedBy.doesNotContain=" + DEFAULT_MODIFIED_BY);

        // Get all the expanseList where modifiedBy does not contain UPDATED_MODIFIED_BY
        defaultExpanseShouldBeFound("modifiedBy.doesNotContain=" + UPDATED_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllExpansesByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultExpanseShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the expanseList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultExpanseShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllExpansesByModifiedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where modifiedOn not equals to DEFAULT_MODIFIED_ON
        defaultExpanseShouldNotBeFound("modifiedOn.notEquals=" + DEFAULT_MODIFIED_ON);

        // Get all the expanseList where modifiedOn not equals to UPDATED_MODIFIED_ON
        defaultExpanseShouldBeFound("modifiedOn.notEquals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllExpansesByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultExpanseShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the expanseList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultExpanseShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllExpansesByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        // Get all the expanseList where modifiedOn is not null
        defaultExpanseShouldBeFound("modifiedOn.specified=true");

        // Get all the expanseList where modifiedOn is null
        defaultExpanseShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpansesByPayToIsEqualToSomething() throws Exception {
        // Get already existing entity
        PayTo payTo = expanse.getPayTo();
        expanseRepository.saveAndFlush(expanse);
        Long payToId = payTo.getId();

        // Get all the expanseList where payTo equals to payToId
        defaultExpanseShouldBeFound("payToId.equals=" + payToId);

        // Get all the expanseList where payTo equals to payToId + 1
        defaultExpanseShouldNotBeFound("payToId.equals=" + (payToId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultExpanseShouldBeFound(String filter) throws Exception {
        restExpanseMockMvc.perform(get("/api/expanses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expanse.getId().intValue())))
            .andExpect(jsonPath("$.[*].loginId").value(hasItem(DEFAULT_LOGIN_ID)))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].voucherDate").value(hasItem(DEFAULT_VOUCHER_DATE.toString())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].isPosted").value(hasItem(DEFAULT_IS_POSTED.booleanValue())))
            .andExpect(jsonPath("$.[*].postDate").value(hasItem(DEFAULT_POST_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restExpanseMockMvc.perform(get("/api/expanses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultExpanseShouldNotBeFound(String filter) throws Exception {
        restExpanseMockMvc.perform(get("/api/expanses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExpanseMockMvc.perform(get("/api/expanses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingExpanse() throws Exception {
        // Get the expanse
        restExpanseMockMvc.perform(get("/api/expanses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExpanse() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        int databaseSizeBeforeUpdate = expanseRepository.findAll().size();

        // Update the expanse
        Expanse updatedExpanse = expanseRepository.findById(expanse.getId()).get();
        // Disconnect from session so that the updates on updatedExpanse are not directly saved in db
        em.detach(updatedExpanse);
        updatedExpanse
            .loginId(UPDATED_LOGIN_ID)
            .voucherNo(UPDATED_VOUCHER_NO)
            .voucherDate(UPDATED_VOUCHER_DATE)
            .month(UPDATED_MONTH)
            .notes(UPDATED_NOTES)
            .isPosted(UPDATED_IS_POSTED)
            .postDate(UPDATED_POST_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        ExpanseDTO expanseDTO = expanseMapper.toDto(updatedExpanse);

        restExpanseMockMvc.perform(put("/api/expanses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expanseDTO)))
            .andExpect(status().isOk());

        // Validate the Expanse in the database
        List<Expanse> expanseList = expanseRepository.findAll();
        assertThat(expanseList).hasSize(databaseSizeBeforeUpdate);
        Expanse testExpanse = expanseList.get(expanseList.size() - 1);
        assertThat(testExpanse.getLoginId()).isEqualTo(UPDATED_LOGIN_ID);
        assertThat(testExpanse.getVoucherNo()).isEqualTo(UPDATED_VOUCHER_NO);
        assertThat(testExpanse.getVoucherDate()).isEqualTo(UPDATED_VOUCHER_DATE);
        assertThat(testExpanse.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testExpanse.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testExpanse.isIsPosted()).isEqualTo(UPDATED_IS_POSTED);
        assertThat(testExpanse.getPostDate()).isEqualTo(UPDATED_POST_DATE);
        assertThat(testExpanse.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testExpanse.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testExpanse.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testExpanse.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingExpanse() throws Exception {
        int databaseSizeBeforeUpdate = expanseRepository.findAll().size();

        // Create the Expanse
        ExpanseDTO expanseDTO = expanseMapper.toDto(expanse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpanseMockMvc.perform(put("/api/expanses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expanseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Expanse in the database
        List<Expanse> expanseList = expanseRepository.findAll();
        assertThat(expanseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExpanse() throws Exception {
        // Initialize the database
        expanseRepository.saveAndFlush(expanse);

        int databaseSizeBeforeDelete = expanseRepository.findAll().size();

        // Delete the expanse
        restExpanseMockMvc.perform(delete("/api/expanses/{id}", expanse.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Expanse> expanseList = expanseRepository.findAll();
        assertThat(expanseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

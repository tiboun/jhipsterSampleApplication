package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Timesheets;
import io.github.jhipster.application.repository.TimesheetsRepository;
import io.github.jhipster.application.service.TimesheetsService;
import io.github.jhipster.application.service.dto.TimesheetsDTO;
import io.github.jhipster.application.service.mapper.TimesheetsMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.sameInstant;
import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TimesheetsResource REST controller.
 *
 * @see TimesheetsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class TimesheetsResourceIntTest {

    private static final Integer DEFAULT_MONTH = 1;
    private static final Integer UPDATED_MONTH = 2;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private TimesheetsRepository timesheetsRepository;

    @Autowired
    private TimesheetsMapper timesheetsMapper;

    @Autowired
    private TimesheetsService timesheetsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTimesheetsMockMvc;

    private Timesheets timesheets;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TimesheetsResource timesheetsResource = new TimesheetsResource(timesheetsService);
        this.restTimesheetsMockMvc = MockMvcBuilders.standaloneSetup(timesheetsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Timesheets createEntity(EntityManager em) {
        Timesheets timesheets = new Timesheets()
            .month(DEFAULT_MONTH)
            .year(DEFAULT_YEAR)
            .creationDate(DEFAULT_CREATION_DATE);
        return timesheets;
    }

    @Before
    public void initTest() {
        timesheets = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimesheets() throws Exception {
        int databaseSizeBeforeCreate = timesheetsRepository.findAll().size();

        // Create the Timesheets
        TimesheetsDTO timesheetsDTO = timesheetsMapper.toDto(timesheets);
        restTimesheetsMockMvc.perform(post("/api/timesheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheetsDTO)))
            .andExpect(status().isCreated());

        // Validate the Timesheets in the database
        List<Timesheets> timesheetsList = timesheetsRepository.findAll();
        assertThat(timesheetsList).hasSize(databaseSizeBeforeCreate + 1);
        Timesheets testTimesheets = timesheetsList.get(timesheetsList.size() - 1);
        assertThat(testTimesheets.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testTimesheets.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testTimesheets.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void createTimesheetsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timesheetsRepository.findAll().size();

        // Create the Timesheets with an existing ID
        timesheets.setId(1L);
        TimesheetsDTO timesheetsDTO = timesheetsMapper.toDto(timesheets);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimesheetsMockMvc.perform(post("/api/timesheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheetsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Timesheets in the database
        List<Timesheets> timesheetsList = timesheetsRepository.findAll();
        assertThat(timesheetsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTimesheets() throws Exception {
        // Initialize the database
        timesheetsRepository.saveAndFlush(timesheets);

        // Get all the timesheetsList
        restTimesheetsMockMvc.perform(get("/api/timesheets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timesheets.getId().intValue())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))));
    }

    @Test
    @Transactional
    public void getTimesheets() throws Exception {
        // Initialize the database
        timesheetsRepository.saveAndFlush(timesheets);

        // Get the timesheets
        restTimesheetsMockMvc.perform(get("/api/timesheets/{id}", timesheets.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timesheets.getId().intValue()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingTimesheets() throws Exception {
        // Get the timesheets
        restTimesheetsMockMvc.perform(get("/api/timesheets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimesheets() throws Exception {
        // Initialize the database
        timesheetsRepository.saveAndFlush(timesheets);
        int databaseSizeBeforeUpdate = timesheetsRepository.findAll().size();

        // Update the timesheets
        Timesheets updatedTimesheets = timesheetsRepository.findOne(timesheets.getId());
        // Disconnect from session so that the updates on updatedTimesheets are not directly saved in db
        em.detach(updatedTimesheets);
        updatedTimesheets
            .month(UPDATED_MONTH)
            .year(UPDATED_YEAR)
            .creationDate(UPDATED_CREATION_DATE);
        TimesheetsDTO timesheetsDTO = timesheetsMapper.toDto(updatedTimesheets);

        restTimesheetsMockMvc.perform(put("/api/timesheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheetsDTO)))
            .andExpect(status().isOk());

        // Validate the Timesheets in the database
        List<Timesheets> timesheetsList = timesheetsRepository.findAll();
        assertThat(timesheetsList).hasSize(databaseSizeBeforeUpdate);
        Timesheets testTimesheets = timesheetsList.get(timesheetsList.size() - 1);
        assertThat(testTimesheets.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testTimesheets.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testTimesheets.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTimesheets() throws Exception {
        int databaseSizeBeforeUpdate = timesheetsRepository.findAll().size();

        // Create the Timesheets
        TimesheetsDTO timesheetsDTO = timesheetsMapper.toDto(timesheets);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTimesheetsMockMvc.perform(put("/api/timesheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheetsDTO)))
            .andExpect(status().isCreated());

        // Validate the Timesheets in the database
        List<Timesheets> timesheetsList = timesheetsRepository.findAll();
        assertThat(timesheetsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTimesheets() throws Exception {
        // Initialize the database
        timesheetsRepository.saveAndFlush(timesheets);
        int databaseSizeBeforeDelete = timesheetsRepository.findAll().size();

        // Get the timesheets
        restTimesheetsMockMvc.perform(delete("/api/timesheets/{id}", timesheets.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Timesheets> timesheetsList = timesheetsRepository.findAll();
        assertThat(timesheetsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Timesheets.class);
        Timesheets timesheets1 = new Timesheets();
        timesheets1.setId(1L);
        Timesheets timesheets2 = new Timesheets();
        timesheets2.setId(timesheets1.getId());
        assertThat(timesheets1).isEqualTo(timesheets2);
        timesheets2.setId(2L);
        assertThat(timesheets1).isNotEqualTo(timesheets2);
        timesheets1.setId(null);
        assertThat(timesheets1).isNotEqualTo(timesheets2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimesheetsDTO.class);
        TimesheetsDTO timesheetsDTO1 = new TimesheetsDTO();
        timesheetsDTO1.setId(1L);
        TimesheetsDTO timesheetsDTO2 = new TimesheetsDTO();
        assertThat(timesheetsDTO1).isNotEqualTo(timesheetsDTO2);
        timesheetsDTO2.setId(timesheetsDTO1.getId());
        assertThat(timesheetsDTO1).isEqualTo(timesheetsDTO2);
        timesheetsDTO2.setId(2L);
        assertThat(timesheetsDTO1).isNotEqualTo(timesheetsDTO2);
        timesheetsDTO1.setId(null);
        assertThat(timesheetsDTO1).isNotEqualTo(timesheetsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(timesheetsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(timesheetsMapper.fromId(null)).isNull();
    }
}

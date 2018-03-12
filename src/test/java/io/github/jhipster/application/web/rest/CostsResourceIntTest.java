package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Costs;
import io.github.jhipster.application.repository.CostsRepository;
import io.github.jhipster.application.service.CostsService;
import io.github.jhipster.application.service.dto.CostsDTO;
import io.github.jhipster.application.service.mapper.CostsMapper;
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
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CostsResource REST controller.
 *
 * @see CostsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class CostsResourceIntTest {

    private static final Integer DEFAULT_DAY_OF_MONTH = 1;
    private static final Integer UPDATED_DAY_OF_MONTH = 2;

    private static final Integer DEFAULT_SPENT_DAYS = 1;
    private static final Integer UPDATED_SPENT_DAYS = 2;

    @Autowired
    private CostsRepository costsRepository;

    @Autowired
    private CostsMapper costsMapper;

    @Autowired
    private CostsService costsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCostsMockMvc;

    private Costs costs;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CostsResource costsResource = new CostsResource(costsService);
        this.restCostsMockMvc = MockMvcBuilders.standaloneSetup(costsResource)
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
    public static Costs createEntity(EntityManager em) {
        Costs costs = new Costs()
            .dayOfMonth(DEFAULT_DAY_OF_MONTH)
            .spentDays(DEFAULT_SPENT_DAYS);
        return costs;
    }

    @Before
    public void initTest() {
        costs = createEntity(em);
    }

    @Test
    @Transactional
    public void createCosts() throws Exception {
        int databaseSizeBeforeCreate = costsRepository.findAll().size();

        // Create the Costs
        CostsDTO costsDTO = costsMapper.toDto(costs);
        restCostsMockMvc.perform(post("/api/costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costsDTO)))
            .andExpect(status().isCreated());

        // Validate the Costs in the database
        List<Costs> costsList = costsRepository.findAll();
        assertThat(costsList).hasSize(databaseSizeBeforeCreate + 1);
        Costs testCosts = costsList.get(costsList.size() - 1);
        assertThat(testCosts.getDayOfMonth()).isEqualTo(DEFAULT_DAY_OF_MONTH);
        assertThat(testCosts.getSpentDays()).isEqualTo(DEFAULT_SPENT_DAYS);
    }

    @Test
    @Transactional
    public void createCostsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = costsRepository.findAll().size();

        // Create the Costs with an existing ID
        costs.setId(1L);
        CostsDTO costsDTO = costsMapper.toDto(costs);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCostsMockMvc.perform(post("/api/costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Costs in the database
        List<Costs> costsList = costsRepository.findAll();
        assertThat(costsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCosts() throws Exception {
        // Initialize the database
        costsRepository.saveAndFlush(costs);

        // Get all the costsList
        restCostsMockMvc.perform(get("/api/costs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(costs.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayOfMonth").value(hasItem(DEFAULT_DAY_OF_MONTH)))
            .andExpect(jsonPath("$.[*].spentDays").value(hasItem(DEFAULT_SPENT_DAYS)));
    }

    @Test
    @Transactional
    public void getCosts() throws Exception {
        // Initialize the database
        costsRepository.saveAndFlush(costs);

        // Get the costs
        restCostsMockMvc.perform(get("/api/costs/{id}", costs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(costs.getId().intValue()))
            .andExpect(jsonPath("$.dayOfMonth").value(DEFAULT_DAY_OF_MONTH))
            .andExpect(jsonPath("$.spentDays").value(DEFAULT_SPENT_DAYS));
    }

    @Test
    @Transactional
    public void getNonExistingCosts() throws Exception {
        // Get the costs
        restCostsMockMvc.perform(get("/api/costs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCosts() throws Exception {
        // Initialize the database
        costsRepository.saveAndFlush(costs);
        int databaseSizeBeforeUpdate = costsRepository.findAll().size();

        // Update the costs
        Costs updatedCosts = costsRepository.findOne(costs.getId());
        // Disconnect from session so that the updates on updatedCosts are not directly saved in db
        em.detach(updatedCosts);
        updatedCosts
            .dayOfMonth(UPDATED_DAY_OF_MONTH)
            .spentDays(UPDATED_SPENT_DAYS);
        CostsDTO costsDTO = costsMapper.toDto(updatedCosts);

        restCostsMockMvc.perform(put("/api/costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costsDTO)))
            .andExpect(status().isOk());

        // Validate the Costs in the database
        List<Costs> costsList = costsRepository.findAll();
        assertThat(costsList).hasSize(databaseSizeBeforeUpdate);
        Costs testCosts = costsList.get(costsList.size() - 1);
        assertThat(testCosts.getDayOfMonth()).isEqualTo(UPDATED_DAY_OF_MONTH);
        assertThat(testCosts.getSpentDays()).isEqualTo(UPDATED_SPENT_DAYS);
    }

    @Test
    @Transactional
    public void updateNonExistingCosts() throws Exception {
        int databaseSizeBeforeUpdate = costsRepository.findAll().size();

        // Create the Costs
        CostsDTO costsDTO = costsMapper.toDto(costs);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCostsMockMvc.perform(put("/api/costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costsDTO)))
            .andExpect(status().isCreated());

        // Validate the Costs in the database
        List<Costs> costsList = costsRepository.findAll();
        assertThat(costsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCosts() throws Exception {
        // Initialize the database
        costsRepository.saveAndFlush(costs);
        int databaseSizeBeforeDelete = costsRepository.findAll().size();

        // Get the costs
        restCostsMockMvc.perform(delete("/api/costs/{id}", costs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Costs> costsList = costsRepository.findAll();
        assertThat(costsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Costs.class);
        Costs costs1 = new Costs();
        costs1.setId(1L);
        Costs costs2 = new Costs();
        costs2.setId(costs1.getId());
        assertThat(costs1).isEqualTo(costs2);
        costs2.setId(2L);
        assertThat(costs1).isNotEqualTo(costs2);
        costs1.setId(null);
        assertThat(costs1).isNotEqualTo(costs2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CostsDTO.class);
        CostsDTO costsDTO1 = new CostsDTO();
        costsDTO1.setId(1L);
        CostsDTO costsDTO2 = new CostsDTO();
        assertThat(costsDTO1).isNotEqualTo(costsDTO2);
        costsDTO2.setId(costsDTO1.getId());
        assertThat(costsDTO1).isEqualTo(costsDTO2);
        costsDTO2.setId(2L);
        assertThat(costsDTO1).isNotEqualTo(costsDTO2);
        costsDTO1.setId(null);
        assertThat(costsDTO1).isNotEqualTo(costsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(costsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(costsMapper.fromId(null)).isNull();
    }
}

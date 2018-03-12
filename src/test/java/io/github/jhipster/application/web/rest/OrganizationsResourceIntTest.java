package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Organizations;
import io.github.jhipster.application.repository.OrganizationsRepository;
import io.github.jhipster.application.service.OrganizationsService;
import io.github.jhipster.application.service.dto.OrganizationsDTO;
import io.github.jhipster.application.service.mapper.OrganizationsMapper;
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
 * Test class for the OrganizationsResource REST controller.
 *
 * @see OrganizationsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class OrganizationsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private OrganizationsRepository organizationsRepository;

    @Autowired
    private OrganizationsMapper organizationsMapper;

    @Autowired
    private OrganizationsService organizationsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrganizationsMockMvc;

    private Organizations organizations;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrganizationsResource organizationsResource = new OrganizationsResource(organizationsService);
        this.restOrganizationsMockMvc = MockMvcBuilders.standaloneSetup(organizationsResource)
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
    public static Organizations createEntity(EntityManager em) {
        Organizations organizations = new Organizations()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .creationDate(DEFAULT_CREATION_DATE);
        return organizations;
    }

    @Before
    public void initTest() {
        organizations = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrganizations() throws Exception {
        int databaseSizeBeforeCreate = organizationsRepository.findAll().size();

        // Create the Organizations
        OrganizationsDTO organizationsDTO = organizationsMapper.toDto(organizations);
        restOrganizationsMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationsDTO)))
            .andExpect(status().isCreated());

        // Validate the Organizations in the database
        List<Organizations> organizationsList = organizationsRepository.findAll();
        assertThat(organizationsList).hasSize(databaseSizeBeforeCreate + 1);
        Organizations testOrganizations = organizationsList.get(organizationsList.size() - 1);
        assertThat(testOrganizations.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrganizations.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrganizations.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void createOrganizationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organizationsRepository.findAll().size();

        // Create the Organizations with an existing ID
        organizations.setId(1L);
        OrganizationsDTO organizationsDTO = organizationsMapper.toDto(organizations);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationsMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Organizations in the database
        List<Organizations> organizationsList = organizationsRepository.findAll();
        assertThat(organizationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrganizations() throws Exception {
        // Initialize the database
        organizationsRepository.saveAndFlush(organizations);

        // Get all the organizationsList
        restOrganizationsMockMvc.perform(get("/api/organizations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organizations.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))));
    }

    @Test
    @Transactional
    public void getOrganizations() throws Exception {
        // Initialize the database
        organizationsRepository.saveAndFlush(organizations);

        // Get the organizations
        restOrganizationsMockMvc.perform(get("/api/organizations/{id}", organizations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(organizations.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingOrganizations() throws Exception {
        // Get the organizations
        restOrganizationsMockMvc.perform(get("/api/organizations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganizations() throws Exception {
        // Initialize the database
        organizationsRepository.saveAndFlush(organizations);
        int databaseSizeBeforeUpdate = organizationsRepository.findAll().size();

        // Update the organizations
        Organizations updatedOrganizations = organizationsRepository.findOne(organizations.getId());
        // Disconnect from session so that the updates on updatedOrganizations are not directly saved in db
        em.detach(updatedOrganizations);
        updatedOrganizations
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .creationDate(UPDATED_CREATION_DATE);
        OrganizationsDTO organizationsDTO = organizationsMapper.toDto(updatedOrganizations);

        restOrganizationsMockMvc.perform(put("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationsDTO)))
            .andExpect(status().isOk());

        // Validate the Organizations in the database
        List<Organizations> organizationsList = organizationsRepository.findAll();
        assertThat(organizationsList).hasSize(databaseSizeBeforeUpdate);
        Organizations testOrganizations = organizationsList.get(organizationsList.size() - 1);
        assertThat(testOrganizations.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganizations.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrganizations.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingOrganizations() throws Exception {
        int databaseSizeBeforeUpdate = organizationsRepository.findAll().size();

        // Create the Organizations
        OrganizationsDTO organizationsDTO = organizationsMapper.toDto(organizations);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrganizationsMockMvc.perform(put("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationsDTO)))
            .andExpect(status().isCreated());

        // Validate the Organizations in the database
        List<Organizations> organizationsList = organizationsRepository.findAll();
        assertThat(organizationsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrganizations() throws Exception {
        // Initialize the database
        organizationsRepository.saveAndFlush(organizations);
        int databaseSizeBeforeDelete = organizationsRepository.findAll().size();

        // Get the organizations
        restOrganizationsMockMvc.perform(delete("/api/organizations/{id}", organizations.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Organizations> organizationsList = organizationsRepository.findAll();
        assertThat(organizationsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Organizations.class);
        Organizations organizations1 = new Organizations();
        organizations1.setId(1L);
        Organizations organizations2 = new Organizations();
        organizations2.setId(organizations1.getId());
        assertThat(organizations1).isEqualTo(organizations2);
        organizations2.setId(2L);
        assertThat(organizations1).isNotEqualTo(organizations2);
        organizations1.setId(null);
        assertThat(organizations1).isNotEqualTo(organizations2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationsDTO.class);
        OrganizationsDTO organizationsDTO1 = new OrganizationsDTO();
        organizationsDTO1.setId(1L);
        OrganizationsDTO organizationsDTO2 = new OrganizationsDTO();
        assertThat(organizationsDTO1).isNotEqualTo(organizationsDTO2);
        organizationsDTO2.setId(organizationsDTO1.getId());
        assertThat(organizationsDTO1).isEqualTo(organizationsDTO2);
        organizationsDTO2.setId(2L);
        assertThat(organizationsDTO1).isNotEqualTo(organizationsDTO2);
        organizationsDTO1.setId(null);
        assertThat(organizationsDTO1).isNotEqualTo(organizationsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(organizationsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(organizationsMapper.fromId(null)).isNull();
    }
}

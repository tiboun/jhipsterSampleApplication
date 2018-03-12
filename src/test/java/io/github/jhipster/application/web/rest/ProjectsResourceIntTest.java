package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Projects;
import io.github.jhipster.application.repository.ProjectsRepository;
import io.github.jhipster.application.service.ProjectsService;
import io.github.jhipster.application.service.dto.ProjectsDTO;
import io.github.jhipster.application.service.mapper.ProjectsMapper;
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
 * Test class for the ProjectsResource REST controller.
 *
 * @see ProjectsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ProjectsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private ProjectsMapper projectsMapper;

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProjectsMockMvc;

    private Projects projects;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectsResource projectsResource = new ProjectsResource(projectsService);
        this.restProjectsMockMvc = MockMvcBuilders.standaloneSetup(projectsResource)
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
    public static Projects createEntity(EntityManager em) {
        Projects projects = new Projects()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .creationDate(DEFAULT_CREATION_DATE);
        return projects;
    }

    @Before
    public void initTest() {
        projects = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjects() throws Exception {
        int databaseSizeBeforeCreate = projectsRepository.findAll().size();

        // Create the Projects
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);
        restProjectsMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isCreated());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeCreate + 1);
        Projects testProjects = projectsList.get(projectsList.size() - 1);
        assertThat(testProjects.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjects.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProjects.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void createProjectsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectsRepository.findAll().size();

        // Create the Projects with an existing ID
        projects.setId(1L);
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectsMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProjects() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get all the projectsList
        restProjectsMockMvc.perform(get("/api/projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projects.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))));
    }

    @Test
    @Transactional
    public void getProjects() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);

        // Get the projects
        restProjectsMockMvc.perform(get("/api/projects/{id}", projects.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projects.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingProjects() throws Exception {
        // Get the projects
        restProjectsMockMvc.perform(get("/api/projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjects() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);
        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();

        // Update the projects
        Projects updatedProjects = projectsRepository.findOne(projects.getId());
        // Disconnect from session so that the updates on updatedProjects are not directly saved in db
        em.detach(updatedProjects);
        updatedProjects
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .creationDate(UPDATED_CREATION_DATE);
        ProjectsDTO projectsDTO = projectsMapper.toDto(updatedProjects);

        restProjectsMockMvc.perform(put("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isOk());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate);
        Projects testProjects = projectsList.get(projectsList.size() - 1);
        assertThat(testProjects.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjects.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjects.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingProjects() throws Exception {
        int databaseSizeBeforeUpdate = projectsRepository.findAll().size();

        // Create the Projects
        ProjectsDTO projectsDTO = projectsMapper.toDto(projects);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjectsMockMvc.perform(put("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectsDTO)))
            .andExpect(status().isCreated());

        // Validate the Projects in the database
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProjects() throws Exception {
        // Initialize the database
        projectsRepository.saveAndFlush(projects);
        int databaseSizeBeforeDelete = projectsRepository.findAll().size();

        // Get the projects
        restProjectsMockMvc.perform(delete("/api/projects/{id}", projects.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Projects> projectsList = projectsRepository.findAll();
        assertThat(projectsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Projects.class);
        Projects projects1 = new Projects();
        projects1.setId(1L);
        Projects projects2 = new Projects();
        projects2.setId(projects1.getId());
        assertThat(projects1).isEqualTo(projects2);
        projects2.setId(2L);
        assertThat(projects1).isNotEqualTo(projects2);
        projects1.setId(null);
        assertThat(projects1).isNotEqualTo(projects2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectsDTO.class);
        ProjectsDTO projectsDTO1 = new ProjectsDTO();
        projectsDTO1.setId(1L);
        ProjectsDTO projectsDTO2 = new ProjectsDTO();
        assertThat(projectsDTO1).isNotEqualTo(projectsDTO2);
        projectsDTO2.setId(projectsDTO1.getId());
        assertThat(projectsDTO1).isEqualTo(projectsDTO2);
        projectsDTO2.setId(2L);
        assertThat(projectsDTO1).isNotEqualTo(projectsDTO2);
        projectsDTO1.setId(null);
        assertThat(projectsDTO1).isNotEqualTo(projectsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(projectsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(projectsMapper.fromId(null)).isNull();
    }
}

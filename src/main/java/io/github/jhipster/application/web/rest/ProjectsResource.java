package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.ProjectsService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.service.dto.ProjectsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Projects.
 */
@RestController
@RequestMapping("/api")
public class ProjectsResource {

    private final Logger log = LoggerFactory.getLogger(ProjectsResource.class);

    private static final String ENTITY_NAME = "projects";

    private final ProjectsService projectsService;

    public ProjectsResource(ProjectsService projectsService) {
        this.projectsService = projectsService;
    }

    /**
     * POST  /projects : Create a new projects.
     *
     * @param projectsDTO the projectsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projectsDTO, or with status 400 (Bad Request) if the projects has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/projects")
    @Timed
    public ResponseEntity<ProjectsDTO> createProjects(@RequestBody ProjectsDTO projectsDTO) throws URISyntaxException {
        log.debug("REST request to save Projects : {}", projectsDTO);
        if (projectsDTO.getId() != null) {
            throw new BadRequestAlertException("A new projects cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectsDTO result = projectsService.save(projectsDTO);
        return ResponseEntity.created(new URI("/api/projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /projects : Updates an existing projects.
     *
     * @param projectsDTO the projectsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projectsDTO,
     * or with status 400 (Bad Request) if the projectsDTO is not valid,
     * or with status 500 (Internal Server Error) if the projectsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/projects")
    @Timed
    public ResponseEntity<ProjectsDTO> updateProjects(@RequestBody ProjectsDTO projectsDTO) throws URISyntaxException {
        log.debug("REST request to update Projects : {}", projectsDTO);
        if (projectsDTO.getId() == null) {
            return createProjects(projectsDTO);
        }
        ProjectsDTO result = projectsService.save(projectsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projectsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /projects : get all the projects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of projects in body
     */
    @GetMapping("/projects")
    @Timed
    public List<ProjectsDTO> getAllProjects() {
        log.debug("REST request to get all Projects");
        return projectsService.findAll();
        }

    /**
     * GET  /projects/:id : get the "id" projects.
     *
     * @param id the id of the projectsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projectsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/projects/{id}")
    @Timed
    public ResponseEntity<ProjectsDTO> getProjects(@PathVariable Long id) {
        log.debug("REST request to get Projects : {}", id);
        ProjectsDTO projectsDTO = projectsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(projectsDTO));
    }

    /**
     * DELETE  /projects/:id : delete the "id" projects.
     *
     * @param id the id of the projectsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/projects/{id}")
    @Timed
    public ResponseEntity<Void> deleteProjects(@PathVariable Long id) {
        log.debug("REST request to delete Projects : {}", id);
        projectsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

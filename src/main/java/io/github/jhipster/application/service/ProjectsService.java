package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.ProjectsDTO;
import java.util.List;

/**
 * Service Interface for managing Projects.
 */
public interface ProjectsService {

    /**
     * Save a projects.
     *
     * @param projectsDTO the entity to save
     * @return the persisted entity
     */
    ProjectsDTO save(ProjectsDTO projectsDTO);

    /**
     * Get all the projects.
     *
     * @return the list of entities
     */
    List<ProjectsDTO> findAll();

    /**
     * Get the "id" projects.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ProjectsDTO findOne(Long id);

    /**
     * Delete the "id" projects.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ProjectsService;
import io.github.jhipster.application.domain.Projects;
import io.github.jhipster.application.repository.ProjectsRepository;
import io.github.jhipster.application.service.dto.ProjectsDTO;
import io.github.jhipster.application.service.mapper.ProjectsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Projects.
 */
@Service
@Transactional
public class ProjectsServiceImpl implements ProjectsService {

    private final Logger log = LoggerFactory.getLogger(ProjectsServiceImpl.class);

    private final ProjectsRepository projectsRepository;

    private final ProjectsMapper projectsMapper;

    public ProjectsServiceImpl(ProjectsRepository projectsRepository, ProjectsMapper projectsMapper) {
        this.projectsRepository = projectsRepository;
        this.projectsMapper = projectsMapper;
    }

    /**
     * Save a projects.
     *
     * @param projectsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProjectsDTO save(ProjectsDTO projectsDTO) {
        log.debug("Request to save Projects : {}", projectsDTO);
        Projects projects = projectsMapper.toEntity(projectsDTO);
        projects = projectsRepository.save(projects);
        return projectsMapper.toDto(projects);
    }

    /**
     * Get all the projects.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectsDTO> findAll() {
        log.debug("Request to get all Projects");
        return projectsRepository.findAll().stream()
            .map(projectsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one projects by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProjectsDTO findOne(Long id) {
        log.debug("Request to get Projects : {}", id);
        Projects projects = projectsRepository.findOne(id);
        return projectsMapper.toDto(projects);
    }

    /**
     * Delete the projects by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Projects : {}", id);
        projectsRepository.delete(id);
    }
}

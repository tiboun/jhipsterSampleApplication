package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ProjectsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Projects and its DTO ProjectsDTO.
 */
@Mapper(componentModel = "spring", uses = {OrganizationsMapper.class})
public interface ProjectsMapper extends EntityMapper<ProjectsDTO, Projects> {

    @Mapping(source = "organizations.id", target = "organizationsId")
    @Mapping(source = "organization.id", target = "organizationId")
    ProjectsDTO toDto(Projects projects);

    @Mapping(source = "organizationsId", target = "organizations")
    @Mapping(source = "organizationId", target = "organization")
    Projects toEntity(ProjectsDTO projectsDTO);

    default Projects fromId(Long id) {
        if (id == null) {
            return null;
        }
        Projects projects = new Projects();
        projects.setId(id);
        return projects;
    }
}

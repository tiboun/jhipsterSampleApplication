package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ActivitiesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Activities and its DTO ActivitiesDTO.
 */
@Mapper(componentModel = "spring", uses = {TimesheetsMapper.class, ProjectsMapper.class})
public interface ActivitiesMapper extends EntityMapper<ActivitiesDTO, Activities> {

    @Mapping(source = "timesheets.id", target = "timesheetsId")
    @Mapping(source = "project.id", target = "projectId")
    ActivitiesDTO toDto(Activities activities);

    @Mapping(source = "timesheetsId", target = "timesheets")
    @Mapping(source = "projectId", target = "project")
    @Mapping(target = "costs", ignore = true)
    Activities toEntity(ActivitiesDTO activitiesDTO);

    default Activities fromId(Long id) {
        if (id == null) {
            return null;
        }
        Activities activities = new Activities();
        activities.setId(id);
        return activities;
    }
}

package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.OrganizationsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Organizations and its DTO OrganizationsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrganizationsMapper extends EntityMapper<OrganizationsDTO, Organizations> {


    @Mapping(target = "projects", ignore = true)
    Organizations toEntity(OrganizationsDTO organizationsDTO);

    default Organizations fromId(Long id) {
        if (id == null) {
            return null;
        }
        Organizations organizations = new Organizations();
        organizations.setId(id);
        return organizations;
    }
}

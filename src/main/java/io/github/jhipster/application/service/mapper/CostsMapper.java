package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.CostsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Costs and its DTO CostsDTO.
 */
@Mapper(componentModel = "spring", uses = {ActivitiesMapper.class})
public interface CostsMapper extends EntityMapper<CostsDTO, Costs> {

    @Mapping(source = "activities.id", target = "activitiesId")
    CostsDTO toDto(Costs costs);

    @Mapping(source = "activitiesId", target = "activities")
    Costs toEntity(CostsDTO costsDTO);

    default Costs fromId(Long id) {
        if (id == null) {
            return null;
        }
        Costs costs = new Costs();
        costs.setId(id);
        return costs;
    }
}

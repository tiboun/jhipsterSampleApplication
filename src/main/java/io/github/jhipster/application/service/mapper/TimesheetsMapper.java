package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.TimesheetsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Timesheets and its DTO TimesheetsDTO.
 */
@Mapper(componentModel = "spring", uses = {UsersMapper.class})
public interface TimesheetsMapper extends EntityMapper<TimesheetsDTO, Timesheets> {

    @Mapping(source = "users.id", target = "usersId")
    @Mapping(source = "user.id", target = "userId")
    TimesheetsDTO toDto(Timesheets timesheets);

    @Mapping(source = "usersId", target = "users")
    @Mapping(source = "userId", target = "user")
    @Mapping(target = "activities", ignore = true)
    Timesheets toEntity(TimesheetsDTO timesheetsDTO);

    default Timesheets fromId(Long id) {
        if (id == null) {
            return null;
        }
        Timesheets timesheets = new Timesheets();
        timesheets.setId(id);
        return timesheets;
    }
}

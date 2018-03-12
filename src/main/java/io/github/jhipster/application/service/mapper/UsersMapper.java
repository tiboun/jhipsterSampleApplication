package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.UsersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Users and its DTO UsersDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UsersMapper extends EntityMapper<UsersDTO, Users> {


    @Mapping(target = "timesheets", ignore = true)
    Users toEntity(UsersDTO usersDTO);

    default Users fromId(Long id) {
        if (id == null) {
            return null;
        }
        Users users = new Users();
        users.setId(id);
        return users;
    }
}

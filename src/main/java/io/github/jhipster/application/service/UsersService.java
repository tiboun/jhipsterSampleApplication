package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.UsersDTO;
import java.util.List;

/**
 * Service Interface for managing Users.
 */
public interface UsersService {

    /**
     * Save a users.
     *
     * @param usersDTO the entity to save
     * @return the persisted entity
     */
    UsersDTO save(UsersDTO usersDTO);

    /**
     * Get all the users.
     *
     * @return the list of entities
     */
    List<UsersDTO> findAll();

    /**
     * Get the "id" users.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UsersDTO findOne(Long id);

    /**
     * Delete the "id" users.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

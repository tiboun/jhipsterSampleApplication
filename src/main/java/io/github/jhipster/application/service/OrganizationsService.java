package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.OrganizationsDTO;
import java.util.List;

/**
 * Service Interface for managing Organizations.
 */
public interface OrganizationsService {

    /**
     * Save a organizations.
     *
     * @param organizationsDTO the entity to save
     * @return the persisted entity
     */
    OrganizationsDTO save(OrganizationsDTO organizationsDTO);

    /**
     * Get all the organizations.
     *
     * @return the list of entities
     */
    List<OrganizationsDTO> findAll();

    /**
     * Get the "id" organizations.
     *
     * @param id the id of the entity
     * @return the entity
     */
    OrganizationsDTO findOne(Long id);

    /**
     * Delete the "id" organizations.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

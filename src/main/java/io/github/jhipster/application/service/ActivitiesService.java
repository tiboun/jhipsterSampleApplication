package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.ActivitiesDTO;
import java.util.List;

/**
 * Service Interface for managing Activities.
 */
public interface ActivitiesService {

    /**
     * Save a activities.
     *
     * @param activitiesDTO the entity to save
     * @return the persisted entity
     */
    ActivitiesDTO save(ActivitiesDTO activitiesDTO);

    /**
     * Get all the activities.
     *
     * @return the list of entities
     */
    List<ActivitiesDTO> findAll();

    /**
     * Get the "id" activities.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ActivitiesDTO findOne(Long id);

    /**
     * Delete the "id" activities.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

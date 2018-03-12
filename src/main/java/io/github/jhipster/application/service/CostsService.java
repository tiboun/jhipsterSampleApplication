package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.CostsDTO;
import java.util.List;

/**
 * Service Interface for managing Costs.
 */
public interface CostsService {

    /**
     * Save a costs.
     *
     * @param costsDTO the entity to save
     * @return the persisted entity
     */
    CostsDTO save(CostsDTO costsDTO);

    /**
     * Get all the costs.
     *
     * @return the list of entities
     */
    List<CostsDTO> findAll();

    /**
     * Get the "id" costs.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CostsDTO findOne(Long id);

    /**
     * Delete the "id" costs.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.TimesheetsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Timesheets.
 */
public interface TimesheetsService {

    /**
     * Save a timesheets.
     *
     * @param timesheetsDTO the entity to save
     * @return the persisted entity
     */
    TimesheetsDTO save(TimesheetsDTO timesheetsDTO);

    /**
     * Get all the timesheets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TimesheetsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" timesheets.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TimesheetsDTO findOne(Long id);

    /**
     * Delete the "id" timesheets.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.TimesheetsService;
import io.github.jhipster.application.domain.Timesheets;
import io.github.jhipster.application.repository.TimesheetsRepository;
import io.github.jhipster.application.service.dto.TimesheetsDTO;
import io.github.jhipster.application.service.mapper.TimesheetsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Timesheets.
 */
@Service
@Transactional
public class TimesheetsServiceImpl implements TimesheetsService {

    private final Logger log = LoggerFactory.getLogger(TimesheetsServiceImpl.class);

    private final TimesheetsRepository timesheetsRepository;

    private final TimesheetsMapper timesheetsMapper;

    public TimesheetsServiceImpl(TimesheetsRepository timesheetsRepository, TimesheetsMapper timesheetsMapper) {
        this.timesheetsRepository = timesheetsRepository;
        this.timesheetsMapper = timesheetsMapper;
    }

    /**
     * Save a timesheets.
     *
     * @param timesheetsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TimesheetsDTO save(TimesheetsDTO timesheetsDTO) {
        log.debug("Request to save Timesheets : {}", timesheetsDTO);
        Timesheets timesheets = timesheetsMapper.toEntity(timesheetsDTO);
        timesheets = timesheetsRepository.save(timesheets);
        return timesheetsMapper.toDto(timesheets);
    }

    /**
     * Get all the timesheets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TimesheetsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Timesheets");
        return timesheetsRepository.findAll(pageable)
            .map(timesheetsMapper::toDto);
    }

    /**
     * Get one timesheets by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TimesheetsDTO findOne(Long id) {
        log.debug("Request to get Timesheets : {}", id);
        Timesheets timesheets = timesheetsRepository.findOne(id);
        return timesheetsMapper.toDto(timesheets);
    }

    /**
     * Delete the timesheets by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Timesheets : {}", id);
        timesheetsRepository.delete(id);
    }
}

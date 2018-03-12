package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ActivitiesService;
import io.github.jhipster.application.domain.Activities;
import io.github.jhipster.application.repository.ActivitiesRepository;
import io.github.jhipster.application.service.dto.ActivitiesDTO;
import io.github.jhipster.application.service.mapper.ActivitiesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Activities.
 */
@Service
@Transactional
public class ActivitiesServiceImpl implements ActivitiesService {

    private final Logger log = LoggerFactory.getLogger(ActivitiesServiceImpl.class);

    private final ActivitiesRepository activitiesRepository;

    private final ActivitiesMapper activitiesMapper;

    public ActivitiesServiceImpl(ActivitiesRepository activitiesRepository, ActivitiesMapper activitiesMapper) {
        this.activitiesRepository = activitiesRepository;
        this.activitiesMapper = activitiesMapper;
    }

    /**
     * Save a activities.
     *
     * @param activitiesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ActivitiesDTO save(ActivitiesDTO activitiesDTO) {
        log.debug("Request to save Activities : {}", activitiesDTO);
        Activities activities = activitiesMapper.toEntity(activitiesDTO);
        activities = activitiesRepository.save(activities);
        return activitiesMapper.toDto(activities);
    }

    /**
     * Get all the activities.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ActivitiesDTO> findAll() {
        log.debug("Request to get all Activities");
        return activitiesRepository.findAll().stream()
            .map(activitiesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one activities by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ActivitiesDTO findOne(Long id) {
        log.debug("Request to get Activities : {}", id);
        Activities activities = activitiesRepository.findOne(id);
        return activitiesMapper.toDto(activities);
    }

    /**
     * Delete the activities by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Activities : {}", id);
        activitiesRepository.delete(id);
    }
}

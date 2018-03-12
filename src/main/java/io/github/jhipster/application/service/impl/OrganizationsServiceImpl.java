package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.OrganizationsService;
import io.github.jhipster.application.domain.Organizations;
import io.github.jhipster.application.repository.OrganizationsRepository;
import io.github.jhipster.application.service.dto.OrganizationsDTO;
import io.github.jhipster.application.service.mapper.OrganizationsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Organizations.
 */
@Service
@Transactional
public class OrganizationsServiceImpl implements OrganizationsService {

    private final Logger log = LoggerFactory.getLogger(OrganizationsServiceImpl.class);

    private final OrganizationsRepository organizationsRepository;

    private final OrganizationsMapper organizationsMapper;

    public OrganizationsServiceImpl(OrganizationsRepository organizationsRepository, OrganizationsMapper organizationsMapper) {
        this.organizationsRepository = organizationsRepository;
        this.organizationsMapper = organizationsMapper;
    }

    /**
     * Save a organizations.
     *
     * @param organizationsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrganizationsDTO save(OrganizationsDTO organizationsDTO) {
        log.debug("Request to save Organizations : {}", organizationsDTO);
        Organizations organizations = organizationsMapper.toEntity(organizationsDTO);
        organizations = organizationsRepository.save(organizations);
        return organizationsMapper.toDto(organizations);
    }

    /**
     * Get all the organizations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrganizationsDTO> findAll() {
        log.debug("Request to get all Organizations");
        return organizationsRepository.findAll().stream()
            .map(organizationsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one organizations by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OrganizationsDTO findOne(Long id) {
        log.debug("Request to get Organizations : {}", id);
        Organizations organizations = organizationsRepository.findOne(id);
        return organizationsMapper.toDto(organizations);
    }

    /**
     * Delete the organizations by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Organizations : {}", id);
        organizationsRepository.delete(id);
    }
}

package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.CostsService;
import io.github.jhipster.application.domain.Costs;
import io.github.jhipster.application.repository.CostsRepository;
import io.github.jhipster.application.service.dto.CostsDTO;
import io.github.jhipster.application.service.mapper.CostsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Costs.
 */
@Service
@Transactional
public class CostsServiceImpl implements CostsService {

    private final Logger log = LoggerFactory.getLogger(CostsServiceImpl.class);

    private final CostsRepository costsRepository;

    private final CostsMapper costsMapper;

    public CostsServiceImpl(CostsRepository costsRepository, CostsMapper costsMapper) {
        this.costsRepository = costsRepository;
        this.costsMapper = costsMapper;
    }

    /**
     * Save a costs.
     *
     * @param costsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CostsDTO save(CostsDTO costsDTO) {
        log.debug("Request to save Costs : {}", costsDTO);
        Costs costs = costsMapper.toEntity(costsDTO);
        costs = costsRepository.save(costs);
        return costsMapper.toDto(costs);
    }

    /**
     * Get all the costs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CostsDTO> findAll() {
        log.debug("Request to get all Costs");
        return costsRepository.findAll().stream()
            .map(costsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one costs by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CostsDTO findOne(Long id) {
        log.debug("Request to get Costs : {}", id);
        Costs costs = costsRepository.findOne(id);
        return costsMapper.toDto(costs);
    }

    /**
     * Delete the costs by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Costs : {}", id);
        costsRepository.delete(id);
    }
}

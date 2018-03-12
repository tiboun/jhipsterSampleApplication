package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.CostsService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.service.dto.CostsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Costs.
 */
@RestController
@RequestMapping("/api")
public class CostsResource {

    private final Logger log = LoggerFactory.getLogger(CostsResource.class);

    private static final String ENTITY_NAME = "costs";

    private final CostsService costsService;

    public CostsResource(CostsService costsService) {
        this.costsService = costsService;
    }

    /**
     * POST  /costs : Create a new costs.
     *
     * @param costsDTO the costsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new costsDTO, or with status 400 (Bad Request) if the costs has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/costs")
    @Timed
    public ResponseEntity<CostsDTO> createCosts(@RequestBody CostsDTO costsDTO) throws URISyntaxException {
        log.debug("REST request to save Costs : {}", costsDTO);
        if (costsDTO.getId() != null) {
            throw new BadRequestAlertException("A new costs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CostsDTO result = costsService.save(costsDTO);
        return ResponseEntity.created(new URI("/api/costs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /costs : Updates an existing costs.
     *
     * @param costsDTO the costsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated costsDTO,
     * or with status 400 (Bad Request) if the costsDTO is not valid,
     * or with status 500 (Internal Server Error) if the costsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/costs")
    @Timed
    public ResponseEntity<CostsDTO> updateCosts(@RequestBody CostsDTO costsDTO) throws URISyntaxException {
        log.debug("REST request to update Costs : {}", costsDTO);
        if (costsDTO.getId() == null) {
            return createCosts(costsDTO);
        }
        CostsDTO result = costsService.save(costsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, costsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /costs : get all the costs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of costs in body
     */
    @GetMapping("/costs")
    @Timed
    public List<CostsDTO> getAllCosts() {
        log.debug("REST request to get all Costs");
        return costsService.findAll();
        }

    /**
     * GET  /costs/:id : get the "id" costs.
     *
     * @param id the id of the costsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the costsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/costs/{id}")
    @Timed
    public ResponseEntity<CostsDTO> getCosts(@PathVariable Long id) {
        log.debug("REST request to get Costs : {}", id);
        CostsDTO costsDTO = costsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(costsDTO));
    }

    /**
     * DELETE  /costs/:id : delete the "id" costs.
     *
     * @param id the id of the costsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/costs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCosts(@PathVariable Long id) {
        log.debug("REST request to delete Costs : {}", id);
        costsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

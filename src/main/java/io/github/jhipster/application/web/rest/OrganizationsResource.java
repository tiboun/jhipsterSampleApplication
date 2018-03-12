package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.OrganizationsService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.service.dto.OrganizationsDTO;
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
 * REST controller for managing Organizations.
 */
@RestController
@RequestMapping("/api")
public class OrganizationsResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationsResource.class);

    private static final String ENTITY_NAME = "organizations";

    private final OrganizationsService organizationsService;

    public OrganizationsResource(OrganizationsService organizationsService) {
        this.organizationsService = organizationsService;
    }

    /**
     * POST  /organizations : Create a new organizations.
     *
     * @param organizationsDTO the organizationsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new organizationsDTO, or with status 400 (Bad Request) if the organizations has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/organizations")
    @Timed
    public ResponseEntity<OrganizationsDTO> createOrganizations(@RequestBody OrganizationsDTO organizationsDTO) throws URISyntaxException {
        log.debug("REST request to save Organizations : {}", organizationsDTO);
        if (organizationsDTO.getId() != null) {
            throw new BadRequestAlertException("A new organizations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganizationsDTO result = organizationsService.save(organizationsDTO);
        return ResponseEntity.created(new URI("/api/organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /organizations : Updates an existing organizations.
     *
     * @param organizationsDTO the organizationsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated organizationsDTO,
     * or with status 400 (Bad Request) if the organizationsDTO is not valid,
     * or with status 500 (Internal Server Error) if the organizationsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/organizations")
    @Timed
    public ResponseEntity<OrganizationsDTO> updateOrganizations(@RequestBody OrganizationsDTO organizationsDTO) throws URISyntaxException {
        log.debug("REST request to update Organizations : {}", organizationsDTO);
        if (organizationsDTO.getId() == null) {
            return createOrganizations(organizationsDTO);
        }
        OrganizationsDTO result = organizationsService.save(organizationsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, organizationsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /organizations : get all the organizations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of organizations in body
     */
    @GetMapping("/organizations")
    @Timed
    public List<OrganizationsDTO> getAllOrganizations() {
        log.debug("REST request to get all Organizations");
        return organizationsService.findAll();
        }

    /**
     * GET  /organizations/:id : get the "id" organizations.
     *
     * @param id the id of the organizationsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the organizationsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/organizations/{id}")
    @Timed
    public ResponseEntity<OrganizationsDTO> getOrganizations(@PathVariable Long id) {
        log.debug("REST request to get Organizations : {}", id);
        OrganizationsDTO organizationsDTO = organizationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(organizationsDTO));
    }

    /**
     * DELETE  /organizations/:id : delete the "id" organizations.
     *
     * @param id the id of the organizationsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/organizations/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrganizations(@PathVariable Long id) {
        log.debug("REST request to delete Organizations : {}", id);
        organizationsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

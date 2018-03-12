package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.ActivitiesService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.service.dto.ActivitiesDTO;
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
 * REST controller for managing Activities.
 */
@RestController
@RequestMapping("/api")
public class ActivitiesResource {

    private final Logger log = LoggerFactory.getLogger(ActivitiesResource.class);

    private static final String ENTITY_NAME = "activities";

    private final ActivitiesService activitiesService;

    public ActivitiesResource(ActivitiesService activitiesService) {
        this.activitiesService = activitiesService;
    }

    /**
     * POST  /activities : Create a new activities.
     *
     * @param activitiesDTO the activitiesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new activitiesDTO, or with status 400 (Bad Request) if the activities has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/activities")
    @Timed
    public ResponseEntity<ActivitiesDTO> createActivities(@RequestBody ActivitiesDTO activitiesDTO) throws URISyntaxException {
        log.debug("REST request to save Activities : {}", activitiesDTO);
        if (activitiesDTO.getId() != null) {
            throw new BadRequestAlertException("A new activities cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivitiesDTO result = activitiesService.save(activitiesDTO);
        return ResponseEntity.created(new URI("/api/activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /activities : Updates an existing activities.
     *
     * @param activitiesDTO the activitiesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated activitiesDTO,
     * or with status 400 (Bad Request) if the activitiesDTO is not valid,
     * or with status 500 (Internal Server Error) if the activitiesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/activities")
    @Timed
    public ResponseEntity<ActivitiesDTO> updateActivities(@RequestBody ActivitiesDTO activitiesDTO) throws URISyntaxException {
        log.debug("REST request to update Activities : {}", activitiesDTO);
        if (activitiesDTO.getId() == null) {
            return createActivities(activitiesDTO);
        }
        ActivitiesDTO result = activitiesService.save(activitiesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, activitiesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /activities : get all the activities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of activities in body
     */
    @GetMapping("/activities")
    @Timed
    public List<ActivitiesDTO> getAllActivities() {
        log.debug("REST request to get all Activities");
        return activitiesService.findAll();
        }

    /**
     * GET  /activities/:id : get the "id" activities.
     *
     * @param id the id of the activitiesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the activitiesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/activities/{id}")
    @Timed
    public ResponseEntity<ActivitiesDTO> getActivities(@PathVariable Long id) {
        log.debug("REST request to get Activities : {}", id);
        ActivitiesDTO activitiesDTO = activitiesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(activitiesDTO));
    }

    /**
     * DELETE  /activities/:id : delete the "id" activities.
     *
     * @param id the id of the activitiesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteActivities(@PathVariable Long id) {
        log.debug("REST request to delete Activities : {}", id);
        activitiesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.TimesheetsService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.TimesheetsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Timesheets.
 */
@RestController
@RequestMapping("/api")
public class TimesheetsResource {

    private final Logger log = LoggerFactory.getLogger(TimesheetsResource.class);

    private static final String ENTITY_NAME = "timesheets";

    private final TimesheetsService timesheetsService;

    public TimesheetsResource(TimesheetsService timesheetsService) {
        this.timesheetsService = timesheetsService;
    }

    /**
     * POST  /timesheets : Create a new timesheets.
     *
     * @param timesheetsDTO the timesheetsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timesheetsDTO, or with status 400 (Bad Request) if the timesheets has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/timesheets")
    @Timed
    public ResponseEntity<TimesheetsDTO> createTimesheets(@RequestBody TimesheetsDTO timesheetsDTO) throws URISyntaxException {
        log.debug("REST request to save Timesheets : {}", timesheetsDTO);
        if (timesheetsDTO.getId() != null) {
            throw new BadRequestAlertException("A new timesheets cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimesheetsDTO result = timesheetsService.save(timesheetsDTO);
        return ResponseEntity.created(new URI("/api/timesheets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /timesheets : Updates an existing timesheets.
     *
     * @param timesheetsDTO the timesheetsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timesheetsDTO,
     * or with status 400 (Bad Request) if the timesheetsDTO is not valid,
     * or with status 500 (Internal Server Error) if the timesheetsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/timesheets")
    @Timed
    public ResponseEntity<TimesheetsDTO> updateTimesheets(@RequestBody TimesheetsDTO timesheetsDTO) throws URISyntaxException {
        log.debug("REST request to update Timesheets : {}", timesheetsDTO);
        if (timesheetsDTO.getId() == null) {
            return createTimesheets(timesheetsDTO);
        }
        TimesheetsDTO result = timesheetsService.save(timesheetsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timesheetsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /timesheets : get all the timesheets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of timesheets in body
     */
    @GetMapping("/timesheets")
    @Timed
    public ResponseEntity<List<TimesheetsDTO>> getAllTimesheets(Pageable pageable) {
        log.debug("REST request to get a page of Timesheets");
        Page<TimesheetsDTO> page = timesheetsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/timesheets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /timesheets/:id : get the "id" timesheets.
     *
     * @param id the id of the timesheetsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timesheetsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/timesheets/{id}")
    @Timed
    public ResponseEntity<TimesheetsDTO> getTimesheets(@PathVariable Long id) {
        log.debug("REST request to get Timesheets : {}", id);
        TimesheetsDTO timesheetsDTO = timesheetsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(timesheetsDTO));
    }

    /**
     * DELETE  /timesheets/:id : delete the "id" timesheets.
     *
     * @param id the id of the timesheetsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/timesheets/{id}")
    @Timed
    public ResponseEntity<Void> deleteTimesheets(@PathVariable Long id) {
        log.debug("REST request to delete Timesheets : {}", id);
        timesheetsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

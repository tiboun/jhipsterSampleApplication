package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.UsersService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.service.dto.UsersDTO;
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
 * REST controller for managing Users.
 */
@RestController
@RequestMapping("/api")
public class UsersResource {

    private final Logger log = LoggerFactory.getLogger(UsersResource.class);

    private static final String ENTITY_NAME = "users";

    private final UsersService usersService;

    public UsersResource(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * POST  /users : Create a new users.
     *
     * @param usersDTO the usersDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new usersDTO, or with status 400 (Bad Request) if the users has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/users")
    @Timed
    public ResponseEntity<UsersDTO> createUsers(@RequestBody UsersDTO usersDTO) throws URISyntaxException {
        log.debug("REST request to save Users : {}", usersDTO);
        if (usersDTO.getId() != null) {
            throw new BadRequestAlertException("A new users cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsersDTO result = usersService.save(usersDTO);
        return ResponseEntity.created(new URI("/api/users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /users : Updates an existing users.
     *
     * @param usersDTO the usersDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated usersDTO,
     * or with status 400 (Bad Request) if the usersDTO is not valid,
     * or with status 500 (Internal Server Error) if the usersDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/users")
    @Timed
    public ResponseEntity<UsersDTO> updateUsers(@RequestBody UsersDTO usersDTO) throws URISyntaxException {
        log.debug("REST request to update Users : {}", usersDTO);
        if (usersDTO.getId() == null) {
            return createUsers(usersDTO);
        }
        UsersDTO result = usersService.save(usersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, usersDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /users : get all the users.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of users in body
     */
    @GetMapping("/users")
    @Timed
    public List<UsersDTO> getAllUsers() {
        log.debug("REST request to get all Users");
        return usersService.findAll();
        }

    /**
     * GET  /users/:id : get the "id" users.
     *
     * @param id the id of the usersDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the usersDTO, or with status 404 (Not Found)
     */
    @GetMapping("/users/{id}")
    @Timed
    public ResponseEntity<UsersDTO> getUsers(@PathVariable Long id) {
        log.debug("REST request to get Users : {}", id);
        UsersDTO usersDTO = usersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(usersDTO));
    }

    /**
     * DELETE  /users/:id : delete the "id" users.
     *
     * @param id the id of the usersDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{id}")
    @Timed
    public ResponseEntity<Void> deleteUsers(@PathVariable Long id) {
        log.debug("REST request to delete Users : {}", id);
        usersService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

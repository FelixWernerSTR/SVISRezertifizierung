package de.svi.svis5g.aktuelles.web.rest;

import de.svi.svis5g.aktuelles.domain.Thementyp;
import de.svi.svis5g.aktuelles.repository.ThementypRepository;
import de.svi.svis5g.aktuelles.service.ThementypService;
import de.svi.svis5g.aktuelles.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link de.svi.svis5g.aktuelles.domain.Thementyp}.
 */
@RestController
@RequestMapping("/api")
public class ThementypResource {

    private final Logger log = LoggerFactory.getLogger(ThementypResource.class);

    private static final String ENTITY_NAME = "thementyp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThementypService thementypService;

    private final ThementypRepository thementypRepository;

    public ThementypResource(ThementypService thementypService, ThementypRepository thementypRepository) {
        this.thementypService = thementypService;
        this.thementypRepository = thementypRepository;
    }

    /**
     * {@code POST  /thementyps} : Create a new thementyp.
     *
     * @param thementyp the thementyp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thementyp, or with status {@code 400 (Bad Request)} if the thementyp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/thementyps")
    public ResponseEntity<Thementyp> createThementyp(@Valid @RequestBody Thementyp thementyp) throws URISyntaxException {
        log.debug("REST request to save Thementyp : {}", thementyp);
        if (thementyp.getId() != null) {
            throw new BadRequestAlertException("A new thementyp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Thementyp result = thementypService.save(thementyp);
        return ResponseEntity
            .created(new URI("/api/thementyps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /thementyps/:id} : Updates an existing thementyp.
     *
     * @param id the id of the thementyp to save.
     * @param thementyp the thementyp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thementyp,
     * or with status {@code 400 (Bad Request)} if the thementyp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thementyp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/thementyps/{id}")
    public ResponseEntity<Thementyp> updateThementyp(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Thementyp thementyp
    ) throws URISyntaxException {
        log.debug("REST request to update Thementyp : {}, {}", id, thementyp);
        if (thementyp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thementyp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thementypRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Thementyp result = thementypService.update(thementyp);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thementyp.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /thementyps/:id} : Partial updates given fields of an existing thementyp, field will ignore if it is null
     *
     * @param id the id of the thementyp to save.
     * @param thementyp the thementyp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thementyp,
     * or with status {@code 400 (Bad Request)} if the thementyp is not valid,
     * or with status {@code 404 (Not Found)} if the thementyp is not found,
     * or with status {@code 500 (Internal Server Error)} if the thementyp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/thementyps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Thementyp> partialUpdateThementyp(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Thementyp thementyp
    ) throws URISyntaxException {
        log.debug("REST request to partial update Thementyp partially : {}, {}", id, thementyp);
        if (thementyp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thementyp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thementypRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Thementyp> result = thementypService.partialUpdate(thementyp);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thementyp.getId().toString())
        );
    }

    /**
     * {@code GET  /thementyps} : get all the thementyps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thementyps in body.
     */
    @GetMapping("/thementyps")
    public List<Thementyp> getAllThementyps() {
        log.debug("REST request to get all Thementyps");
        return thementypService.findAll();
    }

    /**
     * {@code GET  /thementyps/:id} : get the "id" thementyp.
     *
     * @param id the id of the thementyp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thementyp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/thementyps/{id}")
    public ResponseEntity<Thementyp> getThementyp(@PathVariable Long id) {
        log.debug("REST request to get Thementyp : {}", id);
        Optional<Thementyp> thementyp = thementypService.findOne(id);
        return ResponseUtil.wrapOrNotFound(thementyp);
    }

    /**
     * {@code DELETE  /thementyps/:id} : delete the "id" thementyp.
     *
     * @param id the id of the thementyp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/thementyps/{id}")
    public ResponseEntity<Void> deleteThementyp(@PathVariable Long id) {
        log.debug("REST request to delete Thementyp : {}", id);
        thementypService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

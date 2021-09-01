package de.svi.svis5g.rezertifizierung.web.rest;

import de.svi.svis5g.rezertifizierung.domain.Rezertifizierung;
import de.svi.svis5g.rezertifizierung.repository.RezertifizierungRepository;
import de.svi.svis5g.rezertifizierung.service.RezertifizierungService;
import de.svi.svis5g.rezertifizierung.web.rest.errors.BadRequestAlertException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link de.svi.svis5g.rezertifizierung.domain.Rezertifizierung}.
 */
@RestController
@RequestMapping("/api")
public class RezertifizierungResource {

    private final Logger log = LoggerFactory.getLogger(RezertifizierungResource.class);

    private static final String ENTITY_NAME = "rezertifizierung";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RezertifizierungService rezertifizierungService;

    private final RezertifizierungRepository rezertifizierungRepository;

    public RezertifizierungResource(
        RezertifizierungService rezertifizierungService,
        RezertifizierungRepository rezertifizierungRepository
    ) {
        this.rezertifizierungService = rezertifizierungService;
        this.rezertifizierungRepository = rezertifizierungRepository;
    }

    /**
     * {@code POST  /rezertifizierungs} : Create a new rezertifizierung.
     *
     * @param rezertifizierung the rezertifizierung to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rezertifizierung, or with status {@code 400 (Bad Request)} if the rezertifizierung has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rezertifizierungs")
    public ResponseEntity<Rezertifizierung> createRezertifizierung(@Valid @RequestBody Rezertifizierung rezertifizierung)
        throws URISyntaxException {
        log.debug("REST request to save Rezertifizierung : {}", rezertifizierung);
        if (rezertifizierung.getId() != null) {
            throw new BadRequestAlertException("A new rezertifizierung cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rezertifizierung result = rezertifizierungService.save(rezertifizierung);
        return ResponseEntity
            .created(new URI("/api/rezertifizierungs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rezertifizierungs/:id} : Updates an existing rezertifizierung.
     *
     * @param id the id of the rezertifizierung to save.
     * @param rezertifizierung the rezertifizierung to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rezertifizierung,
     * or with status {@code 400 (Bad Request)} if the rezertifizierung is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rezertifizierung couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rezertifizierungs/{id}")
    public ResponseEntity<Rezertifizierung> updateRezertifizierung(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Rezertifizierung rezertifizierung
    ) throws URISyntaxException {
        log.debug("REST request to update Rezertifizierung : {}, {}", id, rezertifizierung);
        if (rezertifizierung.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rezertifizierung.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rezertifizierungRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Rezertifizierung result = rezertifizierungService.save(rezertifizierung);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rezertifizierung.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rezertifizierungs/:id} : Partial updates given fields of an existing rezertifizierung, field will ignore if it is null
     *
     * @param id the id of the rezertifizierung to save.
     * @param rezertifizierung the rezertifizierung to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rezertifizierung,
     * or with status {@code 400 (Bad Request)} if the rezertifizierung is not valid,
     * or with status {@code 404 (Not Found)} if the rezertifizierung is not found,
     * or with status {@code 500 (Internal Server Error)} if the rezertifizierung couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rezertifizierungs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Rezertifizierung> partialUpdateRezertifizierung(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Rezertifizierung rezertifizierung
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rezertifizierung partially : {}, {}", id, rezertifizierung);
        if (rezertifizierung.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rezertifizierung.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rezertifizierungRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Rezertifizierung> result = rezertifizierungService.partialUpdate(rezertifizierung);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rezertifizierung.getId().toString())
        );
    }

    /**
     * {@code GET  /rezertifizierungs} : get all the rezertifizierungs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rezertifizierungs in body.
     */
    @GetMapping("/rezertifizierungs")
    public ResponseEntity<List<Rezertifizierung>> getAllRezertifizierungs(Pageable pageable) {
        log.debug("REST request to get a page of Rezertifizierungs");
        Page<Rezertifizierung> page = rezertifizierungService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rezertifizierungs/:id} : get the "id" rezertifizierung.
     *
     * @param id the id of the rezertifizierung to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rezertifizierung, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rezertifizierungs/{id}")
    public ResponseEntity<Rezertifizierung> getRezertifizierung(@PathVariable Long id) {
        log.debug("REST request to get Rezertifizierung : {}", id);
        Optional<Rezertifizierung> rezertifizierung = rezertifizierungService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rezertifizierung);
    }

    /**
     * {@code DELETE  /rezertifizierungs/:id} : delete the "id" rezertifizierung.
     *
     * @param id the id of the rezertifizierung to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rezertifizierungs/{id}")
    public ResponseEntity<Void> deleteRezertifizierung(@PathVariable Long id) {
        log.debug("REST request to delete Rezertifizierung : {}", id);
        rezertifizierungService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

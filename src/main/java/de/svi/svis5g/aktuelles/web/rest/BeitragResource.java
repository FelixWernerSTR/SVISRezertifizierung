package de.svi.svis5g.aktuelles.web.rest;

import de.svi.svis5g.aktuelles.domain.Beitrag;
import de.svi.svis5g.aktuelles.repository.BeitragRepository;
import de.svi.svis5g.aktuelles.service.BeitragService;
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
 * REST controller for managing {@link de.svi.svis5g.aktuelles.domain.Beitrag}.
 */
@RestController
@RequestMapping("/api")
public class BeitragResource {

    private final Logger log = LoggerFactory.getLogger(BeitragResource.class);

    private static final String ENTITY_NAME = "beitrag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeitragService beitragService;

    private final BeitragRepository beitragRepository;

    public BeitragResource(BeitragService beitragService, BeitragRepository beitragRepository) {
        this.beitragService = beitragService;
        this.beitragRepository = beitragRepository;
    }

    /**
     * {@code POST  /beitrags} : Create a new beitrag.
     *
     * @param beitrag the beitrag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beitrag, or with status {@code 400 (Bad Request)} if the beitrag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/beitrags")
    public ResponseEntity<Beitrag> createBeitrag(@Valid @RequestBody Beitrag beitrag) throws URISyntaxException {
        log.debug("REST request to save Beitrag : {}", beitrag);
        if (beitrag.getId() != null) {
            throw new BadRequestAlertException("A new beitrag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Beitrag result = beitragService.save(beitrag);
        return ResponseEntity
            .created(new URI("/api/beitrags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /beitrags/:id} : Updates an existing beitrag.
     *
     * @param id the id of the beitrag to save.
     * @param beitrag the beitrag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beitrag,
     * or with status {@code 400 (Bad Request)} if the beitrag is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beitrag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/beitrags/{id}")
    public ResponseEntity<Beitrag> updateBeitrag(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Beitrag beitrag
    ) throws URISyntaxException {
        log.debug("REST request to update Beitrag : {}, {}", id, beitrag);
        if (beitrag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beitrag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beitragRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Beitrag result = beitragService.update(beitrag);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, beitrag.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /beitrags/:id} : Partial updates given fields of an existing beitrag, field will ignore if it is null
     *
     * @param id the id of the beitrag to save.
     * @param beitrag the beitrag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beitrag,
     * or with status {@code 400 (Bad Request)} if the beitrag is not valid,
     * or with status {@code 404 (Not Found)} if the beitrag is not found,
     * or with status {@code 500 (Internal Server Error)} if the beitrag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/beitrags/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Beitrag> partialUpdateBeitrag(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Beitrag beitrag
    ) throws URISyntaxException {
        log.debug("REST request to partial update Beitrag partially : {}, {}", id, beitrag);
        if (beitrag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beitrag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beitragRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Beitrag> result = beitragService.partialUpdate(beitrag);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, beitrag.getId().toString())
        );
    }

    /**
     * {@code GET  /beitrags} : get all the beitrags.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beitrags in body.
     */
    @GetMapping("/beitrags")
    public ResponseEntity<List<Beitrag>> getAllBeitrags(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Beitrags");
        Page<Beitrag> page;
        if (eagerload) {
            page = beitragService.findAllWithEagerRelationships(pageable);
        } else {
            page = beitragService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /beitrags/:id} : get the "id" beitrag.
     *
     * @param id the id of the beitrag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beitrag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/beitrags/{id}")
    public ResponseEntity<Beitrag> getBeitrag(@PathVariable Long id) {
        log.debug("REST request to get Beitrag : {}", id);
        Optional<Beitrag> beitrag = beitragService.findOne(id);
        return ResponseUtil.wrapOrNotFound(beitrag);
    }

    /**
     * {@code DELETE  /beitrags/:id} : delete the "id" beitrag.
     *
     * @param id the id of the beitrag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/beitrags/{id}")
    public ResponseEntity<Void> deleteBeitrag(@PathVariable Long id) {
        log.debug("REST request to delete Beitrag : {}", id);
        beitragService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

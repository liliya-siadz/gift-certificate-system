package com.epam.esm.controller;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for processing REST-api requests for Gift Certificate resource .
 * <p>
 * Works with relative to application context '/gift_certificates'.
 * <p>
 * Maps GET, PUT, POST, DELETE http-requests.
 * As a client model uses object of class
 * {@link GiftCertificateClientModel} .
 */
@RestController
@RequestMapping("/gift_certificates")
public class GiftCertificateController {

    /**
     * Service for working with resource Gift Certificate .
     */
    private final GiftCertificateService service;

    /**
     * Constructs controller with injected param .
     *
     * @param service {@link GiftCertificateController#service}
     */
    @Autowired
    public GiftCertificateController(GiftCertificateService service) {
        this.service = service;
    }

    /**
     * Creates resource Gift Certificate.
     * <p>
     * Handles POST http-request.
     *
     * @param certificate Gift Certificate to create
     * @return Gift Certificate that was created
     */
    @PostMapping
    public GiftCertificateClientModel create(@RequestBody GiftCertificateClientModel certificate) {
        return service.create(certificate);
    }

    /**
     * Gets all Gift Certificate resources.
     * <p>
     * Handles GET http-request.
     *
     * @return list of all found Gift Certificates
     */
    @GetMapping
    public List<GiftCertificateClientModel> getAll() {
        return service.findAll();
    }

    /**
     * Gets resource Gift Certificate with requested id.
     * <p>
     * Handles GET http-request.
     *
     * @param id id of Gift Certificate to find
     * @return Gift Certificate that was found
     */
    @GetMapping("/{id}")
    public GiftCertificateClientModel getById(@PathVariable Long id) {
        return service.findById(id);
    }

    /**
     * Deletes resource Gift Certificate with requested id.
     * <p>
     * Handles DELETE http-request.
     *
     * @param id id of Gift Certificate to delete
     * @return Gift Certificate that was deleted
     */
    @DeleteMapping("/{id}")
    public GiftCertificateClientModel deleteById(@PathVariable Long id) {
        return service.delete(id);
    }

    /**
     * Updates resource Gift Certificate with specified id,
     * updates only fields that were passed in the request .
     * <p>
     * Handles PATCH http-request.
     *
     * @param id          id of Gift Certificate to update
     * @param certificate Gift Certificate client model, that contains params for update
     * @return Gift Certificate with updated and actual values
     */
    @PatchMapping("/{id}")
    public GiftCertificateClientModel update(@PathVariable Long id,
                                             @RequestBody GiftCertificateClientModel certificate) {
        return service.update(id, certificate);
    }

    /**
     * Searches (also could sort) Gift Certificate resources
     * that fits to specified params of search .
     *
     * @param tagName       full name of Tag that bound to target Gift Certificate
     * @param name          part of name of target Gift Certificate
     * @param description   part of description of target Gift Certificate
     * @param sortField     property of sorting Gift Certificate
     * @param sortDirection direction of sorting Gift Certificates
     * @return found and sorted list of Gift Certificates
     * see {@link com.epam.esm.service.GiftCertificateService#search}
     */
    @GetMapping("/search")
    public List<GiftCertificateClientModel> search(
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection) {
        return service.search(tagName, name, description, sortField, sortDirection);
    }
}

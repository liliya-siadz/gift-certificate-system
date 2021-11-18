package com.epam.esm.controller;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.group.CreateChecks;
import com.epam.esm.validator.group.IdChecks;
import com.epam.esm.validator.group.UpdateChecks;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

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
@Validated
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
    public GiftCertificateClientModel create(
            @RequestBody @Validated({IdChecks.class, CreateChecks.class})
                    GiftCertificateClientModel certificate) {
        return service.create(certificate);
    }

    /**
     * Gets all Gift Certificate resources of passed quantity from passed page .
     * <p>
     * Handles GET http-request.
     *
     * @param pageNumber page number to get Gift Certificates from
     * @param pageSize   quantity of Gift Certificates on page (page size)
     * @return page of Tag resources of passed quantity
     */
    @GetMapping
    public PageableClientModel<GiftCertificateClientModel> getAll(
            @RequestParam(required = false, defaultValue = "5") @Min(1) Integer pageSize,
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer pageNumber) {
        return service.findAll(pageSize, pageNumber);
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
    public GiftCertificateClientModel getById(@PathVariable @Range(min = 1, max = 2147483647) Long id) {
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
    public GiftCertificateClientModel deleteById(@PathVariable @Range(min = 1, max = 2147483647) Long id) {
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
    public GiftCertificateClientModel update(@PathVariable @Range(min = 1, max = 2147483647) Long id,
                                             @RequestBody @Validated({IdChecks.class, UpdateChecks.class})
                                                     GiftCertificateClientModel certificate) {
        return service.update(id, certificate);
    }

    /**
     * Searches (also could sort) passed quantity of Gift Certificate resources
     * that fits to specified params of search from passed page .
     *
     * @param tagName       full name of Tag that bound to target Gift Certificate
     * @param name          part of name of target Gift Certificate
     * @param description   part of description of target Gift Certificate
     * @param sortField     property of sorting Gift Certificate
     * @param sortDirection direction of sorting Gift Certificates
     * @param pageNumber page number to get Gift Certificates from
     * @param pageSize   quantity of Gift Certificates on page (page size)
     * @return fixed size page of search result
     * see {@link com.epam.esm.service.GiftCertificateService#search}
     */
    @GetMapping("/search")
    public PageableClientModel<GiftCertificateClientModel> search(
            @RequestParam(required = false) @Length(min = 1, max = 200) @Pattern(regexp = ".*[a-zA-Z]+.*")
                    String tagName,
            @RequestParam(required = false) @Length(min = 1, max = 200) @Pattern(regexp = ".*[a-zA-Z]+.*")
                    String name,
            @RequestParam(required = false) @Length(min = 1, max = 2000) @Pattern(regexp = ".*[a-zA-Z]+.*")
                    String description,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection,
            @RequestParam(required = false, defaultValue = "5") @Min(1) Integer pageSize,
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer pageNumber) {
        return service.search(tagName, name, description, sortField, sortDirection, pageSize, pageNumber);
    }
}

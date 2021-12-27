package com.epam.esm.controller.controller;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.group.CreateChecks;
import com.epam.esm.validator.group.IdChecks;
import com.epam.esm.validator.group.UpdateChecks;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * Controller for processing REST-api requests for Gift Certificate resource .
 * <p>
 * Works with relative to application context '/gift_certificates'.
 * <p>
 * Maps GET, PATCH, POST, DELETE http-requests.
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
     * Constructs controller with injected Gift Certificate service .
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
    @PreAuthorize("hasAuthority('gift_certificates:create')")
    public GiftCertificateClientModel create(
            @RequestBody @Validated({IdChecks.class, CreateChecks.class}) GiftCertificateClientModel certificate) {
        return service.create(certificate);
    }

    /**
     * Gets all Gift Certificates resources from requested page with requested page size .
     * <p>
     * Handles GET http-request.
     *
     * @param pageNumber page number to get Users from
     * @param pageSize   quantity of Users on page (page size)
     * @return page of  Gift Certificate resources of passed quantity
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
    @PreAuthorize("hasAuthority('gift_certificates:delete')")
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
    @PreAuthorize("hasAuthority('gift_certificates:update')")
    public GiftCertificateClientModel update(@PathVariable @Range(min = 1, max = 2147483647) Long id,
                                             @RequestBody @Validated({IdChecks.class, UpdateChecks.class})
                                                     GiftCertificateClientModel certificate) {
        return service.update(id, certificate);
    }

    /**
     * Updates price of Gift Certificate resource with specified id .
     * <p>
     * Handles PATCH http-request.
     *
     * @param id    id of Gift Certificate to update
     * @param price new value for Gift Certificate price
     * @return Gift Certificate with updated price
     */
    @PatchMapping("/update-price/{id}")
    @PreAuthorize("hasAuthority('gift_certificates:update')")
    public GiftCertificateClientModel updatePrice(@PathVariable @Range(min = 1, max = 2147483647) Long id,
                                                  @RequestParam @NotNull @Positive BigDecimal price) {
        return service.updatePrice(id, price);
    }

    /**
     * Searches (also could sort) Gift Certificates resources that fit to passed parameters .
     *
     * @param tagNames      full name of Tag that bounds to target Gift Certificate
     * @param name          part of name of target Gift Certificate
     * @param description   part of description of target Gift Certificate
     * @param sortField     property of sorting Gift Certificate
     * @param sortDirection direction of sorting Gift Certificates
     * @param pageNumber    page number of found result of Users
     * @param pageSize      quantity of Users on a page (page size)
     * @return one page of found Gift Certificates resources
     * see {@link com.epam.esm.service.GiftCertificateService#search}
     */
    @GetMapping("/search")
    public PageableClientModel<GiftCertificateClientModel> search(
            @RequestParam(required = false) List<@Pattern(regexp = ".*[a-zA-Z]+.*") String> tagNames,
            @RequestParam(required = false) @Length(min = 1, max = 200)
            @Pattern(regexp = ".*[a-zA-Z]+.*") String name,
            @RequestParam(required = false) @Length(min = 1, max = 2000)
            @Pattern(regexp = ".*[a-zA-Z]+.*") String description,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection,
            @RequestParam(required = false, defaultValue = "5") @Min(1) Integer pageSize,
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer pageNumber) {
        return service.search(tagNames, name, description, sortField, sortDirection, pageSize, pageNumber);
    }
}

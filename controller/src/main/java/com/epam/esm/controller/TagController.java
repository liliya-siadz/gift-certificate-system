package com.epam.esm.controller;

import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.validator.group.CreateChecks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Controller for processing REST-api requests for Tag resource .
 * <p>
 * Works with relative to application context '/tags'.
 * <p>
 * Maps GET, POST, DELETE http-requests.
 * As a client model uses object of class
 * {@link TagClientModel} .
 */
@RestController
@Validated
@RequestMapping("/tags")
public class TagController {

    /**
     * Service for Tag resource .
     */
    private final TagServiceImpl service;

    /**
     * Constructs controller with injected Tag service .
     *
     * @param service {@link TagController#service}
     */
    @Autowired
    public TagController(TagServiceImpl service) {
        this.service = service;
    }

    /**
     * Gets all Tag resources from target page with requested page size .
     * <p>
     * Handles GET http-request.
     *
     * @param pageNumber page number to get Tags from
     * @param pageSize   quantity of Tags on page (page size)
     * @return page of Tag resources of passed quantity
     */
    @GetMapping
    public PageableClientModel<TagClientModel> getAll(
            @RequestParam(required = false, defaultValue = "5") @Min(1) Integer pageSize,
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer pageNumber) {
        return service.findAll(pageSize, pageNumber);
    }

    /**
     * Gets resource Tag with requested id .
     * <p>
     * Handles GET http-request.
     *
     * @param id id of requested Tag to find
     * @return Tag that was found
     */
    @GetMapping("/{id}")
    public TagClientModel getById(@PathVariable @Positive Long id) {
        return service.findById(id);
    }

    /**
     * Creates resource Tag .
     * <p>
     * Handles POST http-request.
     *
     * @param tag Tag to create
     * @return Tag that was created
     */
    @PostMapping
    public TagClientModel create(@RequestBody @Validated(CreateChecks.class) TagClientModel tag) {
        return service.create(tag);
    }

    /**
     * Deletes resource Tag with requested id .
     * <p>
     * Handles DELETE http-request.
     *
     * @param id id of Tag to delete
     * @return Tag that was deleted
     */
    @DeleteMapping("/{id}")
    public TagClientModel deleteById(@PathVariable @NotNull @Positive Long id) {
        return service.delete(id);
    }

    /**
     * Finds top Tag resource of Top Users,
     * i.e. gets the most widely used Tag of a User with the highest cost of all Orders .
     * <p>
     * Handles GET http-request.
     *
     * @return most widely used Tag of a User with the highest cost of all Orders if present,
     * otherwise null
     */
    @GetMapping("/top-tag")
    public TagClientModel findTopTag() {
        return service.findTopTag();
    }
}

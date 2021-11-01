package com.epam.esm.controller;

import com.epam.esm.model.TagClientModel;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for processing REST-api requests for Tag resource .
 * <p>
 * Works with relative to application context '/tags'.
 * <p>
 * Maps GET, POST, DELETE http-requests.
 * As a client model uses object of class
 * {@link com.epam.esm.model.TagClientModel} .
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    /**
     * Service for Tag resource .
     */
    private final TagServiceImpl service;

    /**
     * Constructs controller with injected param .
     *
     * @param service {@link TagController#service}
     */
    @Autowired
    public TagController(TagServiceImpl service) {
        this.service = service;
    }

    /**
     * Gets all Tag resources.
     * <p>
     * Handles GET http-request.
     *
     * @return list of all found Tags
     */
    @GetMapping
    public List<TagClientModel> getAll() {
        return service.findAll();
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
    public TagClientModel getById(@PathVariable(value = "id") Long id) {
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
    public TagClientModel create(@RequestBody TagClientModel tag) {
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
    public TagClientModel deleteById(@PathVariable Long id) {
        return service.delete(id);
    }
}

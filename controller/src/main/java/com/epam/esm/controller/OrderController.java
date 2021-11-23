package com.epam.esm.controller;

import com.epam.esm.clientmodel.OrderClientModel;
import com.epam.esm.service.OrderService;
import com.epam.esm.validator.group.OrderChecks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

/**
 * Controller for processing REST-api requests for Order resource .
 * <p>
 * Works with relative to application context '/orders'.
 * <p>
 * Maps GET http-requests.
 * As a client model uses object of class
 * {@link OrderClientModel} .
 */
@RestController
@Validated
@RequestMapping("/orders")
public class OrderController {

    /**
     * Service for working with resource Order .
     */
    private final OrderService service;

    /**
     * Constructs controller with injected Order service .
     *
     * @param service {@link OrderController#service}
     */
    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    /**
     * Creates resource Order.
     * <p>
     * Handles POST http-request.
     *
     * @param order Order to create
     * @return Order that was created
     */
    @PostMapping
    public OrderClientModel create(@RequestBody @Validated({OrderChecks.class}) OrderClientModel order) {
        return service.create(order);
    }

    /**
     * Gets resource Order with requested id .
     * <p>
     * Handles GET http-request.
     *
     * @param id id of requested Order to find
     * @return Order that was found
     */
    @GetMapping("/{id}")
    public OrderClientModel getById(@PathVariable @Positive Long id) {
        return service.findById(id);
    }
}

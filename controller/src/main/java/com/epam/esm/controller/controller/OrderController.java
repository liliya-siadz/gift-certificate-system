package com.epam.esm.controller.controller;

import com.epam.esm.clientmodel.OrderClientModel;
import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.service.OrderService;
import com.epam.esm.validator.group.OrderChecks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
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
     * Gets all Order resources from target page with requested page size .
     * <p>
     * Handles GET http-request.
     *
     * @param pageNumber page number to get Orders from
     * @param pageSize   quantity of Orders on page (page size)
     * @return page of Order resources of passed quantity
     */
    @GetMapping
    public PageableClientModel<OrderClientModel> getAll(
            @RequestParam(required = false, defaultValue = "5") @Min(1) Integer pageSize,
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer pageNumber) {
        return service.findAll(pageSize, pageNumber);
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
    public OrderClientModel create(
            @RequestBody @Validated({OrderChecks.class}) OrderClientModel order) {
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

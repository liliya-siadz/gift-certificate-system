package com.epam.esm.controller;

import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.clientmodel.ResponseOrderClientModel;
import com.epam.esm.clientmodel.UserClientModel;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Controller for processing REST-api requests for User resource .
 * <p>
 * Works with relative to application context '/users'.
 * <p>
 * Maps GET http-requests.
 * As a client model uses object of class
 * {@link UserClientModel} .
 */
@RestController
@Validated
@RequestMapping("/users")
public class UserController {

    /**
     * Service for Tag resources .
     */
    private final UserServiceImpl service;

    /**
     * Service for Order resources .
     */
    private final OrderServiceImpl orderService;

    /**
     * Constructs controller with injected User service .
     *
     * @param service {@link UserController#service}
     */
    @Autowired
    public UserController(UserServiceImpl service, OrderServiceImpl orderService) {
        this.service = service;
        this.orderService = orderService;
    }

    /**
     * Gets all User resources from requested page with requested page size .
     * <p>
     * Handles GET http-request.
     *
     * @param pageNumber page number to get Users from
     * @param pageSize   quantity of Users on page (page size)
     * @return page of Tag resources of passed quantity
     */
    @GetMapping
    public PageableClientModel<UserClientModel> getAll(
            @RequestParam(required = false, defaultValue = "5") @Min(1) Integer pageSize,
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer pageNumber) {
        return service.findAll(pageSize, pageNumber);
    }

    /**
     * Gets resource User with requested id .
     * <p>
     * Handles GET http-request.
     *
     * @param id id of requested User to find
     * @return User that was found
     */
    @GetMapping("/{id}")
    public UserClientModel getById(@PathVariable @Positive Long id) {
        return service.findById(id);
    }

    /**
     * Gets all Orders resources from target page with requested page size .
     * <p>
     * Handles GET http-request.
     *
     * @param id         id of user with target orders
     * @param pageNumber page number to get Orders from
     * @param pageSize   quantity of Orders on page (page size)
     * @return page of Orders resources for target User
     */
    @GetMapping("/{id}/orders")
    public PageableClientModel<ResponseOrderClientModel> getUserOrders(
            @PathVariable @NotNull @Positive Long id,
            @RequestParam(required = false, defaultValue = "5") @Min(1) Integer pageSize,
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer pageNumber) {
        return orderService.findUserOrders(id, pageSize, pageNumber);
    }

    /**
     * Gets Order of User by id of Order and id of User .
     * <p>
     * Handles GET http-request.
     *
     * @param userId  id of User with target Order
     * @param orderId id of target Order
     * @return Order that was found
     */
    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseOrderClientModel getUserOrderById(
            @PathVariable @NotNull @Positive Long userId,
            @PathVariable @NotNull @Positive Long orderId) {
        return orderService.findUserOrder(userId, orderId);
    }
}

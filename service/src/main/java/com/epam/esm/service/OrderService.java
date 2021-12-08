package com.epam.esm.service;

import com.epam.esm.clientmodel.OrderClientModel;
import com.epam.esm.clientmodel.PageableClientModel;

/**
 * Presents access to service operations with Order .
 */
public interface OrderService extends BaseService<OrderClientModel> {

    /**
     * Finds all Orders for target User by id of User .
     *
     * @param id         id of User which Orders must be found
     * @param pageNumber page number of found result of Users
     * @param pageSize   quantity of Users on a page (page size)
     * @return one page of found Users
     */
    PageableClientModel<OrderClientModel> findUserOrders(Long id, Integer pageSize, Integer pageNumber);

    /**
     * Finds Order of User by id of Order and id of User .
     *
     * @param userId  id of User with target Order
     * @param orderId id of target Order
     * @return client model of Order that was found
     */
    OrderClientModel findUserOrder(Long userId, Long orderId);

    /**
     * Creates Order from client model of Order .
     *
     * @param model Order model to create
     * @return model of Order that was created
     */
    OrderClientModel create(OrderClientModel model);
}

package com.epam.esm.service;

import com.epam.esm.clientmodel.RequestOrderClientModel;
import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.clientmodel.ResponseOrderClientModel;

/**
 * Presents access to service operations with Order .
 */
public interface OrderService extends BaseService<ResponseOrderClientModel> {

    /**
     * Finds all Orders for target User by id of User .
     *
     * @param id         id of User which Orders must be found
     * @param pageNumber page number of found result of Users
     * @param pageSize   quantity of Users on a page (page size)
     * @return one page of found Users
     */
    PageableClientModel<ResponseOrderClientModel> findUserOrders(Long id, Integer pageSize, Integer pageNumber);

    /**
     * Finds Order of User by id of Order and id of User .
     *
     * @param userId  id of User with target Order
     * @param orderId id of target Order
     * @return client model of Order that was found
     */
    ResponseOrderClientModel findUserOrder(Long userId, Long orderId);

    /**
     * Creates Order from request client model of Order
     * and returns response client model of Order .
     *
     * @param model Order model to create
     * @return model of Order that was created
     */
    ResponseOrderClientModel create(RequestOrderClientModel model);
}

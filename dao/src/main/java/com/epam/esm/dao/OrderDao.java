package com.epam.esm.dao;

import com.epam.esm.entity.OrderEntity;
import com.epam.esm.entity.PageableEntity;

/**
 * Presents access to repository operations with Order .
 */
public interface OrderDao extends Dao<OrderEntity> {

    /**
     * Finds all Orders entities for target User by id of User .
     *
     * @param id         id of User which Orders must be found
     * @param pageNumber page number of found result of Users
     * @param pageSize   quantity of Users on a page (page size)
     * @return one page of found entity of Users
     */
    PageableEntity<OrderEntity> findUserOrders(long id, int pageSize, int pageNumber);

    /**
     * Finds entity Order of User by id of Order and id of User .
     *
     * @param userId id of User with target Order
     * @param orderId id of target Order
     * @return entity of Order that was found
     */
    OrderEntity findUserOrder(long userId, long orderId);
}

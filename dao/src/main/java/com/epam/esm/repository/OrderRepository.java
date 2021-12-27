package com.epam.esm.repository;

import com.epam.esm.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Presents access to repository operations with Order .
 */
public interface OrderRepository extends Repository<OrderEntity> {
    /**
     * Finds Orders by id of User .
     *
     * @param userId   id of User that Orders have
     * @param pageable target page of result
     * @return page of result of finding
     */
    Page<OrderEntity> findByUserId(Long userId, Pageable pageable);

    @Override
    default Class<OrderEntity> getEntityClass() {
        return OrderEntity.class;
    }
}

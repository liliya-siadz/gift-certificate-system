package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.builder.OrderQueryBuilder;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.entity.PageableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Implementation of interface {@link OrderDao}
 * for presenting access to repository operations with Order .
 */
@Repository
public class OrderDaoImpl extends AbstractDao<OrderEntity> implements OrderDao {

    /**
     * Query builder for criteria queries .
     */
    @Autowired
    private OrderQueryBuilder queryBuilder;

    /**
     * Constructs class <code>OrderDaoImpl</code> with passed query builder .
     *
     * @param queryBuilder {@link #queryBuilder}
     */
    public OrderDaoImpl(OrderQueryBuilder queryBuilder) {
        super(queryBuilder);
    }

    @Override
    public Class<OrderEntity> getEntityClass() {
        return OrderEntity.class;
    }

    @Override
    public String[] getPrimaryKeyAttributeName() {
        return new String[]{"id"};
    }

    @Override
    public PageableEntity<OrderEntity> findUserOrders(long id, int pageSize, int pageNumber) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> criteriaQuery = criteriaBuilder.createQuery(OrderEntity.class);
        Root<OrderEntity> order = criteriaQuery.from(OrderEntity.class);
        criteriaQuery.select(order).where(criteriaBuilder.equal(order.get("userId"), id))
                .orderBy(criteriaBuilder.asc(order.get("id")));
        return runTypedQuery(entityManager.createQuery(criteriaQuery), pageSize, pageNumber, countUserOrders(id));
    }

    @Override
    public OrderEntity findUserOrder(long userId, long orderId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> criteriaQuery = criteriaBuilder.createQuery(OrderEntity.class);
        Root<OrderEntity> order = criteriaQuery.from(OrderEntity.class);
        criteriaQuery.select(order).where(criteriaBuilder.equal(order.get("userId"), userId))
                .where(criteriaBuilder.equal(order.get("id"), orderId));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    private long countUserOrders(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<OrderEntity> order = criteriaQuery.from(OrderEntity.class);
        criteriaQuery.select(criteriaBuilder.count(order))
                .where(criteriaBuilder.equal(order.get("userId"), id));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}

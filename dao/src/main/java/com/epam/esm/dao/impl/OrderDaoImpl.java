package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.builder.OrderQueryBuilder;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.entity.PageableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Implementation of interface {@link OrderDao}
 * for presenting access to repository operations with Order .
 */
@Repository
public class OrderDaoImpl extends AbstractDao<OrderEntity> implements OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Query builder for criteria queries .
     */
    @Autowired
    private OrderQueryBuilder queryBuilder;

    private CriteriaBuilder criteriaBuilder;

    /**
     * Constructs class <code>OrderDaoImpl</code>
     * with entity manager and criteria query builder .
     *
     * @param entityManager {@link #entityManager}
     * @param queryBuilder  {@link #queryBuilder}
     */
    public OrderDaoImpl(EntityManager entityManager, OrderQueryBuilder queryBuilder) {
        super(entityManager, queryBuilder);
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
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
        CriteriaQuery<OrderEntity> criteriaQuery = criteriaBuilder.createQuery(OrderEntity.class);
        Root<OrderEntity> order = criteriaQuery.from(OrderEntity.class);
        criteriaQuery.select(order).where(criteriaBuilder.equal(order.get("user").get("id"), id))
                .orderBy(criteriaBuilder.asc(order.get("id")));
        return runTypedQuery(entityManager.createQuery(criteriaQuery), pageSize, pageNumber, countUserOrders(id));
    }

    @Override
    public OrderEntity findUserOrder(long userId, long orderId) {
        CriteriaQuery<OrderEntity> criteriaQuery = criteriaBuilder.createQuery(OrderEntity.class);
        Root<OrderEntity> order = criteriaQuery.from(OrderEntity.class);
        criteriaQuery.select(order).where(criteriaBuilder.equal(order.get("user").get("id"), userId))
                .where(criteriaBuilder.equal(order.get("id"), orderId));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    private long countUserOrders(long id) {
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<OrderEntity> order = criteriaQuery.from(OrderEntity.class);
        criteriaQuery.select(criteriaBuilder.count(order))
                .where(criteriaBuilder.equal(order.get("user").get("id"), id));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}
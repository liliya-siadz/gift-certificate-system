package com.epam.esm.dao;

import com.epam.esm.dao.builder.QueryBuilder;
import com.epam.esm.entity.PageableEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Base abstract class for classes in package {@link com.epam.esm.dao.impl} .
 *
 * @param <T> type of entity that used in class
 */
@Repository
public abstract class AbstractDao<T> implements Dao<T> {

    /**
     * Entity manager for using and operation entities .
     */
    @PersistenceContext
    private final EntityManager entityManager;

    /**
     * Query builder for criteria queries .
     */
    private final QueryBuilder<T> queryBuilder;

    /**
     * Constructs class <code>AbstractDao</code>
     * with passed entity manager .
     *
     * @param entityManager {@link #entityManager}
     */
    public AbstractDao(EntityManager entityManager, QueryBuilder<T> queryBuilder) {
        this.entityManager = entityManager;
        this.queryBuilder = queryBuilder;
    }

    @Override
    public Optional<T> findById(long id) {
        return Optional.ofNullable(entityManager.find(getEntityClass(), id));
    }

    @Override
    public PageableEntity<T> findAll(int pageSize, int pageNumber) {
        CriteriaQuery<T> getAllOrderedByPkQuery = queryBuilder.buildGetAllOrderedByPkQuery(
                entityManager, getEntityClass(), getPrimaryKeyAttributeName());
        TypedQuery<T> typedQuery = entityManager.createQuery(getAllOrderedByPkQuery);
        return queryBuilder.buildPageFromQuery(typedQuery, pageSize, pageNumber, countAll());
    }

    @Override
    @Transactional
    public T create(T entity) {
        entityManager.persist(entity);
        Object id = retrieveEntityId(entity);
        return entityManager.find(getEntityClass(), id);
    }

    @Override
    @Transactional
    public void delete(long id) {
        T entity = entityManager.find(getEntityClass(), id);
        entityManager.remove(entity);
    }

    @Override
    public boolean isExist(long id) {
        return findById(id).isPresent();
    }

    @Override
    public Long countAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countAllQuery = criteriaBuilder.createQuery(Long.class);
        countAllQuery.select(criteriaBuilder.count(countAllQuery.from(getEntityClass())));
        return entityManager.createQuery(countAllQuery).getSingleResult();
    }

    @Override
    public abstract Class<T> getEntityClass();

    @Override
    public abstract String[] getPrimaryKeyAttributeName();

    /**
     * Retrieves id of entity .
     *
     * @param entity entity to extract id from
     * @return extracted id of entity
     */
    protected Object retrieveEntityId(T entity) {
        return entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
    }
}

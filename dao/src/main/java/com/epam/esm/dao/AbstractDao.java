package com.epam.esm.dao;

import com.epam.esm.dao.builder.QueryBuilder;
import com.epam.esm.entity.PageableEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
    protected EntityManager entityManager;

    /**
     * Query builder for criteria queries .
     */
    private QueryBuilder<T> queryBuilder;

    /**
     * Constructs class <code>AbstractDao</code> with passed query builder .
     *
     * @param queryBuilder {@link #queryBuilder}
     */
    public AbstractDao(QueryBuilder<T> queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    @Override
    public Optional<T> findById(long id) {
        return Optional.ofNullable(entityManager.find(getEntityClass(), id));
    }

    @Override
    public PageableEntity<T> findAll(int pageSize, int pageNumber) {
        CriteriaQuery<T> getAllOrderedByPkQuery =
                queryBuilder.buildGetAllOrderedByPkQuery(entityManager.getCriteriaBuilder(),
                        getEntityClass(), getPrimaryKeyAttributeName());
        return runCriteriaQuery(getAllOrderedByPkQuery, pageSize, pageNumber);
    }

    @Override
    @Transactional
    public T create(T entity) {
        entityManager.persist(entity);
        Object id = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
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
    public abstract Class<T> getEntityClass();

    @Override
    public abstract String[] getPrimaryKeyAttributeName();

    protected PageableEntity<T> runTypedQuery(TypedQuery<T> typedQuery, int pageSize, int pageNumber,
                                              long totalEntities) {
        PageableEntity<T> pageableEntity = queryBuilder.buildPage(pageSize, pageNumber, totalEntities);
        typedQuery.setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize);
        pageableEntity.setElements(typedQuery.getResultList());
        return pageableEntity;
    }

    protected PageableEntity<T> runCriteriaQuery(CriteriaQuery<T> criteriaQuery, int pageSize, int pageNumber) {
        TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
        TypedQuery<T> temp = entityManager.createQuery(criteriaQuery);
        long totalSearchedEntities = temp.getResultList().size();
        return runTypedQuery(typedQuery, pageSize, pageNumber, totalSearchedEntities);
    }
}

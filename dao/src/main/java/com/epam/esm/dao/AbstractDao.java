package com.epam.esm.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Base abstract class for classes in package {@link com.epam.esm.dao.impl} .
 *
 * @param <T> type of entity that used in class
 */
public abstract class AbstractDao<T> implements Dao<T> {

    /**
     * Entity manager for using and operation entities .
     */
    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * Builder for creating queries for data access .
     */
    protected CriteriaBuilder criteriaBuilder;

    /**
     * Constructs class <code>AbstractDao</code>
     * with passed entity manager .
     *
     * @param entityManager {@link #entityManager}
     */
    public AbstractDao(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public Optional<T> findById(long id) {
        return Optional.ofNullable(entityManager.find(retrieveEntityClass(), id));
    }

    @Override
    public Set<T> findAll() {
        CriteriaQuery<T> query = criteriaBuilder.createQuery(retrieveEntityClass());
        Root<T> root = query.from(retrieveEntityClass());
        query.select(root);
        return entityManager.createQuery(query).getResultStream().collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public T create(T entity) {
        entityManager.persist(entity);
        Object id = retrieveEntityId(entity);
        return entityManager.find(retrieveEntityClass(), id);
    }

    @Override
    @Transactional
    public void delete(long id) {
        T entity = entityManager.find(retrieveEntityClass(), id);
        entityManager.remove(entity);
    }

    @Override
    public boolean isExist(long id) {
        return findById(id).isPresent();
    }

    @Override
    public abstract Class<T> retrieveEntityClass();

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

package com.epam.esm.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
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
     * Constructs class <code>AbstractDao</code>
     * with passed entity manager .
     *
     * @param entityManager {@link #entityManager}
     */
    public AbstractDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<T> findById(long id) {
        return Optional.ofNullable(entityManager.find(getEntityClass(), id));
    }

    @Override
    public List<T> findAll() {
        CriteriaQuery<T> query = entityManager.getCriteriaBuilder().createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root);
        return entityManager.createQuery(query).getResultList();
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
    public abstract Class<T> getEntityClass();

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

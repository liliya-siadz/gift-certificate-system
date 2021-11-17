package com.epam.esm.dao;

/**
 * Presents access to repository operations with update operation with entity .
 */
public interface UpdatableDao<T> extends Dao<T> {

    /**
     * Updates entity with passed id by values from passed entity .
     *
     * @param entity entity with new values for target Gift Certificate
     */
    void update(T entity);
}

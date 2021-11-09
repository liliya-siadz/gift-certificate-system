package com.epam.esm.dao;

import java.util.Optional;
import java.util.Set;

/**
 * Presents access to repository operations with entity .
 */
public interface Dao<T> {

    /**
     * Finds entity with specified id .
     *
     * @param id id of entity
     * @return {@code Optional.of(T)} if entity was found,
     * otherwise {@code Optional.empty()}
     */
    Optional<T> findById(long id);

    /**
     * Finds all entities .
     *
     * @return set of all Tags entities
     */
    Set<T> findAll();

    /**
     * Creates new entity .
     *
     * @param entity entity model that contains params for entity creation
     * @return created entity
     */
    T create(T entity);

    /**
     * Deletes entity with specified id .
     *
     * @param id id of entity to delete
     */
    void delete(long id);

    /**
     * Checks if entity with specified id exists .
     *
     * @param id id of entity to check
     * @return true if entity is exist, otherwise - false
     */
    boolean isExist(long id);

    /**
     * Retrieves class of entity that implements interface .
     *
     * @return class of entity that implements interface
     */
    Class<T> retrieveEntityClass();
}

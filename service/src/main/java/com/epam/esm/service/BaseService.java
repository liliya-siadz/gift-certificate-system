package com.epam.esm.service;

import com.epam.esm.model.AbstractModel;

import java.util.Set;

/**
 * Presents access to service operations with model .
 */
public interface BaseService<S extends AbstractModel> {

    /**
     * Creates passed model .
     *
     * @param model model to create
     * @return client model
     */
    S create(S model);

    /**
     * Find all models .
     *
     * @return list of client models
     */
    Set<S> findAll();

    /**
     * Finds models with specified id .
     *
     * @param id id of model to find
     * @return client model of found Tag
     */
    S findById(Long id);

    /**
     * Deletes model with specified id .
     *
     * @param id id of model to delete
     * @return deleted client model
     */
    S delete(Long id);

    /**
     * Checks if model with specified id exists .
     *
     * @param id id of model to check
     * @return true if model is exist, otherwise - false
     */
    boolean isExist(Long id);
}

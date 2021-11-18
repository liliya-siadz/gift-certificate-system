package com.epam.esm.service;

import com.epam.esm.clientmodel.AbstractClientModel;
import com.epam.esm.clientmodel.PageableClientModel;

/**
 * Presents access to service operations with model .
 */
public interface BaseService<S extends AbstractClientModel> {

    /**
     * Creates passed model .
     *
     * @param model model to create
     * @return client model
     */
    S create(S model);

    /**
     * Finds all models of passed quantity from passed page.
     *
     * @return list of client models
     */
    PageableClientModel<S> findAll(Integer pageSize, Integer pageNumber);

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

    /**
     * Retrieves quantity of models .
     *
     * @return quantity of models
     */
    Long countAll();
}

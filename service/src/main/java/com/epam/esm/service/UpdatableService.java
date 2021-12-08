package com.epam.esm.service;

/**
 * Presents access to service operations with update operation with entity .
 */
public interface UpdatableService<S> extends BaseService<S> {

    /**
     * Updates model with specified id by passed
     * parameters in client model .
     *
     * @param id    id of model that should be updated
     * @param model client model to use params to update from
     * @return updated client model
     */
    S update(Long id, S model);
}

package com.epam.esm.preparator;

import com.epam.esm.clientmodel.AbstractClientModel;

/**
 * Preparator of client models for service operations .
 *
 * @param <S> type of client model,
 *           must extend {@link com.epam.esm.clientmodel.AbstractClientModel} class
 */
public abstract class Preparator<S extends AbstractClientModel> {

    /**
     * Prepare model for create operation .
     *
     * @param model model to create
     */
    public void prepareForCreate(S model) {
        model.setId(null);
    }

}

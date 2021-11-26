package com.epam.esm.preparator;

/**
 * Preparator of client models for service operations .
 *
 * @param <S> type of client model,
 */
public abstract class Preparator<S> {

    /**
     * Prepare model for create operation .
     *
     * @param model model to create
     */
    public abstract void prepareForCreate(S model);
}

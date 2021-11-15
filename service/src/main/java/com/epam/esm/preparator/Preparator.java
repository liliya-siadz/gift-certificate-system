package com.epam.esm.preparator;

import com.epam.esm.clientmodel.AbstractClientModel;
import com.epam.esm.validator.Validator;

/**
 * Preparator of client models for service operations,
 * for ex. for update (merge) or create operations .
 *
 * @param <S> type of client model,
 *           must extend {@link com.epam.esm.clientmodel.AbstractClientModel} class
 */
public abstract class Preparator<S extends AbstractClientModel> {

    /**
     * Client model validator .
     */
    private Validator<S> validator;

    /**
     * Constructs <code>Preparator</code> class
     * with passed validator .
     *
     * @param validator {@link #validator}
     */
    public Preparator(Validator<S> validator) {
        this.validator = validator;
    }

    /**
     * Prepare model for update (merge) operation .
     *
     * @param currentModelState current state of model (before update) to update
     * @param newModelState new state of model that contains new values
     */
    public abstract void prepareForMerge(S currentModelState, S newModelState);

    /**
     * Prepare model for create operation .
     *
     * @param model model to create
     */
    public void prepareForCreate(S model) {
        model.setId(null);
        validator.validateForCreate(model);
    }
}

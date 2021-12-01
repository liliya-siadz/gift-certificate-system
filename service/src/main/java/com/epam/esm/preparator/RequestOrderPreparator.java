package com.epam.esm.preparator;

import com.epam.esm.clientmodel.RequestOrderClientModel;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link Preparator} interface,
 * for preparing Order client models for service operations .
 */
@Component
public class RequestOrderPreparator extends Preparator<RequestOrderClientModel> {
    @Override
    public void prepareForCreate(RequestOrderClientModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Parameter 'model' is null.");
        }
        model.setId(null);
    }
}

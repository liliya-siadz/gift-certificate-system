package com.epam.esm.preparator;

import com.epam.esm.clientmodel.ResponseOrderClientModel;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link Preparator} interface,
 * for preparing Order client models for service operations .
 */
@Component
public class ResponseOrderPreparator extends Preparator<ResponseOrderClientModel> {
    @Override
    public void prepareForCreate(ResponseOrderClientModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Parameter 'model' is null.");
        }
        model.setId(null);
    }
}

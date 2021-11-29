package com.epam.esm.preparator;

import com.epam.esm.clientmodel.OrderClientModel;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link Preparator} interface,
 * for preparing Order client models for service operations .
 */
@Component
public class OrderPreparator extends Preparator<OrderClientModel> {
    @Override
    public void prepareForCreate(OrderClientModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Parameter 'model' is null.");
        }
        model.setId(null);
    }
}

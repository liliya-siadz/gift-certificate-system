package com.epam.esm.preparator;

import com.epam.esm.clientmodel.UserClientModel;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link Preparator} interface,
 * for preparing User client models for service operations .
 */
@Component
public class UserPreparator extends Preparator<UserClientModel> {
    @Override
    public void prepareForCreate(UserClientModel model) {
        model.setId(null);
    }
}

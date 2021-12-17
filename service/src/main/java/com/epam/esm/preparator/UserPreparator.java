package com.epam.esm.preparator;

import com.epam.esm.clientmodel.UserClientModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link Preparator} interface,
 * for preparing User client models for service operations .
 */
@Component
public class UserPreparator extends Preparator<UserClientModel> {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserPreparator(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void prepareForCreate(UserClientModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Parameter 'model' is null.");
        }
        model.setId(null);
        model.setRole("USER");
        model.setPassword(passwordEncoder.encode(model.getPassword()));
    }
}

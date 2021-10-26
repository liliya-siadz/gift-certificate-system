package com.epam.esm.validator;

import com.epam.esm.model.AbstractClientModel;

public interface Validator<T extends AbstractClientModel> {
    void isValidForUpdate(T clientModel);

    void isValidForCreate(T clientModel);
}

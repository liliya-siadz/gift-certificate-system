package com.epam.esm.service;

import com.epam.esm.model.ClientModel;

public interface TagService<T extends ClientModel> {
    T findById(long id);
}

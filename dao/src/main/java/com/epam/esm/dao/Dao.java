package com.epam.esm.dao;

import com.epam.esm.model.EntityModel;

public interface Dao<T extends EntityModel> {
    T findById(long id);
}

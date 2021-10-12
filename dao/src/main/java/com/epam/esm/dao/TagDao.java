package com.epam.esm.dao;

public interface TagDao<T> {
    T findById(long id);
}

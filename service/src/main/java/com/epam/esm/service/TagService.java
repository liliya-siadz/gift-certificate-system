package com.epam.esm.service;

public interface TagService<T> {
    T findById(long id);
}

package com.epam.esm.dao;

import com.epam.esm.model.TagEntityModel;

import java.util.List;

public interface TagDao {
    TagEntityModel findById(long id);

    List<TagEntityModel> findAll();

    long create(TagEntityModel entityModel);

    TagEntityModel delete(long id);
}

package com.epam.esm.dao;

import com.epam.esm.model.TagEntityModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagDao {
    TagEntityModel findById(long id);

    List<TagEntityModel> findAll();

    long create(TagEntityModel entityModel);

    boolean delete(long id);
}

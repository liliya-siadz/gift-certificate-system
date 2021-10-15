package com.epam.esm.service;

import com.epam.esm.model.TagClientModel;

import java.util.List;

public interface TagService {
    TagClientModel findById(Long id);
    TagClientModel delete(Long id);
    List<TagClientModel> findAll();
    TagClientModel create(TagClientModel tagClientModel);
}

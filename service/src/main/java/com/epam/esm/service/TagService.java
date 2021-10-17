package com.epam.esm.service;

import com.epam.esm.model.TagClientModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    TagClientModel findById(Long id);

    TagClientModel delete(Long id);

    List<TagClientModel> findAll();

    TagClientModel create(TagClientModel tagClientModel);
}

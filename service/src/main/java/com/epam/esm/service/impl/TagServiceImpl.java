package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.mapper.TagClientEntityModelMapper;
import com.epam.esm.model.TagClientModel;
import com.epam.esm.model.TagEntityModel;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    private final TagClientEntityModelMapper tagClientEntityModelMapper;

    @Autowired
    public TagServiceImpl(TagDao tagDao,
                          TagClientEntityModelMapper tagClientEntityModelMapper) {
        this.tagDao = tagDao;
        this.tagClientEntityModelMapper = tagClientEntityModelMapper;
    }

    @Override
    public TagClientModel findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of tag is null!");
        }
        TagEntityModel entityModel = tagDao.findById(id);
        return tagClientEntityModelMapper.entityToClient(entityModel);
    }

    @Override
    public TagClientModel delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of tag is null!");
        }
        TagEntityModel tagEntityModel = tagDao.delete(id);
        return tagClientEntityModelMapper.entityToClient(tagEntityModel);
    }

    @Override
    public List<TagClientModel> findAll() {
        return tagDao.findAll()
                .stream()
                .map(tagClientEntityModelMapper::entityToClient).
                collect(Collectors.toList());
    }

    @Override
    public TagClientModel create(TagClientModel clientModel) {
        if ((clientModel == null) || (clientModel.getName() == null)) {
            throw new IllegalArgumentException(
                    "Tag's model or tag's name is null!");
        }
        long tagGeneratedId = tagDao.create(
                tagClientEntityModelMapper.clientToEntity(clientModel));
        clientModel.setId(tagGeneratedId);
        return clientModel;
    }
}

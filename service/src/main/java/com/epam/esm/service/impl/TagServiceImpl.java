package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.exception.UnableToDeleteEntityException;
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

    @Autowired
    private TagDao tagDao;

    @Autowired
    private TagClientEntityModelMapper tagClientEntityModelMapper;

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
        if (tagDao.delete(id)) {
            return findById(id);
        } else {
            throw new UnableToDeleteEntityException("Unable to delete tag with id = " + id);
        }
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
            throw new IllegalArgumentException("Tag's model or tag's name is null!");
        }
        long tagGeneratedId = tagDao.create(tagClientEntityModelMapper.clientToEntity(clientModel));
        clientModel.setId(tagGeneratedId);
        return clientModel;
    }
}

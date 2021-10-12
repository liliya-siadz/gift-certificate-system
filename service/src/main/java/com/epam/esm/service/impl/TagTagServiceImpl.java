package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.mapper.TagClientEntityModelMapper;
import com.epam.esm.model.TagClientModel;
import com.epam.esm.model.TagEntityModel;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagTagServiceImpl
        implements TagService<TagClientModel> {

    @Autowired
    private TagDaoImpl tagDao;

    @Autowired
    private TagClientEntityModelMapper tagClientEntityModelMapper;

    @Override
    public TagClientModel findById(long id) {
        TagEntityModel tagEntityModel = tagDao.findById(id);
        TagClientModel tagClientModel =
                tagClientEntityModelMapper.entityToClient(tagEntityModel);
        return tagClientModel;
    }
}

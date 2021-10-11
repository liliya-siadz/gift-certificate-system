package com.epam.esm.mapper.impl;

import com.epam.esm.mapper.TagClientEntityModelMapper;
import com.epam.esm.model.TagClientModel;
import com.epam.esm.model.TagEntityModel;
import org.springframework.stereotype.Component;

@Component
public class TagClientEntityModelMapperImpl
        implements TagClientEntityModelMapper {
    @Override
    public TagClientModel entityToClient(TagEntityModel tagEntityModel) {
        if (tagEntityModel == null) {
            return null;
        }
        TagClientModel tagClientModel = new TagClientModel();
        long id = tagEntityModel.getId();
        String name = tagEntityModel.getName();
        tagClientModel.setId(id);
        tagClientModel.setName(name);
        return tagClientModel;
    }

    @Override
    public TagEntityModel clientToEntity(TagClientModel tagClientModel) {
        if (tagClientModel == null) {
            return null;
        }
        TagEntityModel tagEntityModel = new TagEntityModel();
        long id = tagClientModel.getId();
        String name = tagClientModel.getName();
        tagEntityModel.setId(id);
        tagEntityModel.setName(name);
        return tagEntityModel;
    }
}

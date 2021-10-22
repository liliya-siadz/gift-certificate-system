package com.epam.esm.mapper;

import com.epam.esm.model.TagClientModel;
import com.epam.esm.model.TagEntityModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagModelMapper {
    TagClientModel toClientModel(TagEntityModel entity);

    TagEntityModel toEntity(TagClientModel clientModel);
}

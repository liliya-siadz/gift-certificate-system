package com.epam.esm.mapper;

import com.epam.esm.entity.TagEntity;
import com.epam.esm.clientmodel.TagClientModel;

/**
 * Maps Tag entity model to client model and client model to entity model .
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface TagMapper extends Mapper<TagEntity, TagClientModel> {
}

package com.epam.esm.mapper;

import com.epam.esm.entity.Tag;
import com.epam.esm.model.TagModel;
import org.mapstruct.Mapper;

/**
 * Maps Tag entity model to client model and client model to entity model .
 */
@Mapper(componentModel = "spring")
public interface TagMapper extends EntityModelMapper<Tag, TagModel> {

    /**
     * Maps Tag entity to client model .
     *
     * @param entity entity model
     * @return client model
     */
    TagModel toModel(Tag entity);

    /**
     * Maps Tag client model to entity model .
     *
     * @param model client model
     * @return entity model
     */
    Tag toEntity(TagModel model);
}

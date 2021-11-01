package com.epam.esm.mapper;

import com.epam.esm.model.TagClientModel;
import com.epam.esm.model.TagEntityModel;
import org.mapstruct.Mapper;

/**
 * Maps Tag entity model to client model and client model to entity model .
 */
@Mapper(componentModel = "spring")
public interface TagModelMapper {

    /**
     * Maps Tag entity to client model .
     *
     * @param entity entity model
     * @return client model
     */
    TagClientModel toClientModel(TagEntityModel entity);

    /**
     * Maps Tag client model to entity model .
     *
     * @param clientModel client model
     * @return entity model
     */
    TagEntityModel toEntity(TagClientModel clientModel);
}

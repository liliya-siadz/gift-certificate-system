package com.epam.esm.mapper;

import com.epam.esm.model.TagClientModel;
import com.epam.esm.model.TagEntityModel;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TagClientEntityModelMapper {
    TagClientModel entityToClient(TagEntityModel tagEntityModel);
    TagEntityModel clientToEntity(TagClientModel tagClientModel);
}

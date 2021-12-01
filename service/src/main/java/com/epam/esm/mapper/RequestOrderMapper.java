package com.epam.esm.mapper;

import com.epam.esm.clientmodel.RequestOrderClientModel;
import com.epam.esm.entity.OrderEntity;

/**
 * Maps Order entity model to request client model and request client model to entity model .
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface RequestOrderMapper extends Mapper<OrderEntity, RequestOrderClientModel> {
}

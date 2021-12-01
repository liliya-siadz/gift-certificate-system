package com.epam.esm.mapper;

import com.epam.esm.clientmodel.ResponseOrderClientModel;
import com.epam.esm.entity.OrderEntity;

/**
 * Maps Order entity model to response client model and response client model to entity model .
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface ResponseOrderMapper extends Mapper<OrderEntity, ResponseOrderClientModel> {
}

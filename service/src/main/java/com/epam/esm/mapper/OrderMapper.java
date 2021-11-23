package com.epam.esm.mapper;

import com.epam.esm.clientmodel.OrderClientModel;
import com.epam.esm.entity.OrderEntity;

/**
 * Maps Order entity model to client model and client model to entity model .
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface OrderMapper extends Mapper<OrderEntity, OrderClientModel>{
}

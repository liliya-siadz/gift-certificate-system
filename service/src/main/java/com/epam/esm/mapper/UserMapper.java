package com.epam.esm.mapper;

import com.epam.esm.clientmodel.UserClientModel;
import com.epam.esm.entity.UserEntity;

/**
 * Maps User entity model to client model and client model to entity model .
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface UserMapper extends Mapper<UserEntity, UserClientModel> {
}

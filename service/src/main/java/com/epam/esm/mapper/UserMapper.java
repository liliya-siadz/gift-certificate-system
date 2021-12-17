package com.epam.esm.mapper;

import com.epam.esm.clientmodel.UserClientModel;
import com.epam.esm.entity.UserEntity;

@org.mapstruct.Mapper(componentModel = "spring")
public interface UserMapper extends Mapper<UserEntity, UserClientModel> {
}

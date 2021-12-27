package com.epam.esm.mapper;

import com.epam.esm.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Maps User entity model to {@link UserDetails} model .
 */
public interface UserDetailsMapper {
    UserDetails toUser(UserEntity userClientModel);
}

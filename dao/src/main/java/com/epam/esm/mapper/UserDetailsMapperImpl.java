package com.epam.esm.mapper;

import com.epam.esm.entity.UserEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapperImpl implements UserDetailsMapper {
    @Override
    public UserDetails toUser(UserEntity userClientModel) {
        return new User(userClientModel.getName(), userClientModel.getPassword(),
                userClientModel.getRole().getAuthorities());
    }
}

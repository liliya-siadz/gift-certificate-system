package com.epam.esm.mapper.impl;

import com.epam.esm.clientmodel.security.UserDetailsImpl;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.mapper.UserDetailsMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapperImpl implements UserDetailsMapper {
    @Override
    public UserDetails toUser(UserEntity user) {
        return UserDetailsImpl.builder().username(user.getName())
                .id(user.getId()).password(user.getPassword())
                .authorities(user.getRole().getAuthorities()).build();
    }
}

package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.mapper.UserDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDao userDao;
    private final UserDetailsMapper mapper;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao, UserDetailsMapper mapper) {
        this.userDao = userDao;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return mapper.toUser(userDao.findByName(username));
    }
}

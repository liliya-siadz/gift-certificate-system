package com.epam.esm.service.impl;

import com.epam.esm.exception.UserWithNameNotFoundException;
import com.epam.esm.mapper.UserDetailsMapper;
import com.epam.esm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Loads user-specific data.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Interface for repository operations with User .
     */
    private final UserRepository repository;

    /**
     * Mapper from entity of User to <code>UserDetails</code> model .
     */
    private final UserDetailsMapper mapper;

    /**
     * Constructs <code>UserDetailsServiceImpl</code> with injected User repository and mapper.
     *
     * @param repository {@link #repository}
     * @param mapper     {@link #mapper}
     */
    @Autowired
    public UserDetailsServiceImpl(UserRepository repository, UserDetailsMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByName(username)
                .map(mapper::toUser)
                .orElseThrow(() -> new UserWithNameNotFoundException(username));
    }
}

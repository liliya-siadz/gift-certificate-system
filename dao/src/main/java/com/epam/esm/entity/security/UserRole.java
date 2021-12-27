package com.epam.esm.entity.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Describes User's app role .
 */
public enum UserRole {

    /**
     * User's role Admin .
     */
    ADMIN(Arrays.stream(Privilege.values()).collect(Collectors.toSet())),

    /**
     * User's role User.
     */
    USER(Stream.of(Privilege.ORDERS_CREATE).collect(Collectors.toSet()));

    /**
     * Contains set of privileges for role .
     */
    private final Set<Privilege> privileges;

    /**
     * Constructs <code>UserRole</code> enum .
     *
     * @param privileges {@link #privileges}
     */
    UserRole(Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    /**
     * Provides authorities of user's role using its privileges .
     *
     * @return authorities of user's role
     */
    public Set<SimpleGrantedAuthority> getAuthorities() {
        return privileges.stream().map(privilege -> new SimpleGrantedAuthority(privilege.getAction()))
                .collect(Collectors.toSet());
    }
}

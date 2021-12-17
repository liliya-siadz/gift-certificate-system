package com.epam.esm.entity.security;

<<<<<<< HEAD
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
=======
import com.epam.esm.entity.security.Privilege;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
>>>>>>> 79b8fef (1) Jwt token created; 2) Signup and login functions realized)
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum UserRole {
<<<<<<< HEAD
    ADMIN(Arrays.stream(Privilege.values()).collect(Collectors.toSet())),
    USER(Stream.of(Privilege.ORDERS_CREATE).collect(Collectors.toSet()));
=======
    ADMIN(Stream.of(Privilege.TAGS_CREATE, Privilege.TAGS_DELETE).collect(Collectors.toSet())),
    USER (new HashSet<>());
>>>>>>> 79b8fef (1) Jwt token created; 2) Signup and login functions realized)

    private final Set<Privilege> privileges;

    UserRole(Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return privileges.stream().map(privilege -> new SimpleGrantedAuthority(privilege.getAction()))
                .collect(Collectors.toSet());
    }
}

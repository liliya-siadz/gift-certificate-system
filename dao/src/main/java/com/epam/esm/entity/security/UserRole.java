package com.epam.esm.entity.security;
import com.epam.esm.entity.security.Privilege;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum UserRole {
    ADMIN(Arrays.stream(Privilege.values()).collect(Collectors.toSet())),
    USER(Stream.of(Privilege.ORDERS_CREATE).collect(Collectors.toSet()));

    private final Set<Privilege> privileges;

    UserRole(Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return privileges.stream().map(privilege -> new SimpleGrantedAuthority(privilege.getAction()))
                .collect(Collectors.toSet());
    }
}

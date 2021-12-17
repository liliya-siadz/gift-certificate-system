package com.epam.esm.entity;

import com.epam.esm.entity.converter.UserRoleConverter;
import com.epam.esm.entity.security.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"user\"")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    /**
     * Represents column 'id' .
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Represents column 'name' .
     */
    @Column(name = "name", unique = true, nullable = false, length = 200)
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<OrderEntity> orders = new ArrayList<>();

    /**
     * Represents column 'role' .
     */
    @Column(name = "role")
    @Convert(converter = UserRoleConverter.class)
    private UserRole role;

    /**
     * Represents column 'password'
     */
    @Column(name = "password", nullable = false, length = 100)
    private String password;
}

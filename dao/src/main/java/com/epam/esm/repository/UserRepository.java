package com.epam.esm.repository;

import com.epam.esm.entity.UserEntity;

import java.util.Optional;

/**
 * Presents access to repository operations with User .
 */
public interface UserRepository extends Repository<UserEntity> {

    /**
     * Find User by name .
     *
     * @param name name of User
     * @return <code>Optional.of</code> User with passed name or <code>Optional.empty</code>
     * if User was not found .
     */
    Optional<UserEntity> findByName(String name);

    @Override
    default Class<UserEntity> getEntityClass() {
        return UserEntity.class;
    }
}

package com.epam.esm.dao;

import com.epam.esm.entity.UserEntity;

/**
 * Presents access to repository operations with User .
 */
public interface UserDao extends Dao<UserEntity> {

    /**
     * Finds user's entity by user's name .
     *
     * @param name user's name
     * @return found User's entity
     */
    UserEntity findByName(String name);
}

package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.builder.UserQueryBuilder;
import com.epam.esm.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Implementation of interface {@link UserDao}
 * for presenting access to repository operations with User .
 */
@Repository
public class UserDaoImpl extends AbstractDao<UserEntity> implements UserDao {

    /**
     * Query builder for criteria queries .
     */
    @Autowired
    private UserQueryBuilder queryBuilder;

    /**
     * Constructs class <code>UserDaoImpl</code> with passed query builder .
     *
     * @param queryBuilder {@link #queryBuilder}
     */
    public UserDaoImpl(UserQueryBuilder queryBuilder) {
        super(queryBuilder);
    }

    @Override
    public Class<UserEntity> getEntityClass() {
        return UserEntity.class;
    }

    @Override
    public String[] getPrimaryKeyAttributeName() {
        return new String[]{"id"};
    }
}

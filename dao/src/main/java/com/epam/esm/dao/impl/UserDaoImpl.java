package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.builder.UserQueryBuilder;
import com.epam.esm.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Implementation of interface {@link UserDao}
 * for presenting access to repository operations with User .
 */
@Repository
public class UserDaoImpl extends AbstractDao<UserEntity> implements UserDao {

    /**
     * Entity manager for using and operation entities .
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Query builder for criteria queries .
     */
    @Autowired
    private UserQueryBuilder queryBuilder;

    /**
     * Constructs class <code>UserDaoImpl</code>
     * with entity manager and criteria query builder .
     *
     * @param entityManager {@link #entityManager}
     * @param queryBuilder  {@link #queryBuilder}
     */
    public UserDaoImpl(EntityManager entityManager, UserQueryBuilder queryBuilder) {
        super(entityManager, queryBuilder);
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

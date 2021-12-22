package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.builder.UserQueryBuilder;
import com.epam.esm.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

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

    @Override
    public UserEntity findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> user = query.from(UserEntity.class);
        query.select(user).where(criteriaBuilder.equal(user.get("name"), name));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    @Transactional
    public UserEntity create(UserEntity entity) {
        entityManager.persist(entity);
        Object id = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
        return entityManager.find(getEntityClass(), id);
    }
}

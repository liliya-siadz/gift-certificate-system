package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.builder.GiftCertificateQueryBuilder;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.PageableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

/**
 * Implementation of interface {@link GiftCertificateDao}
 * for presenting access to repository operations with Gift Certificate .
 */
@Repository
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificateEntity>
        implements GiftCertificateDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Query builder for criteria queries .
     */
    @Autowired
    private GiftCertificateQueryBuilder queryBuilder;

    /**
     * Constructs class <code>GiftCertificateDaoImpl</code>
     * with entity manager and criteria query builder .
     *
     * @param entityManager {@link #entityManager}
     */
    public GiftCertificateDaoImpl(EntityManager entityManager, GiftCertificateQueryBuilder queryBuilder) {
        super(entityManager, queryBuilder);
    }

    @Override
    public Class<GiftCertificateEntity> getEntityClass() {
        return GiftCertificateEntity.class;
    }

    @Override
    public String[] getPrimaryKeyAttributeName() {
        return new String[]{"id"};
    }

    @Override
    @Transactional
    public void update(GiftCertificateEntity entity) {
        entityManager.merge(entity);
    }

    @Override
    public PageableEntity<GiftCertificateEntity> search(String tagName, String name, String description,
                                                        String sortField, String sortDirection,
                                                        int pageSize, int pageNumber) {
        CriteriaQuery<GiftCertificateEntity> searchQuery = queryBuilder.buildSearchQuery(
                entityManager.getCriteriaBuilder(), tagName, name, description, sortField, sortDirection);
        TypedQuery<GiftCertificateEntity> typedQuery = entityManager.createQuery(searchQuery);
        TypedQuery<GiftCertificateEntity> temp = entityManager.createQuery(searchQuery);
        long totalSearchedEntities = temp.getResultList().size();
        return queryBuilder.buildPageFromQuery(typedQuery, pageSize, pageNumber, totalSearchedEntities);
    }
}

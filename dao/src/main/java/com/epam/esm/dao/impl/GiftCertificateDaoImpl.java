package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.querybuilder.GiftCertificateQueryBuilder;
import com.epam.esm.entity.GiftCertificateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Implementation of interface {@link GiftCertificateDao}
 * for presenting access to repository operations with Gift Certificate .
 */
@Repository
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificateEntity>
        implements GiftCertificateDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private GiftCertificateQueryBuilder queryBuilder;

    /**
     * Constructs class <code>GiftCertificateDaoImpl</code>
     * with entity manager .
     *
     * @param entityManager {@link #entityManager}
     */
    public GiftCertificateDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<GiftCertificateEntity> getEntityClass() {
        return GiftCertificateEntity.class;
    }

    @Override
    @Transactional
    public void update(GiftCertificateEntity entity) {
        entityManager.merge(entity);
    }

    @Override
    public List<GiftCertificateEntity> search(String tagName, String name, String description,
                                              String sortField, String sortDirection) {
        CriteriaQuery<GiftCertificateEntity> searchQuery = queryBuilder.buildSearchQuery(
                entityManager.getCriteriaBuilder(), tagName, name, description, sortField, sortDirection);
        return entityManager.createQuery(searchQuery).getResultList();
    }
}

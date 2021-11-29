package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.builder.GiftCertificateQueryBuilder;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.entity.PageableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.math.BigDecimal;
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

    /**
     * Query builder for custom criteria queries .
     */
    @Autowired
    private GiftCertificateQueryBuilder queryBuilder;

    /**
     * Criteria builder for building criteria queries
     */
    private CriteriaBuilder criteriaBuilder;

    /**
     * Constructs class <code>GiftCertificateDaoImpl</code>
     * with entity manager and criteria query builder .
     *
     * @param entityManager {@link #entityManager}
     * @param queryBuilder  {@link #queryBuilder}
     */
    public GiftCertificateDaoImpl(EntityManager entityManager, GiftCertificateQueryBuilder queryBuilder) {
        super(entityManager, queryBuilder);
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
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
    @Transactional
    public void updatePrice(Long id, BigDecimal price) {
        CriteriaUpdate<GiftCertificateEntity> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(getEntityClass());
        Root<GiftCertificateEntity> root = criteriaUpdate.from(getEntityClass());
        criteriaUpdate.set("price", price);
        criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), id));
        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }

    @Override
    public PageableEntity<GiftCertificateEntity> search(List<String> tags, int pageSize, int pageNumber) {
        CriteriaQuery<GiftCertificateEntity> searchQuery = queryBuilder.buildSearchQuery(criteriaBuilder, tags);
        return runCriteriaQuery(searchQuery, pageSize, pageNumber);
    }

    @Override
    public PageableEntity<GiftCertificateEntity> search(String tagName, String name, String description,
                                                        String sortField, String sortDirection,
                                                        int pageSize, int pageNumber) {
        CriteriaQuery<GiftCertificateEntity> searchQuery = queryBuilder.buildSearchQuery(
                criteriaBuilder, tagName, name, description, sortField, sortDirection);
        return runCriteriaQuery(searchQuery, pageSize, pageNumber);
    }

    @Override
    public void boundCertificateToOrder(long certificateId, long orderId) {
        OrderEntity order = entityManager.find(OrderEntity.class, orderId);
        GiftCertificateEntity certificate = entityManager.find(GiftCertificateEntity.class, certificateId);
        order.getCertificates().add(certificate);
        entityManager.merge(order);
    }
}

package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.builder.TagQueryBuilder;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Implementation of interface {@link TagDao}
 * for presenting access to repository operations with Tag .
 */
@Repository
public class TagDaoImpl extends AbstractDao<TagEntity> implements TagDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Query builder for criteria queries .
     */
    @Autowired
    private TagQueryBuilder tagQueryBuilder;

    /**
     * Constructs class <code>TagDaoImpl</code>
     * with entity manager and criteria query builder .
     *
     * @param entityManager {@link #entityManager}
     */
    public TagDaoImpl(EntityManager entityManager, TagQueryBuilder tagQueryBuilder) {
        super(entityManager, tagQueryBuilder);
    }

    @Override
    public Class<TagEntity> getEntityClass() {
        return TagEntity.class;
    }

    @Override
    public String[] getPrimaryKeyAttributeName() {
        return new String[]{"id"};
    }

    @Override
    public List<TagEntity> findAllTagsBoundToGiftCertificate(long certificateId) {
        GiftCertificateEntity giftCertificate = entityManager.find(GiftCertificateEntity.class, certificateId);
        return giftCertificate.getTags();
    }

    @Override
    @Transactional
    public void boundTagToGiftCertificate(long id, long certificateId) {
        GiftCertificateEntity giftCertificate = entityManager.find(GiftCertificateEntity.class, certificateId);
        TagEntity tag = entityManager.find(TagEntity.class, id);
        giftCertificate.getTags().add(tag);
        entityManager.merge(giftCertificate);
    }

    @Override
    @Transactional
    public void unboundTagFromGiftCertificate(long id, long certificateId) {
        GiftCertificateEntity giftCertificate = entityManager.find(GiftCertificateEntity.class, certificateId);
        giftCertificate.getTags().removeIf(tag -> tag.getId() == id);
        entityManager.merge(giftCertificate);
    }

    @Override
    public boolean isTagBoundToGiftCertificate(long id, long certificateId) {
        return entityManager.find(GiftCertificateEntity.class, certificateId)
                .getTags().stream().anyMatch(tag -> tag.getId() == id);
    }
}

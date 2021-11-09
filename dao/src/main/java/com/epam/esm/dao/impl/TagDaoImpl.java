package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of interface {@link TagDao}
 * for presenting access to repository operations with Tag .
 */
@Repository
public class TagDaoImpl extends AbstractDao<Tag> implements TagDao {

    /**
     * Constructs class <code>TagDaoImpl</code>
     * with injected entity manager .
     *
     * @param entityManager {@link #entityManager}
     */
    @Autowired
    public TagDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Tag> retrieveEntityClass() {
        return Tag.class;
    }

    @Override
    public Set<Tag> findAllTagsBoundToGiftCertificate(long certificateId) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, certificateId);
        return new HashSet<>(giftCertificate.getTags());
    }

    @Override
    @Transactional
    public void boundTagToGiftCertificate(long id, long certificateId) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, certificateId);
        Tag tag = entityManager.find(Tag.class, id);
        giftCertificate.getTags().add(tag);
        entityManager.merge(giftCertificate);
    }

    @Override
    @Transactional
    public void unboundTagFromGiftCertificate(long id, long certificateId) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, certificateId);
        giftCertificate.getTags().removeIf(tag -> tag.getId() == id);
        entityManager.merge(giftCertificate);
    }

    @Override
    public boolean isTagBoundToGiftCertificate(long id, long certificateId) {
        return entityManager.find(GiftCertificate.class, certificateId)
                .getTags().stream().anyMatch(tag -> tag.getId() == id);
    }
}

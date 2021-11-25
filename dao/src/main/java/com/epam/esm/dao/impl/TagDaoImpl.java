package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.builder.TagQueryBuilder;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Implementation of interface {@link TagDao}
 * for presenting access to repository operations with Tag .
 */
@Repository
public class TagDaoImpl extends AbstractDao<TagEntity> implements TagDao {

    private static final String FIND_TOP_TAG_NATIVE_SQL_QUERY =
            "SELECT tag.id, tag.name FROM tag"
                    + " JOIN gift_certificates_tags"
                    + " ON gift_certificates_tags.tag_id=tag.id"
                    + " JOIN orders_gift_certificates"
                    + " ON orders_gift_certificates.gift_certificate_id =gift_certificates_tags.gift_certificate_id"
                    + " JOIN public.order"
                    + " ON public.order.id=public.orders_gift_certificates.order_id"
                    + " AND public.order.user_id="
                    + "(SELECT public.user.id FROM public.user"
                    + " JOIN public.order"
                    + " ON public.order.user_id=public.user.id"
                    + " GROUP BY public.user.id ORDER BY sum(public.order.cost) DESC LIMIT 1)"
                    + " GROUP BY tag.id ORDER BY count(tag.id) DESC LIMIT 1";

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Query builder for criteria queries .
     */
    @Autowired
    private TagQueryBuilder queryBuilder;

    /**
     * Constructs class <code>TagDaoImpl</code>
     * with entity manager and criteria query builder .
     *
     * @param entityManager {@link #entityManager}
     * @param queryBuilder  {@link #queryBuilder}
     */
    public TagDaoImpl(EntityManager entityManager, TagQueryBuilder queryBuilder) {
        super(entityManager, queryBuilder);
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

    @Override
    public TagEntity findMostPopularTag() {
        try {
            Query nativeQuery = entityManager.createNativeQuery(FIND_TOP_TAG_NATIVE_SQL_QUERY, TagEntity.class);
            return ((TagEntity) nativeQuery.getSingleResult());
        } catch (NoResultException exception) {
            return null;
        }
    }
}

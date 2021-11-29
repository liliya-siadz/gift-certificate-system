package com.epam.esm.dao;

import com.epam.esm.entity.TagEntity;

import java.util.List;

/**
 * Presents access to repository operations with Tag .
 */
public interface TagDao extends Dao<TagEntity> {

    /**
     * Finds all Tag that related Gift Certificate with specified id .
     *
     * @return list of all entities Tags that related to target Gift Certificate
     */
    List<TagEntity> findAllTagsBoundToGiftCertificate(long certificateId);

    /**
     * Finds most popular Tag of Top User,
     * i.e. gets the most widely used Tag of a User with the highest cost of all Orders .
     *
     * @return entity of most widely used Tag of a User with the highest cost of all Orders
     * if Tag is present, otherwise - null
     */
    TagEntity findMostPopularTag();

    /**
     * Creates relation between Tag and Gift Certificate,
     * if relation didn't exist .
     *
     * @param id                id of Tag to relate
     * @param giftCertificateId id of Gift Certificate to relate
     */
    void boundTagToGiftCertificate(long id, long giftCertificateId);

    /**
     * Deletes relation between Tag and Gift Certificate,
     * if relation exist .
     *
     * @param id                id of Tag to unrelation
     * @param giftCertificateId id of Gift Certificate to unrelation
     */
    void unboundTagFromGiftCertificate(long id, long giftCertificateId);

    /**
     * Checks does Tag has relation with Gift Certificate .
     *
     * @param id                id of Tag to check relation
     * @param giftCertificateId id of Gift Certificate to check relation
     * @return true if relation exists, otherwise false
     */
    boolean isTagBoundToGiftCertificate(long id, long giftCertificateId);
}


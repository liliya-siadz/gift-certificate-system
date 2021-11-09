package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.Set;

/**
 * Presents access to repository operations with Tag .
 */
public interface TagDao extends Dao<Tag> {

    /**
     * Finds all Tag that related Gift Certificate with specified id .
     *
     * @return set of all entities Tags that related to target Gift Certificate
     */
    Set<Tag> findAllTagsBoundToGiftCertificate(long certificateId);

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

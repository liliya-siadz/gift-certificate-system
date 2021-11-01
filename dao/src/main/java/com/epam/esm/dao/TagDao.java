package com.epam.esm.dao;

import com.epam.esm.model.TagEntityModel;

import java.util.List;
import java.util.Optional;

/**
 * Presents access to repository operations with Tag .
 */
public interface TagDao {

    /**
     * Finds Tag with specified id .
     *
     * @param id id of Tag
     * @return {@code Optional.of(TagEntityModel)} if Tag was found,
     * otherwise {@code Optional.empty()}
     */
    Optional<TagEntityModel> findById(long id);

    /**
     * Finds all Tags .
     *
     * @return list of all Tags entities
     */
    List<TagEntityModel> findAll();

    /**
     * Finds all Tag that related Gift Certificate with specified id .
     *
     * @return list of all entities Tags that related to target Gift Certificate
     */
    List<TagEntityModel> findAllTagsBoundToGiftCertificate(long certificateId);

    /**
     * Creates new Tag .
     *
     * @param entityModel Tag entity model that contains params for Tag creation
     * @return created Tag entity
     */
    long create(TagEntityModel entityModel);

    /**
     * Deletes Tag with specified id .
     *
     * @param id id of Tag to delete
     * @return deleted Tag entity
     */
    boolean delete(long id);

    /**
     * Checks if Tag with specified id exists .
     *
     * @param id id of Tag to check
     * @return true if Tag is exist, otherwise - false
     */
    boolean isExist(long id);

    /**
     * Creates relation between Tag and Gift Certificate .
     *
     * @param id                id of Tag to relate
     * @param giftCertificateId id of Gift Certificate to relate
     */
    void boundTagToGiftCertificate(long id, long giftCertificateId);

    /**
     * Deletes relation between Tag and Gift Certificate .
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

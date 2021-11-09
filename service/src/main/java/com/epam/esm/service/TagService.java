package com.epam.esm.service;

import com.epam.esm.model.TagModel;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Presents access to service operations with Tag .
 */
@Service
public interface TagService extends BaseService<TagModel> {

    /**
     * Finds all Tags that bounded to target Gift Certificate .
     *
     * @param giftCertificateId id of Gift Certificate to
     *                          search bounded tags on
     * @return set of Tags client models that bounds
     * to target Gift Certificate
     */
    Set<TagModel> findAllTagsBoundToGiftCertificate(Long giftCertificateId);

    /**
     * Updates Tags relations with specified Gift Certificate with passed id .
     * <p>
     * Could bound, unbound and create and bound Tags to specified Gift Certificate:
     * <li> if both Tag's name and Tag's id are not null
     * bounds Tag to Gift Certificate
     * <li> if Tag's name is null and Tag's id  is not null
     * unbounds Tag from Gift Certificate
     * <li> if Tag's name is not null and Tag's id is null
     * creates and then bounds Tag to Gift Certificate
     *
     * @param certificateId id of target Gift Certificate
     * @param tags          set of Tags to execute relative to Gift Certificate
     *                      updates operations
     * @return set of Tags that bound to target Gift Certificate
     */
    Set<TagModel> updateExistingGiftCertificateTags(Long certificateId, Set<TagModel> tags);

    /**
     * Updates Tags relations with specified Gift Certificate with passed id .
     * <p>
     * Could bound, and create and bound Tags to specified Gift Certificate:
     * <li> if both Tag's name and Tag's id are not null
     * bounds Tag to Gift Certificate
     * <li> if Tag's name is not null and Tag's id is null
     * creates and then bounds Tag to Gift Certificate
     *
     * @param giftCertificateId id of target Gift Certificate
     * @param tags              set of Tags to execute relative to Gift Certificate
     *                          updates operations
     * @return set of Tags that bound to target Gift Certificate
     */
    Set<TagModel> updateNewGiftCertificateTags(Long giftCertificateId, Set<TagModel> tags);
}

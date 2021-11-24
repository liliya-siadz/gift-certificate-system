package com.epam.esm.service;

import com.epam.esm.clientmodel.TagClientModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Presents access to service operations with Tag .
 */
@Service
public interface TagService extends BaseService<TagClientModel> {

    /**
     * Finds all Tags that bounded to target Gift Certificate .
     *
     * @param giftCertificateId id of Gift Certificate to
     *                          search bounded tags on
     * @return list of Tags client models that bounds
     * to target Gift Certificate
     */
    List<TagClientModel> findAllTagsBoundToGiftCertificate(Long giftCertificateId);

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
     * @param tags          list of Tags to execute relative to Gift Certificate
     *                      updates operations
     * @return list of Tags that bound to target Gift Certificate
     */
    List<TagClientModel> updateExistingGiftCertificateTags(Long certificateId, List<TagClientModel> tags);

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
     * @param tags              list of Tags to execute relative to Gift Certificate
     *                          updates operations
     */
    List<TagClientModel> updateNewGiftCertificateTags(Long giftCertificateId, List<TagClientModel> tags);

    /**
     * Finds most popular Tag of Top User,
     * i.e. gets the most widely used Tag of a User with the highest cost of all Orders .
     *
     * @return most widely used Tag of a User with the highest cost of all Orders
     * if Tag is present, otherwise - null
     */
    TagClientModel findMostPopularTag();
}

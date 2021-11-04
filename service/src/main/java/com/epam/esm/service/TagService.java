package com.epam.esm.service;

import com.epam.esm.model.TagClientModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Presents access to service operations with Tag .
 */
@Service
public interface TagService {

    /**
     * Finds Tags with specified id .
     *
     * @param id id of Tag to find
     * @return client model of found Tag
     */
    TagClientModel findById(Long id);

    /**
     * Deletes Tag with specified id .
     *
     * @param id id of Tag to delete
     * @return client model of deleted Tag
     */
    TagClientModel delete(Long id);

    /**
     * Find all Tags that exists .
     *
     * @return list of client models of existing Tags
     */
    List<TagClientModel> findAll();

    /**
     * Creates passed Tag .
     *
     * @param tagClientModel Tag client model to create
     * @return client model of created Tag
     */
    TagClientModel create(TagClientModel tagClientModel);

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
     * @param giftCertificateId id of target Gift Certificate
     * @param tags              list of Tags to execute relative to Gift Certificate
     *                          updates operations
     * @return list of Tags that bound to target Gift Certificate
     */
    List<TagClientModel> updateExistingGiftCertificateTags(Long giftCertificateId, List<TagClientModel> tags);


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
     * @return list of Tags that bound to target Gift Certificate
     */
    List<TagClientModel> updateNewGiftCertificateTags(Long giftCertificateId, List<TagClientModel> tags);

    /**
     * Checks if Gift Certificate with passed id exist .
     *
     * @param id id of Gift Certificate to check existence
     * @return true if Gift Certificate exists, otherwise false
     */
    boolean isExist(Long id);
}

package com.epam.esm.service;

import com.epam.esm.model.GiftCertificateClientModel;

import java.util.List;

/**
 * Presents access to service operations with Gift Certificate .
 */
public interface GiftCertificateService {

    /**
     * Creates Gift Certificate .
     *
     * @param clientModel client model of Gift Certificate to create
     * @return client model of Gift Certificate that was created
     */
    GiftCertificateClientModel create(GiftCertificateClientModel clientModel);

    /**
     * Find all Gift Certificates that exists .
     *
     * @return list of client models of existing Gift Certificates
     */
    List<GiftCertificateClientModel> findAll();

    /**
     * Find Gift Certificate with specified id .
     *
     * @param id id of Gift Certificate to find with
     * @return client model of found Gift Certificate
     */
    GiftCertificateClientModel findById(Long id);

    /**
     * Delete Gift Certificate with specified id .
     *
     * @param id id of Gift Certificate to delete
     * @return client model of delete Gift Certificate
     */
    GiftCertificateClientModel delete(Long id);


    /**
     * Update Gift Certificate with specified id by passed
     * parameters in client model .
     *
     * @param id          id of Gift Certificate that should be updated
     * @param clientModel client model of Gift Certificate
     *                    to use params to update from
     * @return client model of updated Gift Certificate
     */
    GiftCertificateClientModel update(Long id, GiftCertificateClientModel clientModel);

    /**
     * Searches Gift Certificates by params specified conditions, could
     * sort result list .
     *
     * @param tagName     exact name of Tag that relates to Gift Certificate
     * @param name        part of Gift Certificate's name
     * @param description part of Gift Certificate's description
     * @param sort        sort field with direction to execute sorting on
     * @return
     * <li> if all params are null - all Gift Certificates
     * <li> if sort param is null - searched by params unsorted Gift Certificates ,
     * <li>otherwise searched and sorted by params Gift Certificates
     */
    List<GiftCertificateClientModel> search(String tagName, String name, String description, String sort);
}

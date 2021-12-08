package com.epam.esm.service;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.clientmodel.PageableClientModel;

import java.math.BigDecimal;
import java.util.List;

/**
 * Presents access to service operations with Gift Certificate .
 */
public interface GiftCertificateService extends UpdatableService<GiftCertificateClientModel> {

    /**
     * Searches (also could sort) Gift Certificates that fit to passed parameters .
     *
     * @param tagName       full name of Tag that bounds to target Gift Certificate
     * @param name          part of name of target Gift Certificate
     * @param description   part of description of target Gift Certificate
     * @param sortField     property of sorting Gift Certificate
     *                      accessible values in {@link com.epam.esm.dao.builder.sort.SortField#sortFields}
     * @param sortDirection direction of sorting Gift Certificates
     *                      accessible values in {@link com.epam.esm.dao.builder.sort.SortDirection#values()}
     * @param pageNumber    page number of found result of Users
     * @param pageSize      quantity of Users on a page (page size)
     * @return one page of found Gift Certificates
     * see {@link com.epam.esm.dao.GiftCertificateDao#search}
     */
    PageableClientModel<GiftCertificateClientModel> search(String tagName, String name, String description,
                                                           String sortField, String sortDirection,
                                                           Integer pageSize, Integer pageNumber);

    /**
     * Searches Gift Certificates by bound Tags .
     *
     * @param tags       names of Tags that bound to Gift Certificates
     * @param pageNumber page number of found result of Users
     * @param pageSize   quantity of Users on a page (page size)
     * @return one page of found Gift Certificates
     * see {@link com.epam.esm.dao.GiftCertificateDao#search}
     */
    PageableClientModel<GiftCertificateClientModel> search(List<String> tags, Integer pageSize, Integer pageNumber);

    /**
     * Updates price of Gift Certificate with passed id .
     *
     * @param id    id of Gift Certificate to change price
     * @param price new price for Gift Certificate
     * @return updated client model of Gift Certificate
     */
    GiftCertificateClientModel updatePrice(Long id, BigDecimal price);

    /**
     * Updates Gift Certificate relations with specified Order with passed id,
     * by bounding them to the Order .
     *
     * @param orderId      id of target Order
     * @param certificates list of Gift Certificate to bound with the Order
     */
    void updateNewOrderCertificates(Long orderId, List<GiftCertificateClientModel> certificates);
}

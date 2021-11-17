package com.epam.esm.service;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.clientmodel.PageableClientModel;

/**
 * Presents access to service operations with Gift Certificate .
 */
public interface GiftCertificateService extends UpdatableService<GiftCertificateClientModel> {

    /**
     * Searches (also could sort) passed quantity of Gift Certificate
     * that fits to specified params of search from passed page .
     *
     * @param tagName       full name of Tag that bound to target Gift Certificate
     * @param name          part of name of target Gift Certificate
     * @param description   part of description of target Gift Certificate
     * @param sortField     property of sorting Gift Certificate
     *                      accessible values in {@link com.epam.esm.dao.builder.sort.SortField#sortFields}
     * @param sortDirection direction of sorting Gift Certificates
     *                      accessible values in {@link com.epam.esm.dao.builder.sort.SortDirection#values()}
     * @param pageNumber    page number to get Gift Certificates from
     * @param pageSize      quantity of Gift Certificates on page (page size)
     * @return fixed size page of search result
     * see {@link com.epam.esm.dao.GiftCertificateDao#search}
     */
    PageableClientModel<GiftCertificateClientModel> search(String tagName, String name, String description,
                                                           String sortField, String sortDirection,
                                                           Integer pageSize, Integer pageNumber);
}

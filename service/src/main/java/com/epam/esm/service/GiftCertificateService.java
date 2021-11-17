package com.epam.esm.service;

import com.epam.esm.clientmodel.GiftCertificateClientModel;

import java.util.List;

/**
 * Presents access to service operations with Gift Certificate .
 */
public interface GiftCertificateService extends UpdatableService<GiftCertificateClientModel> {

    /**
     * Searches (also could sort) Gift Certificate entities
     * that fits to specified params of search .
     *
     * @param tagName       full name of Tag that bound to target Gift Certificate
     * @param name          part of name of target Gift Certificate
     * @param description   part of description of target Gift Certificate
     * @param sortField     property of sorting Gift Certificate,
     *                      accessible values in {@link com.epam.esm.dao.querybuilder.sort.SortField#sortFields}
     * @param sortDirection direction of sorting Gift Certificates,
     *                      accessible values in {@link com.epam.esm.dao.querybuilder.sort.SortDirection#values()}
     * @return found and sorted list of Gift Certificates
     * see {@link com.epam.esm.dao.GiftCertificateDao#search(String, String, String, String, String)}
     */
    List<GiftCertificateClientModel> search(String tagName, String name, String description,
                                            String sortField, String sortDirection);
}

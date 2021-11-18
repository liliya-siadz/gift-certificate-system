package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.PageableEntity;

/**
 * Presents access to repository operations with Gift Certificate .
 */
public interface GiftCertificateDao extends UpdatableDao<GiftCertificateEntity> {

    /**
     * Searches (also could sort) passed quantity of Gift Certificate entities
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
     * see {@link com.epam.esm.dao.builder.GiftCertificateQueryBuilder#buildSearchQuery}
     */
    PageableEntity<GiftCertificateEntity> search(String tagName, String name, String description,
                                                 String sortField, String sortDirection,
                                                 int pageSize, int pageNumber);
}

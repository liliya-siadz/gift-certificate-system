package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificateEntity;

import java.util.List;

/**
 * Presents access to repository operations with Gift Certificate .
 */
public interface GiftCertificateDao extends UpdatableDao<GiftCertificateEntity> {

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
     */
    List<GiftCertificateEntity> search(String tagName, String name, String description,
                                       String sortField, String sortDirection);
}

package com.epam.esm.dao.builder;

import com.epam.esm.entity.GiftCertificateEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Criteria query builder for repository operations with Gift Certificate .
 */
public interface GiftCertificateQueryBuilder
        extends QueryBuilder<GiftCertificateEntity> {

    /**
     * Builds criteria query for searching Gift Certificate entities
     * using passed arguments as conditions (restrictions) for search (and sort) .
     *
     * @param criteriaBuilder criteria builder to build query
     * @param tagName         full name of Tag that bound to target Gift Certificate
     * @param name            part of name of target Gift Certificate
     * @param description     part of description of target Gift Certificate
     * @param sortField       property of sorting Gift Certificate,
     *                        accessible values in {@link com.epam.esm.dao.builder.sort.SortField#sortFields}
     * @param sortDirection   direction of sorting Gift Certificates,
     *                        accessible values in {@link com.epam.esm.dao.builder.sort.SortDirection#values()}
     * @return built criteria query with restrictions formed by passed arguments
     */
    CriteriaQuery<GiftCertificateEntity> buildSearchQuery(CriteriaBuilder criteriaBuilder,
                                                          String tagName, String name, String description,
                                                          String sortField, String sortDirection);
}

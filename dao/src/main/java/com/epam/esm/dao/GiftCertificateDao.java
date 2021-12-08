package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.PageableEntity;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.List;

/**
 * Presents access to repository operations with Gift Certificate .
 */
public interface GiftCertificateDao extends UpdatableDao<GiftCertificateEntity> {

    /**
     * Searches (also could sort) entities of Gift Certificates that
     * fit to passed parameters .
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
     * @return one page of found Gift Certificates entities
     * see {@link com.epam.esm.dao.builder.GiftCertificateQueryBuilder#buildSearchQuery}
     */
    PageableEntity<GiftCertificateEntity> search(String tagName, String name, String description,
                                                 String sortField, String sortDirection,
                                                 int pageSize, int pageNumber);

    /**
     * Searches Gift Certificates entities by bound Tags .
     *
     * @param tags       names of Tags that bound to Gift Certificates
     * @param pageNumber page number of found result of Users
     * @param pageSize   quantity of Users on a page (page size)
     * @return one page of found Gift Certificates entities
     * see {@link com.epam.esm.dao.builder.GiftCertificateQueryBuilder#buildSearchQuery}
     */
    PageableEntity<GiftCertificateEntity> search(List<String> tags, int pageSize, int pageNumber);

    /**
     * Updates price of Gift Certificate entity with passed id .
     *
     * @param id    id of entity to execute update on
     * @param price new value of price
     */
    void updatePrice(Long id, BigDecimal price);

    /**
     * Creates relation between Gift Certificate and Order .
     *
     * @param certificateId id of Gift Certificate to relate
     * @param orderId       id of Order to relate
     */
    void boundCertificateToOrder(long certificateId, long orderId);
}

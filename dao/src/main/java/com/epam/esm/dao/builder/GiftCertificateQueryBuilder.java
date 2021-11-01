package com.epam.esm.dao.builder;

import com.epam.esm.model.GiftCertificateEntityModel;

import java.util.Map;

/**
 * Interface for building search and update sql queries for Gift Certificate .
 */
public interface GiftCertificateQueryBuilder {
    String EMPTY_STRING = "";

    /**
     * Builds update query with specified values from passed entity
     * for Gift Certificate with passed id .
     *
     * @param id     id of Gift Certificate for update
     * @param entity entity that contains new values of target Gift Certificate
     * @return update query for target Gift Certificate with params from entity,
     * if all entity params are null, returns {@link GiftCertificateQueryBuilder#EMPTY_STRING}
     */
    String buildUpdateQuery(long id, GiftCertificateEntityModel entity);

    /**
     * Builds query from passed params for searching and sorting
     * existing Gift Certificates .
     *
     * @param tagName     full name of relative Tag, could be null
     *                    (query part 'WHERE tag.name = tagName')
     * @param name        part of name of Gift Certificate, could be null
     *                    (query part 'WHERE gift_certificate.name LIKE %name%')
     * @param description part of name of Gift Certificate, could be null
     *                    (query part 'WHERE gift_certificate.description LIKE %description%')
     * @param sort        sort clause, could be null, may accept next values:
     *                    <li>'1name' sort ascending by attribute 'name'
     *                    ('ORDER BY gift_certificate.name ASC')
     *                    <li>'name' sort ascending by attribute 'name'
     *                    ('ORDER BY gift_certificate.name ASC')
     *                    <li>'0name' sort descending by attribute 'name'
     *                    ('ORDER BY gift_certificate.name DESC')
     *                    <li>'1create_date' sort ascending by attribute 'create_date'
     *                    ('ORDER BY gift_certificate.create_date ASC')
     *                    <li>'create_date' sort ascending by attribute 'create_date'
     *                    ('ORDER BY gift_certificate.create_date ASC')
     *                    <li>'0create_date' sort descending by attribute 'create_date'
     *                    ('ORDER BY gift_certificate.create_date DESC')
     * @return query for search and sort existing Gift Certificates
     * that fits to passed params,
     * if sort param is null - return search query with no sorting
     * if all params are null - return simple find all Gift Certificate query
     */
    String buildSearchQuery(String tagName, String name, String description, String sort);

    /**
     * Researches passed Gift Certificate entity
     * for update params and theirs types respectively .
     *
     * @param entity Gift Certificate entity with update params
     * @return map of params for update with param name and description,
     * map entry structure is:
     * <li> key - param's name
     * <li> values - param's description (represented by class{@link FieldDescription})
     */
    Map<String, FieldDescription> findUpdateParams(GiftCertificateEntityModel entity);
}

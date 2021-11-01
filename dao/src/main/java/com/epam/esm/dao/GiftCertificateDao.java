package com.epam.esm.dao;

import com.epam.esm.model.GiftCertificateEntityModel;

import java.util.List;
import java.util.Optional;

/**
 * Presents access to repository operations with Gift Certificate .
 */
public interface GiftCertificateDao {

    /**
     * Creates new Gift Certificate .
     *
     * @param entity Gift Certificate entity model
     *               that contains params for Gift Certificate creation
     * @return created Gift Certificate entity
     */
    long create(GiftCertificateEntityModel entity);

    /**
     * Finds all Gift Certificates .
     *
     * @return list of all Gift Certificates entities
     */
    List<GiftCertificateEntityModel> findAll();

    /**
     * Finds Gift Certificate with specified id .
     *
     * @param id id of Gift Certificate
     * @return {@code Optional.of(GiftCertificateEntityModel)} if Gift Certificate was found,
     * otherwise {@code Optional.empty()}
     */
    Optional<GiftCertificateEntityModel> findById(long id);

    /**
     * Deletes Gift Certificate with specified id .
     *
     * @param id id of Gift Certificate to delete
     * @return deleted Gift Certificate entity
     */
    boolean delete(long id);

    /**
     * Updates Gift Certificate with passed id by values from passed entity .
     *
     * @param id     id of Gift Certificate for update
     * @param entity entity with new values for target Gift Certificate
     * @return updated Gift Certificate entity with actual values
     */
    GiftCertificateEntityModel update(long id, GiftCertificateEntityModel entity);

    /**
     * Checks if Gift Certificate with specified id exists .
     *
     * @param id id of Gift Certificate for check existence
     * @return true if Gift Certificate is exist, otherwise - false
     */
    boolean isExist(long id);

    /**
     * Search for Gift Certificates that fit to passed params .
     *
     * @param tagName     full name of relative Tag, could be null
     * @param name        part of name of Gift Certificate, could be null
     * @param description part of name of Gift Certificate, could be null
     * @param sort        sort param, could be null, may accept next values:
     *                    <li>'1name' sort ascending by attribute 'name'
     *                    <li>'name' sort ascending by attribute 'name'
     *                    <li>'0name' sort descending by attribute 'name'
     *                    <li>'1create_date' sort ascending by attribute 'create_date'
     *                    <li>'create_date' sort ascending by attribute 'create_date'
     *                    <li>'0create_date' sort descending by attribute 'create_date'
     * @return all Gift Certificates that filtered and sorted by passed params,
     * if params are null - returns list of all Gift Certificates entities,
     * if sort param is null - returns unsorted filtered entities,
     * see {@link com.epam.esm.dao.builder.GiftCertificateQueryBuilder#buildSearchQuery}
     */
    List<GiftCertificateEntityModel> search(String tagName, String name, String description, String sort);
}

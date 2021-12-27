package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificateEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Presents access to repository operations with Gift Certificate .
 */
public interface GiftCertificateRepository
        extends com.epam.esm.repository.Repository<GiftCertificateEntity> {

    /**
     * Finds entities of Gift Certificates by passed conditions (filters) .
     *
     * @param tagNames    list of Tags names, that bound to Gift Certificate
     * @param tagQuantity quantity of Tags names that passed (size of list)
     * @param name        part of Gift Certificate name
     * @param description part of Gift Certificate description
     * @param pageable    page of search result to retrieve
     * @return page of searched Gift Certificates
     */
    @Query(value = "SELECT gc FROM GiftCertificateEntity gc LEFT JOIN gc.tags tg"
            + " WHERE ((:tagNames) is null or tg.name IN :tagNames)"
            + " AND (:name is null or gc.name like %:name%) "
            + " AND (:description is null or gc.description like %:description%)"
            + "  GROUP BY gc HAVING COUNT(tg)>=:tagQuantity")
    Page<GiftCertificateEntity> findByConditions(@Param("tagNames") List<String> tagNames,
                                                 @Param("tagQuantity") Long tagQuantity,
                                                 @Param("name") String name,
                                                 @Param("description") String description,
                                                 Pageable pageable);

    /**
     * Finds Gift Certificate by passed id and passed id od of Tag, that bounds to Gift Certificate .
     *
     * @param id    id of Gift Certificate to find
     * @param tagId id of Tag, that bounds to finding Gift Certificate
     * @return <code>Optional.of</code> Gift Certificate or <code>Optional.empty</code>
     * if Gift Certificate was not found .
     */
    Optional<GiftCertificateEntity> findByIdAndTagsId(Long id, Long tagId);

    @Override
    default Class<GiftCertificateEntity> getEntityClass() {
        return GiftCertificateEntity.class;
    }
}

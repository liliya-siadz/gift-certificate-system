package com.epam.esm.dao.builder.impl;

import com.epam.esm.dao.builder.GiftCertificateQueryBuilder;
import com.epam.esm.dao.builder.sort.SortDirection;
import com.epam.esm.dao.builder.sort.SortField;
import com.epam.esm.dao.builder.sort.UnknownSortFieldException;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.TagEntity;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of interface {@link GiftCertificateQueryBuilder}
 * builds criteria queries for Gift Certificate .
 */
@Component
public class GiftCertificateQueryBuilderImpl implements GiftCertificateQueryBuilder {
    @Override
    public CriteriaQuery<GiftCertificateEntity> buildSearchQuery(CriteriaBuilder criteriaBuilder,
                                                                 String tagName, String name,
                                                                 String description, String sortField,
                                                                 String sortDirection) {
        CriteriaQuery<GiftCertificateEntity> query = criteriaBuilder.createQuery(GiftCertificateEntity.class);
        Root<GiftCertificateEntity> certificate = query.from(GiftCertificateEntity.class);
        Predicate[] searchRestrictions = createSearchRestrictions(
                criteriaBuilder, certificate, tagName, name, description);
        Optional<Order> optionalSortOrder = formSortOrder(criteriaBuilder, certificate, sortField, sortDirection);
        if (optionalSortOrder.isPresent()) {
            query.select(certificate).where(searchRestrictions).orderBy(optionalSortOrder.get());
        } else {
            query.select(certificate).where(searchRestrictions);
        }
        return query;
    }

    private Predicate[] createSearchRestrictions(CriteriaBuilder criteriaBuilder,
                                                 Root<GiftCertificateEntity> certificate,
                                                 String tagName, String name, String description) {
        List<Predicate> restrictions = new ArrayList<>();
        if (name != null) {
            Predicate nameLike = criteriaBuilder.like(certificate.get("name"), "%" + name + "%");
            restrictions.add(nameLike);
        }
        if (description != null) {
            Predicate descriptionLike = criteriaBuilder.like(certificate.get("description"),
                    "%" + description + "%");
            restrictions.add(descriptionLike);
        }
        Join<GiftCertificateEntity, TagEntity> tags = certificate.join("tags");
        if (tagName != null) {
            Predicate tagNameEqual = criteriaBuilder.equal(tags.get("name"), tagName);
            restrictions.add(tagNameEqual);
        }
        return restrictions.toArray(new Predicate[0]);
    }

    private Optional<Order> formSortOrder(CriteriaBuilder criteriaBuilder,
                                          Root<GiftCertificateEntity> certificate,
                                          String sortField, String sortDirection) {
        if ((sortField == null) && (sortDirection == null)) {
            return Optional.empty();
        }
        SortDirection direction = formSortDirection(sortDirection);
        Expression<GiftCertificateEntity> orderExpression = formSortOrderExpression(certificate, sortField);
        return Optional.of(direction.equals(SortDirection.ASC)
                ? criteriaBuilder.asc(orderExpression)
                : criteriaBuilder.desc(orderExpression));
    }

    private SortDirection formSortDirection(String sortDirection) {
        if (sortDirection == null) {
            return SortDirection.ASC;
        }
        return Arrays.stream(SortDirection.values())
                .filter(value -> value.name().toLowerCase().equals(sortDirection))
                .findFirst()
                .orElseThrow(() -> new EnumConstantNotPresentException(SortDirection.class, sortDirection));
    }

    private Expression<GiftCertificateEntity> formSortOrderExpression(Root<GiftCertificateEntity> certificate,
                                                                      String sortField) {
        if (SortField.sortFields.contains(sortField)) {
            return certificate.get(sortField);
        } else {
            throw new UnknownSortFieldException(sortField);
        }
    }
}

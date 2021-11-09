package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.sort.SortDirection;
import com.epam.esm.dao.sort.SortField;
import com.epam.esm.dao.sort.UnknownSortFieldException;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
 * Implementation of interface {@link GiftCertificateDao}
 * for presenting access to repository operations with Gift Certificate .
 */
@Repository
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificate>
        implements GiftCertificateDao {

    /**
     * Constructs class <code>GiftCertificateDaoImpl</code>
     * with injected entity manager .
     *
     * @param entityManager {@link #entityManager}
     */
    @Autowired
    public GiftCertificateDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<GiftCertificate> retrieveEntityClass() {
        return GiftCertificate.class;
    }

    @Override
    public void update(GiftCertificate entity) {
        entityManager.merge(entity);
    }

    @Override
    public List<GiftCertificate> search(String tagName, String name, String description,
                                        String sortField, String sortDirection) {
        CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificate = query.from(retrieveEntityClass());
        Predicate[] searchRestrictions = createSearchRestrictions(certificate, tagName, name, description);
        Optional<Order> optionalSortOrder = formSortOrder(criteriaBuilder, certificate, sortField, sortDirection);
        if (optionalSortOrder.isPresent()) {
            query.select(certificate).where(searchRestrictions).orderBy(optionalSortOrder.get());
        } else {
            query.select(certificate).where(searchRestrictions);
        }
        return entityManager.createQuery(query).getResultList();
    }

    private Predicate[] createSearchRestrictions(Root<GiftCertificate> certificate,
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
        Join<GiftCertificate, Tag> tags = certificate.join("tags");
        if (tagName != null) {
            Predicate tagNameEqual = criteriaBuilder.equal(tags.get("name"), tagName);
            restrictions.add(tagNameEqual);
        }
        return restrictions.toArray(new Predicate[0]);
    }

    private Optional<Order> formSortOrder(CriteriaBuilder criteriaBuilder, Root<GiftCertificate> certificate,
                                          String sortField, String sortDirection) {
        if ((sortField == null) && (sortDirection == null)) {
            return Optional.empty();
        }
        SortDirection direction = formSortDirection(sortDirection);
        Expression<GiftCertificate> orderExpression = formSortOrderExpression(certificate, sortField);
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

    private Expression<GiftCertificate> formSortOrderExpression(Root<GiftCertificate> certificate,
                                                                String sortField) {
        if (SortField.sortFields.contains(sortField)) {
            return certificate.get(sortField);
        } else {
            throw new UnknownSortFieldException(sortField);
        }
    }
}

package com.epam.esm.dao.builder;

import com.epam.esm.entity.PageableEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.Arrays;

/**
 * Criteria query builder for repository operation with entities .
 *
 * @param <T> type of entity
 */
public interface QueryBuilder<T> {

    /**
     * Builds criteria query for passed class of entity
     * to extract all  ordered by primary key entities of class .
     *
     * @param entityManager        entity manager for building query
     * @param entityClass          class of entity for querying
     * @param primaryKeyAttributes array of attributes names which store primary key
     * @return criteria query for selecting all entities of class
     * ordered by primary key
     */
    default CriteriaQuery<T> buildGetAllOrderedByPkQuery(EntityManager entityManager,
                                                         Class<T> entityClass,
                                                         String[] primaryKeyAttributes) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        Order[] orders = Arrays.stream(primaryKeyAttributes)
                .map(pk -> criteriaBuilder.asc(root.get(pk))).toArray(Order[]::new);
        return criteriaQuery.select(root).orderBy(orders);
    }

    /**
     * Retrieves requested page with passed quantity of entities
     * from passed query of entities  .
     *
     * @param typedQuery    query that contains list of entities to cut on pages
     * @param pageSize      quantity of entities on page
     * @param pageNumber    page number
     * @param totalEntities total number of entities
     * @return page of entities from passed query with passed params
     */
    default PageableEntity<T> buildPageFromQuery(TypedQuery<T> typedQuery,
                                                 int pageSize, int pageNumber,
                                                 long totalEntities) {
        typedQuery.setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize);
        long totalPages = totalEntities == 0 ? 0 : ((totalEntities + pageSize - 1) / pageSize);
        return PageableEntity.<T>builder()
                .pageSize(pageSize).pageNumber(pageNumber).elements(typedQuery.getResultList())
                .totalElements(totalEntities).totalPages(totalPages).build();
    }
}

package com.epam.esm.mapper;

import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.entity.PageableEntity;

/**
 * Mapper for entity to client model mapping and otherwise .
 *
 * @param <T> entity type
 * @param <S> client model type
 */
public interface Mapper<T, S> {

    /**
     * Maps client model to entity .
     *
     * @param clientModel client model
     * @return entity model
     */
    T toEntity(S clientModel);

    /**
     * Maps entity to client model .
     *
     * @param entity entity model
     * @return client model
     */
    S toClientModel(T entity);

    /**
     * Maps page of entities to page of client models .
     *
     * @param clientModel page of client models
     * @return page of entity models
     */
    PageableEntity<T> toEntity(PageableClientModel<S> clientModel);

    /**
     * Maps page of client models to page of entities.
     *
     * @param entity page of entity models
     * @return page of client models
     */
    PageableClientModel<S> toClientModel(PageableEntity<T> entity);
}

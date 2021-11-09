package com.epam.esm.mapper;

/**
 * Mapper for entity to client model mapping and otherwise .
 *
 * @param <T> entity type
 * @param <S> client model type
 */
public interface EntityModelMapper<T, S> {

    /**
     * Maps client model to entity .
     *
     * @param model client model
     * @return entity model
     */
    T toEntity(S model);

    /**
     * Maps entity to client model .
     *
     * @param entity entity model
     * @return client model
     */
    S toModel(T entity);
}

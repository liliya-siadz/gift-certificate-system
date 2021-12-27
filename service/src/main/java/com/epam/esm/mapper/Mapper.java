package com.epam.esm.mapper;

import com.epam.esm.clientmodel.PageableClientModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.stream.Collectors;

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
     * Maps page of client models to page of entities.
     *
     * @param entity page of entity models
     * @return page of client models
     */
    default PageableClientModel<S> toClientModel(Page<T> entity) {
        return (PageableClientModel<S>) PageableClientModel.builder().totalPages((long) entity.getTotalPages())
                .totalElements(entity.getTotalElements())
                .pageSize(entity.getSize()).pageNumber(entity.getNumber() + 1)
                .elements(entity.getContent().stream().map(this::toClientModel).collect(Collectors.toList()))
                .build();
    }

    /**
     * Maps page of entities to page of client models.
     *
     * @param clientModel page of client models
     * @return page of entities
     */
    default Page<T> toEntity(PageableClientModel<S> clientModel) {
        return new PageImpl<T>(clientModel.getElements().stream().map(this::toEntity).collect(Collectors.toList()),
                PageRequest.of(clientModel.getPageNumber(), clientModel.getPageSize()),
                clientModel.getTotalElements());
    }
}

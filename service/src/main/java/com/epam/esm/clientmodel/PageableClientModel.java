package com.epam.esm.clientmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Client model, presents result of pagination,
 * retrieves one page of resources with additional information,
 * such page size, total pages quantity, etc.
 *
 * @param <S> type of elements presented on page (client models)
 */
@Data
@Builder
@AllArgsConstructor
public class PageableClientModel<S> {

    /**
     * List of elements presented on page .
     */
    private List<S> elements;

    /**
     * Quantity of elements on page .
     */
    private Integer pageSize;

    /**
     * Page number .
     */
    private Integer pageNumber;

    /**
     * Total quantity of elements .
     */
    private Long totalElements;

    /**
     * Total quantity of pages .
     */
    private Long totalPages;
}

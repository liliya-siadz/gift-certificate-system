package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Entity model, presents result of pagination,
 * retrieves one page of elements from database
 * with additional information, such page size, total pages quantity, etc.
 *
 * @param <T> type of elements presented on page
 */
@Data
@Builder
@AllArgsConstructor
public class PageableEntity<T> {

    /**
     * List of elements presented on page .
     */
    private List<T> elements;

    /**
     * Quantity of elements on page .
     */
    private int pageSize;

    /**
     * Page number .
     */
    private int pageNumber;

    /**
     * Total quantity of elements .
     */
    private long totalElements;

    /**
     * Total quantity of pages .
     */
    private long totalPages;
}

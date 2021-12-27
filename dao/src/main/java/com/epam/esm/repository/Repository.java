package com.epam.esm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Base repository interface for interfaces in package {@link com.epam.esm.repository} .
 *
 * @param <T> type of entity interface uses
 */
@NoRepositoryBean
public interface Repository<T> extends PagingAndSortingRepository<T, Long> {

    @Override
    Iterable<T> findAll(Sort sort);

    @Override
    Page<T> findAll(Pageable pageable);

    /**
     * Retrieves class of entity that implements interface .
     *
     * @return class of entity that implements interface
     */
    Class<T> getEntityClass();
}

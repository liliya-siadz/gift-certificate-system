package com.epam.esm.service.impl.builder;

import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Builder for building {@link PageRequest} requests of Gift Certificates .
 */
public interface PageRequestBuilder {

    /**
     * Builds search {@link PageRequest} request by passed filters .
     *
     * @param tagNames      names of Tags that bound to Gift Certificates
     * @param name          part of name of target Gift Certificate
     * @param description   part of description of target Gift Certificate
     * @param sortField     property of sorting Gift Certificate
     * @param sortDirection direction of sorting Gift Certificates
     * @param pageNumber    page number of found result of Users
     * @param pageSize      quantity of Users on a page (page size)
     * @return page request that was build with passed filters .
     */
    PageRequest buildCertificateSearchRequest(List<String> tagNames, String name, String description,
                                              String sortField, String sortDirection,
                                              Integer pageSize, Integer pageNumber);
}

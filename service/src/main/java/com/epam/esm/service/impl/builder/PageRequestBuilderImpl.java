package com.epam.esm.service.impl.builder;

import com.epam.esm.exception.UnknownSortParamException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PageRequestBuilderImpl implements PageRequestBuilder {
    @Override
    public PageRequest buildCertificateSearchRequest(List<String> tagNames, String name,
                                                     String description, String sortField,
                                                     String sortDirection, Integer pageSize,
                                                     Integer pageNumber) {
        Sort.Direction direction;
        if ((sortDirection == null) || (sortDirection.trim().isEmpty())) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Arrays.stream(Sort.Direction.values())
                    .filter(value -> value.name().toLowerCase().equals(sortDirection))
                    .findFirst()
                    .orElseThrow(() -> new UnknownSortParamException(sortDirection));
        }
        PageRequest pageRequest;
        if ((sortField == null) || (sortField.trim().isEmpty())) {
            pageRequest = PageRequest.of(--pageNumber, pageSize);
        } else if (!SortField.sortFields.contains(sortField)) {
            throw new UnknownSortParamException(sortDirection);
        } else {
            pageRequest = PageRequest.of(--pageNumber, pageSize, Sort.by(direction, sortField));
        }
        return pageRequest;
    }
}

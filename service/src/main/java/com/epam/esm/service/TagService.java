package com.epam.esm.service;

import com.epam.esm.clientmodel.TagClientModel;
import org.springframework.stereotype.Service;

/**
 * Presents access to service operations with Tag .
 */
@Service
public interface TagService extends BaseService<TagClientModel> {

    /**
     * Finds most popular Tag of Top User,
     * i.e. gets the most widely used Tag of a User with the highest cost of all Orders .
     *
     * @return most widely used Tag of a User with the highest cost of all Orders
     * if Tag is present, otherwise - null
     */
    TagClientModel findMostPopularTag();
}

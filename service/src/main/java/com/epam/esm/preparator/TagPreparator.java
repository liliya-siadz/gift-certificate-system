package com.epam.esm.preparator;

import com.epam.esm.clientmodel.TagClientModel;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link Preparator} interface,
 * for preparing Tag client models for service operations .
 */
@Component
public class TagPreparator extends Preparator<TagClientModel> {
    @Override
    public void prepareForCreate(TagClientModel model) {
        model.setId(null);
    }
}

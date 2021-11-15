package com.epam.esm.preparator;

import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link Preparator} interface,
 * for preparing Tag client models for service operations .
 */
@Component
public class TagPreparator extends Preparator<TagClientModel> {

    /**
     * Validator for validating client models .
     */
    @Autowired
    private Validator<TagClientModel> validator;

    /**
     * Constructs <code>TagPreparator</code>
     * with passed model validator
     *
     * @param validator {@link #validator}
     */
    public TagPreparator(Validator<TagClientModel> validator) {
        super(validator);
    }

    @Override
    public void prepareForMerge(TagClientModel currentModelState, TagClientModel newModelState) {
    }
}

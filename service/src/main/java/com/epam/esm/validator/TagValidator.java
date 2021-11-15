package com.epam.esm.validator;

import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.exception.InvalidFieldValueException;
import com.epam.esm.validator.group.IdChecks;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Extends {@link Validator}, typed by {@link TagClientModel},
 * validates client model of Tag .
 */
@Component
public class TagValidator extends Validator<TagClientModel> {

    /**
     * Validates tag for relation operations,
     * such as bounding/unbounding from certificate .
     *
     * @param tag Tag client model to validate
     */
    public void validateForRelationOperations(TagClientModel tag) {
        Map<String, Object> validationMap = getValidationMap(tag);
        validationMap.putAll(getValidationMapForGroup(tag, IdChecks.class));
        if (!validationMap.isEmpty()) {
            throw new InvalidFieldValueException(getResourceName(), validationMap);
        }
    }

    @Override
    public String getResourceName() {
        return "Tag";
    }
}

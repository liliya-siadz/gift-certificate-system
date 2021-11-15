package com.epam.esm.validator.constraint;

import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.exception.InvalidFieldValueException;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TagsValidator implements ConstraintValidator<TagsConstraint, List<TagClientModel>> {

    /**
     * Validator for Tag models .
     */
    private final Validator<TagClientModel> tagValidator;

    /**
     * Constructs <code>TagsValidator</code> class .
     */
    public TagsValidator() {
        tagValidator = new TagValidator();
    }

    @Override
    public boolean isValid(List<TagClientModel> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        Set<Object> invalidTagsIds = getInvalidTagsIds(value);
        Set<Object> invalidTagsNames = getInvalidTagsNames(value);
        if ((!invalidTagsIds.isEmpty()) || (!invalidTagsNames.isEmpty())) {
            StringBuilder invalidTagsValues = new StringBuilder("{")
                    .append(invalidTagsNames.isEmpty() ? "" : "names: " + invalidTagsNames)
                    .append(invalidTagsIds.isEmpty() ? "" : " ids: " + invalidTagsIds)
                    .append("}");
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(invalidTagsValues.toString()).addConstraintViolation();
            return false;
        } else {
            return true;
        }
    }

    private Set<Object> getInvalidTagsIds(List<TagClientModel> tags) {
        Set<Object> invalidTagsId = new HashSet<>();
        tags.forEach(tag -> {
            try {
                ((TagValidator) tagValidator).validateForRelationOperations(tag);
            } catch (InvalidFieldValueException exception) {
                Map<String, Object> validationMap = exception.getValidationMap();
                if (validationMap.containsKey("id")) {
                    invalidTagsId.add(validationMap.get("id"));
                }
            }
        });
        return invalidTagsId;
    }

    private Set<Object> getInvalidTagsNames(List<TagClientModel> tags) {
        Set<Object> invalidTagsNames = new HashSet<>();
        tags.forEach(tag -> {
            try {
                ((TagValidator) tagValidator).validateForRelationOperations(tag);
            } catch (InvalidFieldValueException exception) {
                Map<String, Object> validationMap = exception.getValidationMap();
                if (validationMap.containsKey("name")) {
                    String invalidTagName = validationMap.get("name").toString();
                    if ((invalidTagName.isEmpty()) || (invalidTagName.trim().isEmpty())) {
                        invalidTagsNames.add(Validator.EMPTY_OR_BLANK_VALUE_MARKER);
                    } else {
                        invalidTagsNames.add(invalidTagName);
                    }
                }
            }
        });
        return invalidTagsNames;
    }
}

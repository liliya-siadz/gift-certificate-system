package com.epam.esm.validator;

import com.epam.esm.clientmodel.AbstractClientModel;
import com.epam.esm.exception.InvalidFieldValueException;
import com.epam.esm.validator.group.CreateChecks;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Validator for service operations with client models .
 *
 * @param <S> model that must extend class {@link AbstractClientModel}
 */
public abstract class Validator<S extends AbstractClientModel> {

    /**
     * Marker-replacement if value was empty or blank .
     */
    public static final String EMPTY_OR_BLANK_VALUE_MARKER = "[EMPTY OR BLANK]";

    /**
     * Validates client model for update operations .
     *
     * @param clientModel client model to process validation
     */
    public void validateForUpdate(S clientModel) {
        final Map<String, Object> validationMap = getValidationMap(clientModel);
        if (!validationMap.isEmpty()) {
            throw new InvalidFieldValueException(getResourceName(), validationMap);
        }
    }

    /**
     * Validates client model for create operations .
     *
     * @param clientModel client model to process validation
     */
    public void validateForCreate(S clientModel) {
        final Map<String, Object> validationMap = getValidationMapForGroup(clientModel, CreateChecks.class);
        validationMap.putAll(getValidationMap(clientModel));
        if (!validationMap.isEmpty()) {
            throw new InvalidFieldValueException(getResourceName(), validationMap);
        }
    }

    /**
     * Retrieves name of resource presented by client model.
     *
     * @return name of resource presented by client model
     */
    public abstract String getResourceName();

    /**
     * Provides validation map using passed group of validators .
     *
     * @param clientModel client model to validate
     * @param group       group of validators to use while validation
     * @return validation map, where key - name of invalid field,
     * value - value of invalid field
     */
    protected Map<String, Object> getValidationMapForGroup(S clientModel, Class<?> group) {
        Set<ConstraintViolation<S>> constraintViolations = getValidator().validate(clientModel, group);
        return processConstraintViolations(constraintViolations);
    }

    /**
     * Provides validation map .
     *
     * @param clientModel client model to validate
     * @return validation map, where key - name of invalid field,
     * value - value of invalid field
     */
    protected Map<String, Object> getValidationMap(S clientModel) {
        Set<ConstraintViolation<S>> constraintViolations = getValidator().validate(clientModel);
        return processConstraintViolations(constraintViolations);
    }

    /**
     * Provides validator from validator factory .
     *
     * @return validator from validator factory
     */
    protected javax.validation.Validator getValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    private Map<String, Object> processConstraintViolations(Set<ConstraintViolation<S>> constraintViolations) {
        if (constraintViolations.size() > 0) {
            return constraintViolations.stream()
                    .collect(Collectors.toMap(key -> key.getPropertyPath().toString(),
                            value -> {
                                String invalidValue = value.getInvalidValue().toString();
                                return ((invalidValue.isEmpty()) || (invalidValue.trim().isEmpty()))
                                        ? EMPTY_OR_BLANK_VALUE_MARKER : invalidValue;
                            }, (key1, key2) -> key1));
        } else {
            return new HashMap<>();
        }
    }
}

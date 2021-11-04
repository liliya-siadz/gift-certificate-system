package com.epam.esm.exception;

import java.util.Map;

/**
 * Thrown to indicate that resource client model contains invalid value .
 */
public class InvalidFieldValueException extends RuntimeException {

    /**
     * Resource name .
     */
    private final String resourceName;

    /**
     * Validation map of resource's fields, where key is name of invalid field,
     * value - value of invalid field .
     */
    private final Map<String, Object> validationMap;

    /**
     * Constructs an <code>InvalidFieldValueException</code>
     * with name of field with invalid value .
     *
     * @param resourceName {@link #resourceName}
     * @param validationMap {@link #validationMap}
     */
    public InvalidFieldValueException(String resourceName, Map<String, Object> validationMap) {
        super();
        this.resourceName = resourceName;
        this.validationMap = validationMap;
    }

    /**
     * Retrieves error message key .
     *
     * @return error message key of exception
     */
    public String getErrorMessageKey() {
        return "invalid_field_value";
    }

    /**
     * Retrieves validation map of resource.
     *
     * @return validation map of resource
     */
    public Map<String, Object> getValidationMap() {
        return validationMap;
    }

    /**
     * Retrieves resource name .
     */
    public String getResourceName() {
        return resourceName;
    }
}

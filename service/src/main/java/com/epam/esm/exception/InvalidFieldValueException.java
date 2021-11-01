package com.epam.esm.exception;

/**
 * Thrown to indicate that client model contains invalid value .
 */
public class InvalidFieldValueException extends RuntimeException {

    /**
     * Name of field with invalid value .
     */
    private final String fieldName;

    /**
     * Constructs an <code>InvalidFieldValueException</code>
     * with name of field with invalid value .
     *
     * @param fieldName {@link #fieldName}
     */
    public InvalidFieldValueException(String fieldName) {
        super();
        this.fieldName = fieldName;
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
     * Retrieves name of field with invalid value .
     *
     * @return name of field with invalid value
     */
    public String getFieldName() {
        return fieldName;
    }
}

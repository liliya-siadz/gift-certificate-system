package com.epam.esm.exception;

public class InvalidFieldValueException extends RuntimeException {
    private final String fieldName;

    public InvalidFieldValueException(String fieldName) {
        super();
        this.fieldName = fieldName;
    }

    public String getErrorMessageKey() {
        return "invalid_field_value";
    }

    public String getFieldName() {
        return fieldName;
    }
}

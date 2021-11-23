package com.epam.esm.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Validates constraint {@link Iso8601LocalDateTime} for object type of {@link String} .
 */
public class Iso8601LocalDateTimeValidator implements ConstraintValidator<Iso8601LocalDateTime, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        try {
            LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return true;
        } catch (DateTimeParseException exception) {
            return false;
        }
    }
}

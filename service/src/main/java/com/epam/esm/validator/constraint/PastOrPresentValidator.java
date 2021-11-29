package com.epam.esm.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Validates constraint {@link PastOrPresent} for object type of {@link String} .
 */
public class PastOrPresentValidator implements ConstraintValidator<PastOrPresent, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                .isBefore(LocalDateTime.now());
    }
}

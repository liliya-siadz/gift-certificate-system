package com.epam.esm.validator.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that passed string has format of
 * {@link java.time.format.DateTimeFormatter#ISO_LOCAL_DATE_TIME} .
 */
@Documented
@Constraint(validatedBy = IsoLocalDateTimeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsoLocalDateTimeConstraint {
    String message() default "Passed datetime should be in format of 8601.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

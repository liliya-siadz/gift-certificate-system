package com.epam.esm.validator.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotated element must be a string instant
 * datetime in format of {@link java.time.format.DateTimeFormatter#ISO_LOCAL_DATE_TIME} .
 */
@Documented
@Constraint(validatedBy = Iso8601LocalDateTimeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Iso8601LocalDateTime {
    String message() default "Passed datetime should be in format of 8601.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

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
 * datetime in the past or in the present.
 */
@Documented
@Constraint(validatedBy = PastOrPresentValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PastOrPresent {
    String message() default "Passed datetime value should be past or present .";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

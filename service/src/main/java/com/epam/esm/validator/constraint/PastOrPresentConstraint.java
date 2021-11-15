package com.epam.esm.validator.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that passed string presented datetime
 * is in past or present time .
 */
@Documented
@Constraint(validatedBy = PastOrPresentValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PastOrPresentConstraint {
    String message() default "Passed datetime value should be past or present .";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

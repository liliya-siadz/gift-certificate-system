package com.epam.esm.validator.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Special validation for list of Tag resources,
 * validates that passed list doesn't contain duplicates .
 */
@Documented
@Constraint(validatedBy = TagsSetValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TagsSetConstraint {
    String message() default "Tags list should not contain duplicates.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

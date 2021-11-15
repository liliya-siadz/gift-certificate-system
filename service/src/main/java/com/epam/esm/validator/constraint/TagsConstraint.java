package com.epam.esm.validator.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates list of Tag resources,
 * uses {@link com.epam.esm.validator.TagValidator} validator .
 */
@Documented
@Constraint(validatedBy = TagsValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TagsConstraint {
    String message() default "Tags list should not contain invalid tags.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

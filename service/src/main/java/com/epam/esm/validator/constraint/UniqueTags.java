package com.epam.esm.validator.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotated element must be list of Tags,
 * that doesn't contain duplicates .
 */
@Documented
@Constraint(validatedBy = UniqueTagsValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueTags {
    String message() default "Tags list should not contain duplicates.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

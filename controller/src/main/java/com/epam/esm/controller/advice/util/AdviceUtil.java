package com.epam.esm.controller.advice.util;

import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Utility for controller advice {@link com.epam.esm.controller.advice.ControllerAdvice} .
 */
public interface AdviceUtil {

    /**
     * Forms validation map from set of constraint violations .
     *
     * @return map, where key - name of invalid field,
     * value - invalid value of field .
     */
    Map<String, Object> formValidationMap(Set<ConstraintViolation<?>> constraintViolations);

    /**
     * Forms validation map from list of field errors, {@link FieldError} .
     *
     * @return map, where key - name of invalid field,
     * value - invalid value of field .
     */
    Map<String, Object> formValidationMap(List<FieldError> errors);

    /**
     * Forms error response, which used as a result of handling exceptions
     * in class {@link com.epam.esm.controller.advice.ControllerAdvice} .
     *
     * @param errorMessageKey key of error message (to find error message by key)
     * @param locale          request locale (to localize error message)
     * @param params          params of error message (to use while forming error message)
     * @return error response object
     */
    ErrorResponse formErrorResponse(String errorMessageKey, Locale locale, Object[] params);

    /**
     * Retrieves error message from Resource Bundle with params .
     *
     * @param key    key of error message
     * @param locale locale
     * @param params params of error message
     * @return error message from Resource Bundle
     */
    String getErrorMessageResource(String key, Locale locale, Object[] params);

    /**
     * Retrieves error message from Resource Bundle .
     *
     * @param key    key of error message
     * @param locale locale
     * @return error message from Resource Bundle
     */
    String getErrorMessageResource(String key, Locale locale);
}

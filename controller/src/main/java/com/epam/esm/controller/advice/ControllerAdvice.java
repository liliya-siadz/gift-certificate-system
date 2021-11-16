package com.epam.esm.controller.advice;

import com.epam.esm.controller.advice.util.AdviceUtil;
import com.epam.esm.controller.advice.util.ErrorResponse;
import com.epam.esm.exception.ResourceContainsDuplicateValuesException;
import com.epam.esm.exception.ResourceWithIdNotFoundException;
import com.epam.esm.exception.ResourceWithNameExistsException;
import com.epam.esm.exception.UnknownSortParamException;
import com.epam.esm.service.ResourceNames;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.Locale;
import java.util.Map;

/**
 * Handles exceptions from {@link com.epam.esm.exception} package
 * across whole application .
 * <p>
 * Uses object of class {@link ErrorResponse} as response body of exception handling result.
 * Creates response body object with localized message,
 * that is constructed inside handling methods.
 */
@RestControllerAdvice
public class ControllerAdvice {

    /**
     * Service utility for advice, forms error response, validation maps, etc.
     */
    private final AdviceUtil adviceUtil;

    /**
     * Constructs <code>ControllerAdvice</code> class with injected controller advice util .
     *
     * @param adviceUtil {@link #adviceUtil}
     */
    @Autowired
    public ControllerAdvice(AdviceUtil adviceUtil) {
        this.adviceUtil = adviceUtil;
    }

    /**
     * Handles {@link com.epam.esm.exception.ResourceWithIdNotFoundException} exception,
     * ads to the response http status 404 .
     * <p>
     * Extracts exception fields 'errorMessageKey', 'resourceName','resourceId'
     * and uses these values while creating response body object .
     *
     * @param exception handled exception
     * @param locale    request locale
     * @return response body (with localized and parametrized message)
     * as result of exception handling
     */
    @ExceptionHandler(ResourceWithIdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceWithIdNotFoundException(
            ResourceWithIdNotFoundException exception, Locale locale) {
        Object[] messageParams = new Object[]{exception.getResourceName(), exception.getResourceId()};
        return adviceUtil.formErrorResponse(exception.getErrorMessageKey(), locale, messageParams);
    }

    /**
     * Handles {@link ResourceWithNameExistsException} exception,
     * ads to the response http status 409 .
     * <p>
     * Extracts exception fields 'errorMessageKey', 'resourceName', 'name'
     * and uses these values while creating response body object .
     *
     * @param exception handled exception
     * @param locale    request locale
     * @return response body (with localized and parametrized message)
     * as result of exception handling
     */
    @ExceptionHandler(ResourceWithNameExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleResourceIsAlreadyExistException(
            ResourceWithNameExistsException exception, Locale locale) {
        Object[] messageParams = new Object[]{exception.getResourceName(), exception.getNameValue()};
        return adviceUtil.formErrorResponse(exception.getErrorMessageKey(), locale, messageParams);
    }

    /**
     * Handles {@link UnknownSortParamException} exception,
     * ads to the response http status 400 .
     * <p>
     * Extracts exception fields 'errorMessageKey', 'sortParamValue'
     * and uses these values while creating response body object .
     *
     * @param exception handled exception
     * @param locale    request locale
     * @return response body (with localized and parametrized message)
     * as result of exception handling
     */
    @ExceptionHandler(UnknownSortParamException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUnknownSortParamException(
            UnknownSortParamException exception, Locale locale) {
        Object[] messageParams = new Object[]{exception.getSortParamValue()};
        return adviceUtil.formErrorResponse(exception.getErrorMessageKey(), locale, messageParams);
    }

    /**
     * Handles {@link InvalidFormatException} exception,
     * ads to the response http status 422 .
     * <p>
     * Extracts exception fields 'value' and 'targetType'
     * and uses these values while creating response body object .
     *
     * @param exception handled exception
     * @param locale    request locale
     * @return response body (with localized and parametrized message)
     * as result of exception handling
     */
    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleInvalidFormatException(
            InvalidFormatException exception, Locale locale) {
        Object[] messageParams = new Object[]{exception.getValue(), exception.getTargetType().getSimpleName()};
        String errorMessageKey = "mismatched_request_body_types";
        return adviceUtil.formErrorResponse(errorMessageKey, locale, messageParams);
    }

    /**
     * Handles {@link ResourceContainsDuplicateValuesException} exception,
     * ads to the response http status 422 .
     * <p>
     * Extracts exception fields 'resourceName' and 'duplicatesInfo'
     * and uses these values while creating response body object .
     *
     * @param exception handled exception
     * @param locale    request locale
     * @return response body (with localized and parametrized message)
     * as result of exception handling
     */
    @ExceptionHandler(ResourceContainsDuplicateValuesException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleResourceContainsDuplicatesValuesException(
            ResourceContainsDuplicateValuesException exception, Locale locale) {
        Object[] messageParams = new Object[]{exception.getResourceName(), exception.getDuplicatesInfo()};
        String errorMessageKey = exception.getErrorMessageKey();
        return adviceUtil.formErrorResponse(errorMessageKey, locale, messageParams);
    }

    /**
     * Handles {@link MethodArgumentNotValidException} exception (which thrown if resource has invalid values) ,
     * ads to the response http status 422 .
     * <p>
     * Extracts exception fields 'fieldErrors' and 'parameter'
     * and uses these values while creating response body object .
     *
     * @param exception handled exception
     * @param locale    request locale
     * @return response body (with localized and parametrized message)
     * as result of exception handling
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception, Locale locale) {
        Map<String, Object> validationMap = adviceUtil.formValidationMap(exception.getFieldErrors());
        String resourceName  = ResourceNames.getResourceName(exception.getParameter().getParameterType());
        Object[] messageParams = new Object[]{resourceName, validationMap};
        String errorMessageKey = "invalid_field_values";
        return adviceUtil.formErrorResponse(errorMessageKey, locale, messageParams);
    }

    /**
     * Handles {@link ConstraintViolationException} exception (which thrown if invalid path variable was passed) ,
     * ads to the response http status 400 .
     * <p>
     * Extracts exception field 'constraintViolations'
     * and uses this value while creating response body object .
     *
     * @param exception handled exception
     * @param locale    request locale
     * @return response body (with localized and parametrized message)
     * as result of exception handling
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(
            ConstraintViolationException exception, Locale locale) {
        Map<String, Object> validationMap = adviceUtil.formValidationMap(exception.getConstraintViolations());
        Object[] messageParams = new Object[]{validationMap};
        String errorMessageKey = "invalid_path_variable_or_request_param_value";
        return adviceUtil.formErrorResponse(errorMessageKey, locale, messageParams);
    }

    /**
     * Handles {@link MethodArgumentTypeMismatchException} exception (which thrown if path variable value has mit) ,
     * ads to the response http status 400 .
     * <p>
     * Extracts exception field 'name', 'parameter'
     * and uses this value while creating response body object .
     *
     * @param exception handled exception
     * @param locale    request locale
     * @return response body (with localized and parametrized message)
     * as result of exception handling
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception, Locale locale) {
        Object[] messageParams = new Object[]{exception.getName(), exception.getValue(),
                exception.getParameter().getParameterType().getSimpleName()};
        String errorMessageKey = "invalid_path_variable_type";
        return adviceUtil.formErrorResponse(errorMessageKey, locale, messageParams);
    }

    /**
     * Handles {@link HttpMessageNotReadableException} exception (if request body of resource is invalid) ,
     * ads to the response http status 400 .
     * <p>
     * Extracts exception field 'message' and uses this value while creating response body object .
     *
     * @param exception handled exception
     * @param locale    request locale
     * @return response body (with localized and parametrized message)
     * as result of exception handling
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception, Locale locale) {
        Object[] messageParams = new Object[]{exception.getMessage()};
        String errorMessageKey = "invalid_request_body";
        return adviceUtil.formErrorResponse(errorMessageKey, locale, messageParams);
    }
}

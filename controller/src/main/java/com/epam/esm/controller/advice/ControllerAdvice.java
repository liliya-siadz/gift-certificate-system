package com.epam.esm.controller.advice;

import com.epam.esm.exception.InvalidFieldValueException;
import com.epam.esm.exception.ResourceWithNameExistsException;
import com.epam.esm.exception.ResourceWithIdNotFoundException;
import com.epam.esm.exception.UnknownSortParamException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

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
     * Handles {@link com.epam.esm.exception.InvalidFieldValueException} exception,
     * ads to the response http status 400 .
     * <p>
     * Extracts exception field 'validationMap'
     * and this value while creating response body object .
     *
     * @param exception handled exception
     * @param locale    request locale
     * @return response body (with localized and parametrized message)
     * as result of exception handling
     */
    @ExceptionHandler(InvalidFieldValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidFieldValueException(
            InvalidFieldValueException exception, Locale locale) {
        Object[] messageParams = new Object[]{exception.getResourceName(), exception.getValidationMap()};
        return formErrorResponse(exception.getErrorMessageKey(), locale, messageParams);
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
        return formErrorResponse(exception.getErrorMessageKey(), locale, messageParams);
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
        return formErrorResponse(exception.getErrorMessageKey(), locale, messageParams);
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
        return formErrorResponse(exception.getErrorMessageKey(), locale, messageParams);
    }

    /**
     * Handles {@link HttpMessageNotReadableException} exception,
     * ads to the response http status 422 .
     * <p>
     * Extracts exception field 'httpInputMessage'
     * and uses its values while creating response body object .
     *
     * @param exception handled exception
     * @param locale    request locale
     * @return response body (with localized and parametrized message)
     * as result of exception handling
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception, Locale locale) {
        Object[] messageParams = new Object[]{exception.getMostSpecificCause().getMessage()};
        String errorMessageKey = "invalid_request_body_types";
        return formErrorResponse(errorMessageKey, locale, messageParams);
    }

    /**
     * Handles {@link NumberFormatException} exception,
     * ads to the response http status 400 .
     * <p>
     * Extracts exception field 'message'
     * and uses this value while creating response body object .
     *
     * @param exception handled exception
     * @param locale    request locale
     * @return response body (with localized and parametrized message)
     * as result of exception handling
     */
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNumberFormatException(
            NumberFormatException exception, Locale locale) {
        Object[] messageParams = new Object[]{exception.getMessage()};
        String errorMessageKey = "invalid_path_variable_value";
        return formErrorResponse(errorMessageKey, locale, messageParams);
    }

    private ErrorResponse formErrorResponse(String errorMessageKey, Locale locale, Object[] params) {
        int errorCode = Integer.parseInt(getErrorMessageResource(errorMessageKey.concat(".code"), locale));
        String errorMessage = getErrorMessageResource(errorMessageKey.concat(".message"), locale, params);
        return new ErrorResponse(errorCode, errorMessage);
    }

    private String getErrorMessageResource(String key, Locale locale, Object[] params) {
        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(locale);
        formatter.applyPattern(getErrorMessageResource(key, locale));
        return formatter.format(params);
    }

    private String getErrorMessageResource(String key, Locale locale) {
        return ResourceBundle.getBundle("com.epam.esm.error_messages", locale).getString(key);
    }
}

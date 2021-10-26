package com.epam.esm.controller.advice;

import com.epam.esm.exception.InvalidFieldValueException;
import com.epam.esm.exception.ResourceWithIdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(InvalidFieldValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleInvalidFieldValueException(
            InvalidFieldValueException exception, Locale locale) {
        Object[] messageParams = new Object[]{exception.getFieldName()};
        return formCustomResponse(exception.getErrorMessageKey(), locale, messageParams);
    }

    @ExceptionHandler(ResourceWithIdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomResponse handleResourceWithIdNotFoundException(
            ResourceWithIdNotFoundException exception, Locale locale) {
        Object[] messageParams = new Object[]{exception.getResourceName(), exception.getResourceId()};
        return formCustomResponse(exception.getErrorMessageKey(), locale, messageParams);
    }

    private CustomResponse formCustomResponse(String errorMessageKey, Locale locale, Object[] params) {
        int errorCode = Integer.parseInt(getErrorMessageResource(errorMessageKey.concat(".code"), locale));
        String errorMessage = getErrorMessageResource(errorMessageKey.concat(".message"), locale, params);
        return new CustomResponse(errorCode, errorMessage);
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

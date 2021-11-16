package com.epam.esm.controller.advice.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Implementation of interface {@link AdviceUtil},
 * utility for controller advice class {@link com.epam.esm.controller.advice.ControllerAdvice} .
 */
@Component
public class AdviceUtilImpl implements AdviceUtil {
    @Override
    public Map<String, Object> formValidationMap(Set<ConstraintViolation<?>> constraintViolations) {
        return constraintViolations.stream()
                .collect(Collectors.toMap(key->key.getPropertyPath().toString().replaceAll(".*[.]",""),
                ConstraintViolation::getInvalidValue, (key1, key2)->key1));
    }

    @Override
    public Map<String, Object> formValidationMap(List<FieldError> errors) {
        return new TreeMap<>(errors.stream()
                .collect(Collectors.toMap(FieldError::getField,
                        value->{
                          if (value.getRejectedValue() == null) {
                              return "[NULL VALUE]";
                          }
                          String temp = value.getRejectedValue().toString();
                          if (temp.isEmpty() || temp.trim().isEmpty()) {
                              return "[EMPTY OR BLANK VALUE]";
                          }
                          return value.getRejectedValue();
                        } ,
                        (key1, key2) -> key1)));
    }

    @Override
    public ErrorResponse formErrorResponse(String errorMessageKey, Locale locale, Object[] params) {
        int errorCode = Integer.parseInt(getErrorMessageResource(errorMessageKey.concat(".code"), locale));
        String errorMessage = getErrorMessageResource(errorMessageKey.concat(".message"), locale, params);
        return new ErrorResponse(errorCode, errorMessage);
    }

    @Override
    public String getErrorMessageResource(String key, Locale locale, Object[] params) {
        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(locale);
        formatter.applyPattern(getErrorMessageResource(key, locale));
        return formatter.format(params);
    }

    @Override
    public String getErrorMessageResource(String key, Locale locale) {
        return ResourceBundle.getBundle("com.epam.esm.error_messages", locale).getString(key);
    }
}

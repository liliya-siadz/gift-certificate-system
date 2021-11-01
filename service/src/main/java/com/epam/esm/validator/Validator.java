package com.epam.esm.validator;

import com.epam.esm.model.AbstractClientModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator for client models
 * @param <T> model that must extends class {@link com.epam.esm.model.AbstractClientModel}
 */
public interface Validator<T extends AbstractClientModel> {

    String AT_LEAST_ONE_LETTER_SET_REGEX_PATTERN = ".*[a-zA-Z]+.*";

    /**
     * Validate client model for update operations
     * @param clientModel client model to process validation
     */
    void isValidForUpdate(T clientModel);

    /**
     * Validate client model for create operations
     * @param clientModel client model to process validation
     */
    void isValidForCreate(T clientModel);

    static boolean isMatchToRegex(String value, String regexPattern) {
        if ((value == null) || (regexPattern == null)) {
            throw new IllegalArgumentException("Value or regexPattern is null!");
        }
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}

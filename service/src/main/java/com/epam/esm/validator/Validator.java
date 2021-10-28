package com.epam.esm.validator;

import com.epam.esm.model.AbstractClientModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Validator<T extends AbstractClientModel> {
    String AT_LEAST_ONE_LETTER_SET_REGEX_PATTERN = ".*[a-zA-Z]+.*";

    void isValidForUpdate(T clientModel);

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

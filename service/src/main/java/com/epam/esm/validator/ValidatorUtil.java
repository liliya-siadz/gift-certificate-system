package com.epam.esm.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {
    static final String AT_LEAST_ONE_LETTER_SET_REGEX_PATTERN = ".*[a-zA-Z]+.*";

    private ValidatorUtil() {
    }

    static boolean isMatchToRegex(String value, String regexPattern) {
        if ((value == null) || (regexPattern == null)) {
            throw new IllegalArgumentException("Value or regexPattern is null!");
        }
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}

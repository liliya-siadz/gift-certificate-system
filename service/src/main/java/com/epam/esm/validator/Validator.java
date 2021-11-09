package com.epam.esm.validator;

import com.epam.esm.model.AbstractModel;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator for client models .
 *
 * @param <S> model that must extends class {@link AbstractModel}
 */
public abstract class Validator<S extends AbstractModel> {
    static String AT_LEAST_ONE_LETTER_SET_REGEX_PATTERN = ".*[a-zA-Z]+.*";

    /**
     * Validation map, where key is name of invalid field,
     * value - it's invalid value .
     */
    protected Map<String, Object> validationMap;

    /**
     * Set validation map .
     *
     * @param validationMap {@link #validationMap}
     */
    protected void setValidationMap(Map<String, Object> validationMap) {
        this.validationMap = validationMap;
    }

    /**
     * Retrieves copy of validation map .
     *
     * @return copy of validation map
     */
    public Map<String, Object> getValidationMap() {
        return new HashMap<>(validationMap);
    }

    /**
     * Validate client model for update operations .
     *
     * @param clientModel client model to process validation
     */
    public abstract void validateForUpdate(S clientModel);

    /**
     * Validate client model for create operations .
     *
     * @param clientModel client model to process validation
     */
    public abstract void validateForCreate(S clientModel);

    static boolean isMatchToRegex(String value, String regexPattern) {
        if ((value == null) || (regexPattern == null)) {
            throw new IllegalArgumentException("Value or regexPattern is null!");
        }
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}

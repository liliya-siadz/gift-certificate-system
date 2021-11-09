package com.epam.esm.validator;

import com.epam.esm.exception.InvalidFieldValueException;
import com.epam.esm.model.TagModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Implements interface {@link Validator} typed by {@link TagModel},
 * validates client model of Tag .
 */
@Component("tagValidator")
public class TagValidator extends Validator<TagModel> {
    private static final int NAME_MAX_LENGTH = 200;
    private static final int ID_MAX_VALUE = 2147483647;

    @Override
    public void validateForUpdate(TagModel tag) {
        setValidationMap(new HashMap<>());
        if (tag != null) {
            String name = tag.getName();
            Long id = tag.getId();
            if ((name != null) && (!isNameValid(name))) {
                validationMap.put("name", name);
            }
            if ((id != null) && (!isIdValid(id))) {
                validationMap.put("id", id);
            }
        }
        if (!validationMap.isEmpty()) {
            throw new InvalidFieldValueException("Tag", validationMap);
        }
    }

    @Override
    public void validateForCreate(TagModel tag) {
        setValidationMap(new HashMap<>());
        if (tag != null) {
            validateForNullables(tag);
            String name = tag.getName();
            if (!isNameValid(name)) {
                validationMap.put("name", name);
            }
        }
        if (!validationMap.isEmpty()) {
            throw new InvalidFieldValueException("Tag", validationMap);
        }
    }

    boolean isNameValid(String name) {
        return (isMatchToRegex(name, AT_LEAST_ONE_LETTER_SET_REGEX_PATTERN))
                && (name.length() <= NAME_MAX_LENGTH);
    }

    boolean isIdValid(Long id) {
        return (id > 0) && (id <= ID_MAX_VALUE);
    }

    private void validateForNullables(TagModel tag) {
        String name = tag.getName();
        if (name == null) {
            validationMap.put("name", null);
        }
        if (!validationMap.isEmpty()) {
            throw new InvalidFieldValueException("Tag", validationMap);
        }
    }
}

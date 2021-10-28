package com.epam.esm.validator;

import com.epam.esm.exception.InvalidFieldValueException;
import com.epam.esm.model.TagClientModel;
import org.springframework.stereotype.Component;

import static com.epam.esm.validator.Validator.isMatchToRegex;

@Component("tagValidator")
public class TagValidator implements Validator<TagClientModel> {
    private static final int NAME_MAX_LENGTH = 200;
    private static final int ID_MAX_VALUE = 2147483647;

    @Override
    public void isValidForUpdate(TagClientModel tag) {
        if (tag != null) {
            String name = tag.getName();
            Long id = tag.getId();
            if (name != null) {
                validateName(name);
            }
            if (id != null) {
                validateId(id);
            }
        }
    }

    @Override
    public void isValidForCreate(TagClientModel tag) {
        if (tag != null) {
            validateForNullables(tag);
            validateName(tag.getName());
        }
    }

    public void validateName(String name) {
        if (!((isMatchToRegex(name, AT_LEAST_ONE_LETTER_SET_REGEX_PATTERN))
                && (name.length() <= NAME_MAX_LENGTH))) {
            throw new InvalidFieldValueException("name");
        }
    }

    public void validateId(Long id) {
        if (!((id > 0) && (id <= ID_MAX_VALUE))) {
            throw new InvalidFieldValueException("id");
        }
    }

    private void validateForNullables(TagClientModel tag) {
        if (tag.getName() == null) {
            throw new InvalidFieldValueException("name");
        }
    }
}

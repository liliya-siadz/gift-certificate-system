package com.epam.esm.validator;

import com.epam.esm.configuration.TestServiceConfiguration;
import com.epam.esm.exception.InvalidFieldValueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {TestServiceConfiguration.class})
class TagValidatorTest {

    @Autowired
    private TagValidator validator;

    @ParameterizedTest()
    @ValueSource(longs = {21474836478L, 100000000000000L, -100008L, 0L})
    void validateIdShouldThrowInvalidFieldException(Long id) {
        assertThrows(InvalidFieldValueException.class, () -> validator.validateId(id));
    }

    @ParameterizedTest()
    @ValueSource(longs = {10L, 12, 1L, 123L})
    void validateIdShouldNotThrowInvalidFieldException(Long id) {
        try {
            validator.validateId(id);
        } catch (InvalidFieldValueException exception) {
            fail("Should not be thrown InvalidFieldValueException, cause data is valid");
        }
    }

    @ParameterizedTest()
    @ValueSource(strings = {"", "    ", "3456784657890_", "------_____++__"})
    void validateNameShouldThrowInvalidFieldException(String name) {
        assertThrows(InvalidFieldValueException.class, () -> validator.validateName(name));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"Helo", "Tag 1", "3456784657890T",
            "Best regards", "Taaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaag"})
    void validateNameShouldNotThrowInvalidFieldException(String name) {
        try {
            validator.validateName(name);
        } catch (InvalidFieldValueException exception) {
            fail("Should not be thrown InvalidFieldValueException, cause data is valid");
        }
    }

    @Test
    void validateNameShouldThrowInvalidFieldExceptionIfNameTooLong() {
        assertThrows(InvalidFieldValueException.class, () -> validator.validateName(
                String.join("", Collections.nCopies(201, "b"))));
    }
}
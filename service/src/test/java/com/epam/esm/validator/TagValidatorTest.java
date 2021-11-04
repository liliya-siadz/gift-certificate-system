package com.epam.esm.validator;

import com.epam.esm.configuration.TestServiceConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {TestServiceConfiguration.class})
@ActiveProfiles("prod")
class TagValidatorTest {
    @Autowired
    private TagValidator validator;

    @ParameterizedTest()
    @ValueSource(longs = {21474836478L, 100000000000000L, -100008L, 0L})
    void validateIdShouldReturnFalse(Long id) {
        assertFalse(validator.isIdValid(id));
    }

    @ParameterizedTest()
    @ValueSource(longs = {10L, 12, 1L, 123L})
    void validateIdShouldReturnTrue(Long id) {
        assertTrue(validator.isIdValid(id));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"", "    ", "3456784657890_", "------_____++__"})
    void validateNameShouldReturnFalse(String name) {
        assertFalse(validator.isNameValid(name));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"Helo", "Tag 1", "3456784657890T",
            "Best regards", "Taaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaag"})
    void validateNameShouldReturnTrue(String name) {
        assertTrue(validator.isNameValid(name));
    }

    @Test
    void validateNameShouldReturnFalseIfNameTooLong() {
        assertFalse(validator.isNameValid(String.join("", Collections.nCopies(201, "b"))));
    }
}
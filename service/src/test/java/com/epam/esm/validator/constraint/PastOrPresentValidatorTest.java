package com.epam.esm.validator.constraint;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PastOrPresentValidatorTest {
    private PastOrPresentValidator validator = new PastOrPresentValidator();

    @ParameterizedTest()
    @ValueSource(strings = {"2025-08-29T06:12:15.156", "2022-08-29T11:12:15.156", "2023-08-29T06:12:15.156"})
    void isValidShouldReturnFalse(String createDate) {
        assertFalse(validator.isValid(createDate, null));
    }

    @Test
    void isValidShouldReturnTrue() {
        assertTrue(validator.isValid(null, null));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"1999-08-29T06:12:15.156", "1999-08-29T11:12:15.156", "2021-08-29T06:12:15.156"})
    void isValidShouldReturnTrue(String createDate) {
        assertTrue(validator.isValid(createDate, null));
    }
}
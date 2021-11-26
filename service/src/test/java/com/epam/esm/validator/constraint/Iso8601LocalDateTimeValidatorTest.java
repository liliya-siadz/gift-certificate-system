package com.epam.esm.validator.constraint;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Iso8601LocalDateTimeValidatorTest {
   private Iso8601LocalDateTimeValidator validator = new Iso8601LocalDateTimeValidator();

    @ParameterizedTest()
    @ValueSource(strings = {"2018 08 29T06:12:15.156", "2023-08-29T06:12:15.156",
            "20180829061215156", "2018-08-29T", "2018-08-29T06", "06:12:15.156.",
            "2020-08-29 T06:12:15.156"})
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
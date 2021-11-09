package com.epam.esm.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({SpringExtension.class})
@ActiveProfiles("prod")
class GiftCertificateValidatorTest {
    @Autowired
    private GiftCertificateValidator validator;

    @ParameterizedTest()
    @ValueSource(strings = {"", "    ", "3456784657890_", "------_____++__"})
    void validateNameShouldReturnFalse(String name) {
        assertFalse(validator.isNameValid(name));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"Katty Blanch", "K12B23", "3456784657890n",
            "------_____++__G<AAAA>RY__++_____------"})
    void validateNameShouldReturnTrue(String name) {
        assertTrue(validator.isNameValid(name));
    }

    @Test
    void validateNameShouldReturnFalseIfNameTooLong() {
        assertFalse(validator.isNameValid(String.join("", Collections.nCopies(201, "b"))));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"", "    ", "3456784657890_", "------_____++__"})
    void validateDescriptionShouldReturnFalse(String description) {
        assertFalse(validator.isDescriptionValid(description));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"Jamaica internet-surfing in 2021: all-inclusive!", "Swimming",
            "call 3456784657890 for details", "Enjoooooooooooooooooooooooooooooooooooooooy"})
    void validateDescriptionShouldReturnTrue(String description) {
        assertTrue(validator.isDescriptionValid(description));
    }

    @Test
    void validateDescriptionShouldReturnFalseIfNameTooLong() {
        assertFalse(validator.isNameValid(String.join("", Collections.nCopies(2001, "b"))));
    }

    static Stream<BigDecimal> invalidPrices() {
        return Stream.of(new BigDecimal("-2342.3"),
                new BigDecimal("-0.999"),
                new BigDecimal("-7892340234"));
    }

    @ParameterizedTest()
    @MethodSource("invalidPrices")
    void validatePriceShouldReturnFalse(BigDecimal price) {
        assertFalse(validator.isPriceValid(price));
    }

    static Stream<BigDecimal> validPrices() {
        return Stream.of(new BigDecimal("2342.32342344"),
                new BigDecimal("0.999"),
                new BigDecimal("7892340234"));
    }

    @ParameterizedTest()
    @MethodSource("validPrices")
    void validatePriceShouldReturnTrue(BigDecimal price) {
        assertTrue(validator.isPriceValid(price));
    }

    @ParameterizedTest()
    @ValueSource(ints = {0, -24324, 327683})
    void validateDurationShouldReturnFalse(int duration) {
        assertFalse(validator.isDurationValid(duration));
    }

    @ParameterizedTest()
    @ValueSource(ints = {1, 24324, 32768, 10})
    void validateDurationShouldReturnTrue(int duration) {
        assertTrue(validator.isDurationValid(duration));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"2018 08 29T06:12:15.156", "2023-08-29T06:12:15.156",
            "20180829061215156", "2018-08-29T", "2018-08-29T06", "06:12:15.156.",
            "2020-08-29 T06:12:15.156"})
    void validateCreateDateShouldReturnFalse(String createDate) {
        assertFalse(validator.isCreateDateValid(createDate));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"1999-08-29T06:12:15.156", "1999-08-29T11:12:15.156", "2021-08-29T06:12:15.156",
            "0020-08-29T06:12:15.156"})
    void validateCreateDateShouldReturnTrue(String createDate) {
        assertTrue(validator.isCreateDateValid(createDate));
    }
}
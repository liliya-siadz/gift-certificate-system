package com.epam.esm.validator;

import com.epam.esm.configuration.TestServiceConfiguration;
import com.epam.esm.exception.InvalidFieldValueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {TestServiceConfiguration.class})
class GiftCertificateValidatorTest {

    @Autowired
    private GiftCertificateValidator validator;

    @ParameterizedTest()
    @ValueSource(strings = {"", "    ", "3456784657890_", "------_____++__"})
    void validateNameShouldThrowInvalidFieldException(String name) {
        assertThrows(InvalidFieldValueException.class, () -> validator.validateName(name));
    }

    @Test
    void validateNameShouldThrowInvalidFieldExceptionIfNameTooLong() {
        assertThrows(InvalidFieldValueException.class, () -> validator.validateName(
                String.join("", Collections.nCopies(201, "b"))));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"", "    ", "3456784657890_", "------_____++__"})
    void validateDescriptionShouldThrowInvalidFieldException(String description) {
        assertThrows(InvalidFieldValueException.class, () -> validator.validateDescription(description));
    }

    @Test
    void validateDescriptionShouldThrowInvalidFieldExceptionIfNameTooLong() {
        assertThrows(InvalidFieldValueException.class, () -> validator.validateName(
                String.join("", Collections.nCopies(2001, "b"))));
    }

    @ParameterizedTest()
    @MethodSource("invalidPrices")
    void validatePriceShouldThrowInvalidFieldException(BigDecimal price) {
        assertThrows(InvalidFieldValueException.class, () -> validator.validatePrice(price));
    }

    static Stream<BigDecimal> invalidPrices() {
        return Stream.of(new BigDecimal("-2342.3"),
                new BigDecimal("-0"),
                new BigDecimal("-7892340234"));
    }

    @ParameterizedTest()
    @ValueSource(ints = {0,-24324,327683})
    void validateDurationShouldThrowInvalidFieldException(int duration) {
        assertThrows(InvalidFieldValueException.class, () -> validator.validateDuration(duration));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"2018 08 29T06:12:15.156", "2023-08-29T06:12:15.156",
            "20180829061215156", "2018-08-29T", "2018-08-29T06", "06:12:15.156.",
            "2020-08-29T06:12:15.156"})
    void validateCreateDateShouldThrowInvalidFieldException(String createDate) {
        assertThrows(InvalidFieldValueException.class, () -> validator.validateCreateDate(createDate));
    }
}
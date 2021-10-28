package com.epam.esm.validator;

import com.epam.esm.configuration.TestServiceConfiguration;
import com.epam.esm.exception.InvalidFieldValueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {TestServiceConfiguration.class})
class GiftCertificateValidatorTest {

    @Autowired
    private GiftCertificateValidator validator;

    @ParameterizedTest()
    @ValueSource(strings = {"", "    ", "3456784657890_", "------_____++__"})
    void validateNameShouldThrowInvalidFieldException(String name) {
        assertThrows(InvalidFieldValueException.class, () -> validator.validateName(name));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"Katty Blanch", "K12B23", "3456784657890n",
            "------_____++__G<AAAA>RY__++_____------"})
    void validateNameShouldNotThrowInvalidFieldException(String name) {
        try {
            validator.validateName(name);
        } catch (InvalidFieldValueException exception) {
            fail("Should not be thrown InvalidFieldValueException, cause data is valid");
        }
    }

    @Test
    void validateNameShouldThrowInvalidFieldExceptionIfNameTooLong() {
        assertThrows(InvalidFieldValueException.class, ()
                -> validator.validateName(String.join("", Collections.nCopies(201, "b"))));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"", "    ", "3456784657890_", "------_____++__"})
    void validateDescriptionShouldThrowInvalidFieldException(String description) {
        assertThrows(InvalidFieldValueException.class, () -> validator.validateDescription(description));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"Jamaica internet-surfing in 2021: all-inclusive!", "Swimming",
            "call 3456784657890 for details", "Enjoooooooooooooooooooooooooooooooooooooooy"})
    void validateDescriptionNotShouldThrowInvalidFieldException(String description) {
        try {
            validator.validateDescription(description);
        } catch (InvalidFieldValueException exception) {
            fail("Should not be thrown InvalidFieldValueException, cause data is valid");
        }
    }

    @Test
    void validateDescriptionShouldThrowInvalidFieldExceptionIfNameTooLong() {
        assertThrows(InvalidFieldValueException.class,
                () -> validator.validateName(String.join("", Collections.nCopies(2001, "b"))));
    }

    static Stream<BigDecimal> invalidPrices() {
        return Stream.of(new BigDecimal("-2342.3"),
                new BigDecimal("-0.999"),
                new BigDecimal("-7892340234"));
    }

    @ParameterizedTest()
    @MethodSource("invalidPrices")
    void validatePriceShouldThrowInvalidFieldException(BigDecimal price) {
        assertThrows(InvalidFieldValueException.class, () -> validator.validatePrice(price));
    }

    static Stream<BigDecimal> validPrices() {
        return Stream.of(new BigDecimal("2342.32342344"),
                new BigDecimal("0.999"),
                new BigDecimal("7892340234"));
    }

    @ParameterizedTest()
    @MethodSource("validPrices")
    void validatePriceShouldNotThrowInvalidFieldException(BigDecimal price) {
        try {
            validator.validatePrice(price);
        } catch (InvalidFieldValueException exception) {
            fail("Should not be thrown InvalidFieldValueException, cause data is valid");
        }
    }

    @ParameterizedTest()
    @ValueSource(ints = {0, -24324, 327683})
    void validateDurationShouldThrowInvalidFieldException(int duration) {
        assertThrows(InvalidFieldValueException.class, () -> validator.validateDuration(duration));
    }

    @ParameterizedTest()
    @ValueSource(ints = {1, 24324, 32768, 10})
    void validateDurationShouldNotThrowInvalidFieldException(int duration) {
        try {
            validator.validateDuration(duration);
        } catch (InvalidFieldValueException exception) {
            fail("Should not be thrown InvalidFieldValueException, cause data is valid");
        }
    }

    @ParameterizedTest()
    @ValueSource(strings = {"2018 08 29T06:12:15.156", "2023-08-29T06:12:15.156",
            "20180829061215156", "2018-08-29T", "2018-08-29T06", "06:12:15.156.",
            "2020-08-29 T06:12:15.156"})
    void validateCreateDateShouldThrowInvalidFieldException(String createDate) {
        assertThrows(InvalidFieldValueException.class, () -> validator.validateCreateDate(createDate));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"1999-08-29T06:12:15.156", "1999-08-29T11:12:15.156", "2021-08-29T06:12:15.156",
            "0020-08-29T06:12:15.156"})
    void validateCreateDateShouldNotThrowInvalidFieldException(String createDate) {
        try {
            validator.validateCreateDate(createDate);
        } catch (InvalidFieldValueException exception) {
            fail("Should not be thrown InvalidFieldValueException, cause data is valid");
        }
    }
}
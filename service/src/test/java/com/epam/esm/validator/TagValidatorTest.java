package com.epam.esm.validator;

import com.epam.esm.configuration.TestServiceConfiguration;
import com.epam.esm.exception.InvalidFieldValueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {TestServiceConfiguration.class})
class TagValidatorTest {

    @Autowired
    private TagValidator validator;

    @ParameterizedTest()
    @ValueSource(longs ={21474836478L, 10L, -100008L, 0L})
    void validateIdShouldThrowInvalidFieldException(Long id) {
        assertThrows(InvalidFieldValueException.class, () -> validator.validateId(id));
    }

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
}
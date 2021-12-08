package com.epam.esm.preparator;

import com.epam.esm.clientmodel.UserClientModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = UserPreparator.class)
class UserPreparatorTest {
    @Autowired
    private Preparator<UserClientModel> preparator;

    private UserClientModel user;

    @BeforeEach
    void setUp() {
        user = UserClientModel.builder().id(555L).name("Verginia Pink").build();
    }

    @Test
    void prepareForCreateShouldSetIdToNull() {
        preparator.prepareForCreate(user);
        assertNull(user.getId());
    }

    @Test
    void prepareForCreateShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> preparator.prepareForCreate(null));
    }
}
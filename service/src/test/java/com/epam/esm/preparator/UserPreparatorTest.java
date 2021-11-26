package com.epam.esm.preparator;

import com.epam.esm.clientmodel.UserClientModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = UserPreparator.class)
class UserPreparatorTest {
    @Autowired
    private Preparator<UserClientModel> preparator;

    private UserClientModel user = new UserClientModel(555L, "Verginia Pink");

    @Test
    void prepareForCreateShouldSetIdToNull() {
        preparator.prepareForCreate(user);
        assertNull(user.getId());
    }

    @Test
    void prepareForCreateShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> preparator.prepareForCreate(null));
    }
}
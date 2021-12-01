package com.epam.esm.preparator;

import com.epam.esm.clientmodel.TagClientModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = TagPreparator.class)
class TagPreparatorTest {
    @Autowired
    private Preparator<TagClientModel> preparator;

    private TagClientModel tag = new TagClientModel(555L, "New Tag");

    @Test
    void prepareForCreateShouldSetIdToNull() {
        preparator.prepareForCreate(tag);
        assertNull(tag.getId());
    }

    @Test
    void prepareForCreateShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> preparator.prepareForCreate(null));
    }
}
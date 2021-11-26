package com.epam.esm.validator.constraint;

import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.exception.ResourceContainsDuplicateValuesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UniqueTagsValidatorTest {
    private UniqueTagsValidator validator = new UniqueTagsValidator();

    private List<TagClientModel> listWithDuplicates;
    private List<TagClientModel> emptyList = Collections.emptyList();
    private List<TagClientModel> listWithoutDuplicates;

    @BeforeEach
    void setUp() {
        listWithDuplicates = new ArrayList<>();
        TagClientModel tag1 = new TagClientModel(5L, "New Tag");
        TagClientModel tagDuplicate1 = new TagClientModel(5L, "New Tag 2");
        TagClientModel tagDuplicate2 = new TagClientModel(6L, "New Tag");
        listWithDuplicates.add(tag1);
        listWithDuplicates.add(tagDuplicate1);
        listWithDuplicates.add(tagDuplicate2);

        listWithoutDuplicates = new ArrayList<>();
        TagClientModel tag2 = new TagClientModel(7L, "Tag 2");
        TagClientModel tag3 = new TagClientModel(8L, "Tag 3");
        listWithDuplicates.add(tag1);
        listWithDuplicates.add(tag2);
        listWithDuplicates.add(tag3);

    }

    @Test
    void isValidShouldReturnTrue() {
        assertTrue(validator.isValid(listWithoutDuplicates, null));
    }

    @Test
    void isValidShouldReturnTrueIfNull() {
        assertTrue(validator.isValid(null, null));
    }

    @Test
    void isValidShouldReturnTrueIfEmptyList() {
        assertTrue(validator.isValid(emptyList, null));
    }

    @Test
    void isValidShouldReturnFalse() {
        ResourceContainsDuplicateValuesException exception =
                assertThrows(ResourceContainsDuplicateValuesException.class,
                        () -> validator.isValid(listWithDuplicates, null));
        assertEquals("{ids: [5]names: [New Tag]}", exception.getDuplicatesInfo());
    }
}
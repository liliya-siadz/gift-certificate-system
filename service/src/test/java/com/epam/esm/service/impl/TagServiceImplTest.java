package com.epam.esm.service.impl;

import com.epam.esm.configuration.TestServiceConfiguration;
import com.epam.esm.dao.TagDao;
import com.epam.esm.mapper.TagModelMapper;
import com.epam.esm.model.TagClientModel;
import com.epam.esm.model.TagEntityModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {TestServiceConfiguration.class})
class TagServiceImplTest {

    @Mock
    private TagDao tagDao;
    @Mock
    private TagModelMapper tagModelMapper;
    @InjectMocks
    private TagServiceImpl tagService;

    private static final long TAG_ID = 1;
    private static final String TAG_NAME = "Test Tag 2021";
    private static final int LIST_SIZE = 3;

    private TagEntityModel entityModel;
    private TagClientModel expectedClientModel;
    private List<TagEntityModel> entities;
    private List<TagClientModel> expectedClientModels;

    @BeforeEach
    void setUp() {
        entityModel = new TagEntityModel(TAG_ID, TAG_NAME);
        entities = new ArrayList<>();
        for (int i = 0; i < LIST_SIZE; i++) {
            entities.add(entityModel);
        }
        expectedClientModel = new TagClientModel(TAG_ID, TAG_NAME);
        expectedClientModels = new ArrayList<>();
        for (int i = 0; i < LIST_SIZE; i++) {
            expectedClientModels.add(expectedClientModel);
        }
    }

    @Test
    void findAllShouldReturnResult() {
        doReturn(entities)
                .when(tagDao).findAll();
        doReturn(expectedClientModel)
                .when(tagModelMapper).toClientModel(
                        any(TagEntityModel.class));
        List<TagClientModel> actualClientModels = tagService.findAll();
        assertEquals(expectedClientModels, actualClientModels);
        verify(tagDao).findAll();
        verify(tagModelMapper, times(LIST_SIZE)).toClientModel(entityModel);
    }

    @Test
    void createShouldThrowIllegalArgumentExceptionIfTagNameIsNull() {
        try {
            tagService.create(new TagClientModel());
            fail("IllegalArgumentException should be thrown,"
                    + "cause id of tag is null!");
        } catch (IllegalArgumentException exception) {
            assertEquals("Tag's model or tag's name is null!",
                    exception.getMessage());
        }
    }

    @Test
    void createShouldThrowIllegalArgumentExceptionIfNull() {
        try {
            tagService.create(null);
            fail("IllegalArgumentException should be thrown,"
                    + "cause id of tag is null!");
        } catch (IllegalArgumentException exception) {
            assertEquals("Tag's model or tag's name is null!",
                    exception.getMessage());
        }
    }

    @Test
    void createShouldReturnResult() {
        doReturn(entityModel)
                .when(tagModelMapper).toEntity(any(TagClientModel.class));
        doReturn(TAG_ID)
                .when(tagDao).create(any(TagEntityModel.class));
        TagClientModel actualClientModel = tagService.create(expectedClientModel);
        assertEquals(actualClientModel, expectedClientModel);
        verify(tagModelMapper, times(1)).toEntity(any(TagClientModel.class));
        verify(tagDao, times(1)).create(any(TagEntityModel.class));
    }
}
package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.mapper.TagClientEntityModelMapper;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.nullable;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {TestTagServiceImplConfiguration.class})
class TagServiceImplTest {

    @Mock
    private TagDao tagDao;
    @Mock
    private TagClientEntityModelMapper tagClientEntityModelMapper;
    @InjectMocks
    private TagServiceImpl tagService;

    private static final long TAG_ID = 1;
    private static final String TAG_NAME = "tag1";
    private static final int LIST_SIZE = 3;

    private TagEntityModel expectedTagEntityModel;
    private TagClientModel expectedTagClientModel;
    private List<TagEntityModel> expectedTagEntityModelsList;
    private List<TagClientModel> expectedTagClientModelList;

    @BeforeEach
    void setUp() {
        expectedTagEntityModel = new TagEntityModel(TAG_ID, TAG_NAME);
        expectedTagEntityModelsList = new ArrayList<>();
        for (int i = 0; i < LIST_SIZE; i++) {
            expectedTagEntityModelsList.add(expectedTagEntityModel);
        }
        expectedTagClientModel = new TagClientModel(TAG_ID, TAG_NAME);
        expectedTagClientModelList = new ArrayList<>();
        for (int i = 0; i < LIST_SIZE; i++) {
            expectedTagClientModelList.add(expectedTagClientModel);
        }
    }

    @Test
    void findAll_ListTagClientModel_BaseCall() {
        doReturn(expectedTagEntityModelsList)
                .when(tagDao).findAll();
        doReturn(expectedTagClientModel)
                .when(tagClientEntityModelMapper).entityToClient(
                        any(TagEntityModel.class));
        List<TagClientModel> actualTagClientModelList = tagService.findAll();
        assertEquals(expectedTagClientModelList, actualTagClientModelList);
    }

    @Test
    void findById_TagClientModel_IfTagExists() {
        doReturn(expectedTagEntityModel)
                .when(tagDao).findById(anyLong());
        doReturn(expectedTagClientModel)
                .when(tagClientEntityModelMapper)
                .entityToClient(any(TagEntityModel.class));
        tagService.findById(anyLong());
        TagClientModel actualTagClientModel = tagService.findById(TAG_ID);
        assertEquals(expectedTagClientModel, actualTagClientModel);
    }

    @Test
    void findById_ThrowIllegalArgumentException_IfNull() {
        when(tagService.findById(nullable(Long.class)))
                .thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.findById(nullable(Long.class));
        }, "Id of tag is null!");
    }

    @Test
    void delete_ThrowIllegalArgumentException_IfNull() {
        when(tagService.delete(nullable(Long.class)))
                .thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.delete(nullable(Long.class));
        }, "Id of tag is null!");
    }

    @Test
    void delete_TagClientModel_IfExists() {
        doReturn(expectedTagEntityModel)
                .when(tagDao).delete(anyLong());
        doReturn(expectedTagClientModel)
                .when(tagClientEntityModelMapper).entityToClient(
                        any(TagEntityModel.class));
        TagClientModel actualTagClientModel =
                tagService.delete(TAG_ID);
        assertEquals(expectedTagClientModel, actualTagClientModel);
    }

    @Test
    void create_ThrowIllegalArgumentException_IfTagNameIsNull() {
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
    void create_ThrowIllegalArgumentException_IfNull() {
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
    void create_TagClientModel_IfNonNullTagClientModel() {
        doReturn(TAG_ID)
                .when(tagDao).create(any(TagEntityModel.class));
        doReturn(expectedTagEntityModel)
                .when(tagClientEntityModelMapper).clientToEntity(any(TagClientModel.class));
        TagClientModel actualClientModel = tagService.create(expectedTagClientModel);
        assertEquals(actualClientModel, expectedTagClientModel);
    }
}
package com.epam.esm.service.impl;

import com.epam.esm.configuration.TestServiceConfiguration;
import com.epam.esm.dao.TagDao;
import com.epam.esm.exception.InvalidFieldValueException;
import com.epam.esm.exception.ResourceWithIdNotFoundException;
import com.epam.esm.mapper.TagModelMapper;
import com.epam.esm.model.TagClientModel;
import com.epam.esm.model.TagEntityModel;
import com.epam.esm.validator.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.nullable;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {TestServiceConfiguration.class})
class TagServiceImplTest {
    private static final Long TAG_ID = 1L;
    private static final String TAG_NAME = "Test Tag 2021";
    private static final int LIST_SIZE = 3;
    private static final long GIFT_CERTIFICATE_ID = 10L;

    @Mock
    private TagDao tagDao;
    @Mock
    private TagModelMapper tagModelMapper;
    @Mock
    private Validator<TagClientModel> validator;
    @InjectMocks
    private TagServiceImpl tagService;

    private TagEntityModel entityModel;
    private TagClientModel clientModel;
    private TagClientModel tagForUnbound;
    private List<TagClientModel> tagsForUnbound;

    private List<TagEntityModel> entities;
    private List<TagClientModel> clientModels;
    private List<TagClientModel> nullTagsList;

    @BeforeEach
    void setUpCommons() {
        entityModel = new TagEntityModel(TAG_ID, TAG_NAME);
        entities = new ArrayList<>();
        for (int i = 0; i < LIST_SIZE; i++) {
            entities.add(entityModel);
        }
        clientModel = new TagClientModel(TAG_ID, TAG_NAME);
        clientModels = new ArrayList<>();
        for (int i = 0; i < LIST_SIZE; i++) {
            clientModels.add(clientModel);
        }
        nullTagsList = new ArrayList<>();
        nullTagsList.add(null);
        nullTagsList.add(null);
        nullTagsList.add(clientModel);
    }

    @BeforeEach
    public void setUpTagsForUnbound() {
        tagForUnbound = new TagClientModel(TAG_ID, null);
        tagsForUnbound = new ArrayList<>();
        for (int i = 0; i < LIST_SIZE; i++) {
            tagsForUnbound.add(tagForUnbound);
        }
    }

    @Test
    void findByIdShouldReturnResult() {
        when(tagDao.findById(TAG_ID))
                .thenReturn(Optional.of(entityModel));
        when(tagModelMapper.toClientModel(entityModel))
                .thenReturn(clientModel);
        assertEquals(clientModel, tagService.findById(TAG_ID));
        verify(tagDao).findById(TAG_ID);
        verify(tagModelMapper).toClientModel(entityModel);
    }

    @Test
    void findByIdShouldThrowResourceWithIdNotFoundException() {
        when(tagDao.findById(TAG_ID))
                .thenReturn(Optional.empty());
        assertThrows(ResourceWithIdNotFoundException.class,
                () -> tagService.findById(TAG_ID));
        verify(tagDao).findById(TAG_ID);
        verify(tagModelMapper, never()).toClientModel(entityModel);
    }

    @Test
    void findByIdShouldThrowIllegalArgumentExceptionIfNull() {
        assertThrows(IllegalArgumentException.class,
                () -> tagService.findById(null));
    }

    @Test
    void deleteShouldReturnResult() {
        when(tagDao.isExist(TAG_ID))
                .thenReturn(Boolean.TRUE);
        when(tagDao.findById(TAG_ID))
                .thenReturn(Optional.ofNullable(entityModel));
        when(tagDao.delete(TAG_ID))
                .thenReturn(Boolean.TRUE);
        when(tagModelMapper.toClientModel(entityModel))
                .thenReturn(clientModel);
        assertEquals(clientModel, tagService.delete(TAG_ID));
        verify(tagDao).isExist(TAG_ID);
        verify(tagDao).findById(TAG_ID);
        verify(tagModelMapper).toClientModel(entityModel);
    }

    @Test
    void deleteShouldThrowResourceWithIdNotFoundException() {
        when(tagDao.isExist(TAG_ID))
                .thenReturn(false);
        assertThrows(ResourceWithIdNotFoundException.class,
                () -> tagService.delete(TAG_ID));
        verify(tagDao).isExist(TAG_ID);
        verify(tagDao, never()).delete(TAG_ID);
    }

    @Test
    void deleteShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> tagService.delete(null));
    }

    @Test
    void findAllShouldReturnResult() {
        doReturn(entities)
                .when(tagDao).findAll();
        doReturn(clientModel)
                .when(tagModelMapper).toClientModel(entityModel);
        List<TagClientModel> actualClientModels = tagService.findAll();
        assertEquals(clientModels, actualClientModels);
        verify(tagDao).findAll();
        verify(tagModelMapper, times(LIST_SIZE)).toClientModel(entityModel);
    }

    @Test
    void createShouldThrowIllegalArgumentExceptionIfNull() {
        assertThrows(IllegalArgumentException.class, () -> tagService.create(null));
    }

    @Test
    void createShouldReturnResult() {
        doNothing().when(validator).isValidForCreate(clientModel);
        doReturn(entityModel)
                .when(tagModelMapper).toEntity(clientModel);
        doReturn(TAG_ID)
                .when(tagDao).create(entityModel);
        TagClientModel actualClientModel = tagService.create(clientModel);
        assertEquals(actualClientModel, clientModel);
        verify(tagModelMapper, times(1)).toEntity(clientModel);
        verify(tagDao, times(1)).create(entityModel);
    }

    @Test
    void findAllTagsBoundToGiftCertificateShouldReturnResult() {
        when(tagDao.findAllTagsBoundToGiftCertificate(GIFT_CERTIFICATE_ID))
                .thenReturn(entities);
        when(tagModelMapper.toClientModel(entityModel))
                .thenReturn(clientModel);
        assertEquals(clientModels, tagService.findAllTagsBoundToGiftCertificate(
                GIFT_CERTIFICATE_ID));
        verify(tagDao).findAllTagsBoundToGiftCertificate(GIFT_CERTIFICATE_ID);
        verify(tagModelMapper, times(LIST_SIZE)).toClientModel(entityModel);
    }

    @Test
    void findAllTagsBoundToGiftCertificateShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> tagService.findAllTagsBoundToGiftCertificate(null));
    }

    @Test
    void updateExistingGiftCertificateTagsShouldBoundTags() {
        doNothing().when(validator).isValidForUpdate(clientModel);
        when(tagDao.isExist(TAG_ID))
                .thenReturn(Boolean.TRUE);
        when(tagDao.isTagBoundToGiftCertificate(TAG_ID, GIFT_CERTIFICATE_ID))
                .thenReturn(Boolean.FALSE);
        when(tagDao.boundTagToGiftCertificate(TAG_ID, GIFT_CERTIFICATE_ID))
                .thenReturn(Boolean.TRUE);
        List<TagClientModel> actual = tagService.updateExistingGiftCertificateTags(
                GIFT_CERTIFICATE_ID, clientModels);
        assertEquals(clientModels, actual);
        verify(validator, times(LIST_SIZE)).isValidForUpdate(clientModel);
        verify(tagDao, times(LIST_SIZE)).isExist(TAG_ID);
        verify(tagDao, times(LIST_SIZE)).isTagBoundToGiftCertificate(TAG_ID, GIFT_CERTIFICATE_ID);
        verify(tagDao, times(LIST_SIZE)).boundTagToGiftCertificate(TAG_ID, GIFT_CERTIFICATE_ID);
    }

    @Test
    void updateExistingGiftCertificateTagsShouldUnboundTags() {
        doNothing().when(validator).isValidForUpdate(tagForUnbound);
        when(tagDao.isExist(TAG_ID))
                .thenReturn(Boolean.TRUE);
        when(tagDao.isTagBoundToGiftCertificate(TAG_ID, GIFT_CERTIFICATE_ID))
                .thenReturn(Boolean.TRUE);
        when(tagDao.unboundTagFromGiftCertificate(TAG_ID, GIFT_CERTIFICATE_ID))
                .thenReturn(Boolean.TRUE);
        List<TagClientModel> actual = tagService.updateExistingGiftCertificateTags(
                GIFT_CERTIFICATE_ID, tagsForUnbound);
        List<TagClientModel> expected = Collections.emptyList();
        assertEquals(expected, actual);
        verify(validator, times(LIST_SIZE)).isValidForUpdate(tagForUnbound);
        verify(tagDao, times(LIST_SIZE)).isExist(TAG_ID);
        verify(tagDao, times(LIST_SIZE)).isTagBoundToGiftCertificate(
                TAG_ID, GIFT_CERTIFICATE_ID);
        verify(tagDao, times(LIST_SIZE)).unboundTagFromGiftCertificate
                (TAG_ID, GIFT_CERTIFICATE_ID);
    }

    @Test
    void updateExistingGiftCertificateTagsShouldNotBoundIfAlreadyBound() {
        doNothing().when(validator).isValidForUpdate(clientModel);
        when(tagDao.isExist(TAG_ID))
                .thenReturn(Boolean.TRUE);
        when(tagDao.isTagBoundToGiftCertificate(TAG_ID, GIFT_CERTIFICATE_ID))
                .thenReturn(Boolean.TRUE);
        List<TagClientModel> actual = tagService.updateExistingGiftCertificateTags(
                GIFT_CERTIFICATE_ID, clientModels);
        assertEquals(clientModels, actual);
        verify(tagDao, times(LIST_SIZE)).isExist(TAG_ID);
        verify(tagDao, times(LIST_SIZE)).isTagBoundToGiftCertificate(
                TAG_ID, GIFT_CERTIFICATE_ID);
        verify(tagDao, never()).boundTagToGiftCertificate(TAG_ID, GIFT_CERTIFICATE_ID);
    }

    @Test
    void updateExistingGiftCertificateTagsShouldNotUnboundIfAlreadyUnbound() {
        doNothing().when(validator).isValidForUpdate(tagForUnbound);
        when(tagDao.isExist(TAG_ID))
                .thenReturn(Boolean.TRUE);
        when(tagDao.isTagBoundToGiftCertificate(TAG_ID, GIFT_CERTIFICATE_ID))
                .thenReturn(Boolean.FALSE);
        List<TagClientModel> actual = tagService.updateExistingGiftCertificateTags(
                GIFT_CERTIFICATE_ID, tagsForUnbound);
        List<TagClientModel> expected = Collections.emptyList();
        assertEquals(expected, actual);
        verify(tagDao, times(LIST_SIZE)).isExist(TAG_ID);
        verify(tagDao, times(LIST_SIZE)).isTagBoundToGiftCertificate(TAG_ID, GIFT_CERTIFICATE_ID);
        verify(tagDao, never()).unboundTagFromGiftCertificate(TAG_ID, GIFT_CERTIFICATE_ID);
    }

    @Test
    void updateExistingGiftCertificateTagsShouldThrowInvalidFieldException() {
        doThrow(InvalidFieldValueException.class)
                .when(validator).isValidForUpdate(tagForUnbound);
        assertThrows(InvalidFieldValueException.class,
                () -> tagService.updateExistingGiftCertificateTags(GIFT_CERTIFICATE_ID, tagsForUnbound));
    }

    @Test
    void updateExistingGiftCertificateTagsShouldThrowIllegalArgumentExceptionIfGiftCertificateIdNull() {
        assertThrows(IllegalArgumentException.class,
                () -> tagService.updateExistingGiftCertificateTags(null, clientModels));
    }

    @Test
    void updateExistingGiftCertificateTagsShouldThrowIllegalArgumentExceptionIfTagsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> tagService.updateExistingGiftCertificateTags(GIFT_CERTIFICATE_ID, null));
    }

    @Test
    void updateExistingGiftCertificateTagsShouldThrowIllegalArgumentExceptionIfTagsHasNullValues() {
        assertThrows(IllegalArgumentException.class,
                () -> tagService.updateExistingGiftCertificateTags(GIFT_CERTIFICATE_ID, nullTagsList));
    }

    @Test
    void updateNewGiftCertificateTagsShouldThrowInvalidFieldException() {
        doThrow(InvalidFieldValueException.class)
                .when(validator).isValidForUpdate(clientModel);
        assertThrows(InvalidFieldValueException.class,
                () -> tagService.updateNewGiftCertificateTags(GIFT_CERTIFICATE_ID, clientModels));
    }

    @Test
    void updateNewGiftCertificateTagsShouldThrowIllegalArgumentExceptionIfGiftCertificateIdNull() {
        assertThrows(IllegalArgumentException.class,
                () -> tagService.updateNewGiftCertificateTags(null, clientModels));
    }

    @Test
    void updateNewGiftCertificateTagsShouldThrowIllegalArgumentExceptionIfTagsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> tagService.updateNewGiftCertificateTags(GIFT_CERTIFICATE_ID, null));
    }

    @Test
    void updateNewGiftCertificateTagsShouldThrowIllegalArgumentExceptionIfTagsHasNullValues() {
        assertThrows(IllegalArgumentException.class,
                () -> tagService.updateNewGiftCertificateTags(GIFT_CERTIFICATE_ID, nullTagsList));
    }

    @Test
    void isExistsShouldReturnTrue() {
        when(tagDao.isExist(TAG_ID))
                .thenReturn(Boolean.TRUE);
        assertTrue(tagService.isExist(TAG_ID));
        verify(tagDao).isExist(TAG_ID);
    }

    @Test
    void isExistsShouldReturnFalse() {
        when(tagDao.isExist(TAG_ID))
                .thenReturn(Boolean.FALSE);
        assertFalse(tagService.isExist(TAG_ID));
        verify(tagDao).isExist(TAG_ID);
    }

    @Test
    void isExistShouldReturnFalseIfNull() {
        when(tagDao.isExist(nullable(Long.class)))
                .thenReturn(Boolean.FALSE);
        assertFalse(tagService.isExist(nullable(Long.class)));
        verify(tagDao).isExist(nullable(Long.class));
    }
}
package com.epam.esm.service.impl;

import com.epam.esm.configuration.TestServiceConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.exception.InvalidFieldValueException;
import com.epam.esm.exception.ResourceWithIdNotFoundException;
import com.epam.esm.mapper.GiftCertificateModelMapper;
import com.epam.esm.model.GiftCertificateClientModel;
import com.epam.esm.model.GiftCertificateEntityModel;
import com.epam.esm.model.TagClientModel;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {TestServiceConfiguration.class})
@ActiveProfiles("prod")
class GiftCertificateServiceImplTest {
    private static final long ID = 15L;
    private static final int LIST_SIZE = 3;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;
    @Mock
    private GiftCertificateDao dao;
    @Mock
    private TagService tagService;
    @Mock
    private GiftCertificateModelMapper modelMapper;
    @Mock
    private Validator<GiftCertificateClientModel> validator;

    private GiftCertificateEntityModel entityModel;
    private List<GiftCertificateEntityModel> entities;

    private List<GiftCertificateClientModel> clientModels;
    private List<TagClientModel> tags;
    private GiftCertificateClientModel clientModel;

    @BeforeEach
    void setUpCommons() {
        entityModel = new GiftCertificateEntityModel();
        entityModel.setId(ID);
        entities = new ArrayList<>();
        for (int i = 0; i < LIST_SIZE; i++) {
            entities.add(entityModel);
        }

        clientModel = new GiftCertificateClientModel();
        clientModel.setId(ID);
        tags = new ArrayList<>();
        clientModel.setTags(tags);
        clientModels = new ArrayList<>();
        for (int i = 0; i < LIST_SIZE; i++) {
            clientModels.add(clientModel);
        }
    }

    @Test
    void createShouldThrowIllegalArgumentExceptionIfNull() {
        assertThrows(IllegalArgumentException.class,
                () -> giftCertificateService.create(null));
    }

    @Test
    void createShouldThrowInvalidFieldValueException() {
        doThrow(InvalidFieldValueException.class)
                .when(validator).validateForCreate(clientModel);
        assertThrows(InvalidFieldValueException.class,
                () -> giftCertificateService.create(clientModel));
    }

    @Test
    void createShouldReturnResult() {
        doNothing()
                .when(validator).validateForCreate(clientModel);
        when(modelMapper.toEntity(clientModel))
                .thenReturn(entityModel);
        when(dao.create(entityModel))
                .thenReturn(ID);
        when(tagService.updateNewGiftCertificateTags(ID, tags))
                .thenReturn(tags);
        when(dao.isExist(ID))
                .thenReturn(Boolean.TRUE);
        when(dao.findById(ID))
                .thenReturn(Optional.of(entityModel));
        when(modelMapper.toClientModel(entityModel))
                .thenReturn(clientModel);
        when(tagService.findAllTagsBoundToGiftCertificate(ID))
                .thenReturn(tags);
        GiftCertificateClientModel actual = giftCertificateService.create(clientModel);
        assertEquals(clientModel, actual);
        verify(validator).validateForCreate(clientModel);
        verify(modelMapper).toEntity(clientModel);
        verify(tagService).updateNewGiftCertificateTags(ID, tags);
        verify(dao).isExist(ID);
        verify(dao).findById(ID);
        verify(modelMapper).toClientModel(entityModel);
        verify(tagService).findAllTagsBoundToGiftCertificate(ID);
    }

    @Test
    void findAll() {
        when(dao.findAll())
                .thenReturn(entities);
        when(modelMapper.toClientModel(entityModel))
                .thenReturn(clientModel);
        when(tagService.findAllTagsBoundToGiftCertificate(ID))
                .thenReturn(tags);
        List<GiftCertificateClientModel> actual = giftCertificateService.findAll();
        assertEquals(clientModels, actual);
        verify(dao).findAll();
        verify(modelMapper, times(LIST_SIZE))
                .toClientModel(entityModel);
        verify(tagService, times(LIST_SIZE))
                .findAllTagsBoundToGiftCertificate(ID);
    }

    @Test
    void deleteShouldReturnResult() {
        when(dao.isExist(ID))
                .thenReturn(Boolean.TRUE);
        when(dao.findById(ID))
                .thenReturn(Optional.of(entityModel));
        when(modelMapper.toClientModel(entityModel))
                .thenReturn(clientModel);
        when(tagService.findAllTagsBoundToGiftCertificate(ID))
                .thenReturn(tags);
        when(dao.delete(ID))
                .thenReturn(Boolean.TRUE);
        GiftCertificateClientModel actual = giftCertificateService.delete(ID);
        assertEquals(clientModel, actual);
        verify(dao, atLeast(1)).isExist(ID);
        verify(dao).findById(ID);
        verify(modelMapper).toClientModel(entityModel);
        verify(tagService).findAllTagsBoundToGiftCertificate(ID);
    }

    @Test
    void deleteShouldThrowResourceWithIdNotFoundExceptionIfNotExist() {
        when(dao.isExist(ID))
                .thenReturn(Boolean.FALSE);
        assertThrows(ResourceWithIdNotFoundException.class,
                () -> giftCertificateService.delete(ID));
    }

    @Test
    void deleteShouldThrowIllegalArgumentExceptionIfIdNull() {
        assertThrows(IllegalArgumentException.class,
                () -> giftCertificateService.delete(null));
    }

    @Test
    void findByIdShouldReturnResult() {
        when(dao.isExist(ID))
                .thenReturn(Boolean.TRUE);
        when(dao.findById(ID))
                .thenReturn(Optional.of(entityModel));
        when(modelMapper.toClientModel(entityModel))
                .thenReturn(clientModel);
        when(tagService.findAllTagsBoundToGiftCertificate(ID))
                .thenReturn(anyList());
        GiftCertificateClientModel actual = giftCertificateService.findById(ID);
        assertEquals(clientModel, actual);
        verify(dao).isExist(ID);
        verify(dao).findById(ID);
        verify(modelMapper).toClientModel(entityModel);
        verify(tagService).findAllTagsBoundToGiftCertificate(ID);
    }

    @Test
    void findByIdShouldThrowResourceWithIdNotFoundException() {
        when(dao.isExist(ID)).thenReturn(Boolean.FALSE);
        assertThrows(ResourceWithIdNotFoundException.class,
                () -> giftCertificateService.findById(ID));
    }

    @Test
    void findByIdShouldThrowIllegalArgumentExceptionIfNull() {
        assertThrows(IllegalArgumentException.class,
                () -> giftCertificateService.findById(null));
    }

    @Test
    void updateShouldReturnResult() {
        when(dao.isExist(ID))
                .thenReturn(Boolean.TRUE);
        doNothing().when(validator).validateForUpdate(clientModel);
        when(modelMapper.toEntity(clientModel))
                .thenReturn(entityModel);
        when(dao.update(ID, entityModel))
                .thenReturn(entityModel);
        when(tagService.updateExistingGiftCertificateTags(ID, tags))
                .thenReturn(tags);
        when(dao.isExist(ID))
                .thenReturn(Boolean.TRUE);
        when(dao.findById(anyLong()))
                .thenReturn(Optional.of(entityModel));
        when(modelMapper.toClientModel(entityModel))
                .thenReturn(clientModel);
        when(tagService.findAllTagsBoundToGiftCertificate(ID))
                .thenReturn(anyList());
        GiftCertificateClientModel actual = giftCertificateService.update(ID, clientModel);
        assertEquals(clientModel, actual);
        verify(dao, atLeast(1)).isExist(ID);
        verify(validator).validateForUpdate(clientModel);
        verify(modelMapper).toEntity(clientModel);
        verify(dao).update(ID, entityModel);
        verify(tagService).updateExistingGiftCertificateTags(ID, tags);
        verify(modelMapper).toClientModel(entityModel);
        verify(tagService).findAllTagsBoundToGiftCertificate(ID);
    }

    @Test
    void updateShouldThrowResourceWithIdNotFoundExceptionIfNotExist() {
        when(dao.isExist(ID)).thenReturn(Boolean.FALSE);
        assertThrows(ResourceWithIdNotFoundException.class,
                () -> giftCertificateService.update(ID, clientModel));
    }

    @Test
    void updateShouldThrowIllegalArgumentExceptionIfIdNull() {
        assertThrows(IllegalArgumentException.class,
                () -> giftCertificateService.update(null, clientModel));
    }

    @Test
    void searchShouldReturnResult() {
        when(dao.search(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(entities);
        when(modelMapper.toClientModel(entityModel))
                .thenReturn(clientModel);
        when(tagService.findAllTagsBoundToGiftCertificate(ID))
                .thenReturn(tags);
        List<GiftCertificateClientModel> actual = giftCertificateService.search(
                anyString(), anyString(), anyString(), anyString());
        assertEquals(clientModels, actual);
        verify(modelMapper, times(LIST_SIZE)).toClientModel(entityModel);
        verify(tagService, times(LIST_SIZE)).findAllTagsBoundToGiftCertificate(ID);
    }

    @Test
    void searchShouldReturnResultIfParamsNull() {
        when(dao.search(null, null, null, null))
                .thenReturn(entities);
        when(modelMapper.toClientModel(entityModel))
                .thenReturn(clientModel);
        when(tagService.findAllTagsBoundToGiftCertificate(ID))
                .thenReturn(tags);
        List<GiftCertificateClientModel> actual = giftCertificateService.search(null, null, null, null);
        assertEquals(clientModels, actual);
        verify(modelMapper, times(LIST_SIZE)).toClientModel(entityModel);
        verify(tagService, times(LIST_SIZE)).findAllTagsBoundToGiftCertificate(ID);
    }
}
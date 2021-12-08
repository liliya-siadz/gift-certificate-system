package com.epam.esm.service.impl;

import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.PageableEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.ResourceWithIdNotFoundException;
import com.epam.esm.exception.ResourceWithNameExistsException;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.TagMapperImpl;
import com.epam.esm.preparator.Preparator;
import com.epam.esm.preparator.TagPreparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.nullable;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TagDaoImpl.class, TagMapperImpl.class, TagPreparator.class})
@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @InjectMocks
    private TagServiceImpl service;
    @MockBean
    private TagDao dao;
    @Mock
    private Mapper<TagEntity, TagClientModel> mapper;
    @Mock
    private Preparator<TagClientModel> preparator;

    private long id = 5L;
    private String name = "New Tag";
    private int pageSize = 3;
    private int pageNumber = 1;
    private long certificateId = 4;

    private TagClientModel tagClientModel;
    private PageableClientModel<TagClientModel> tagClientModelPage;
    private List<TagClientModel> clientModelList;

    private PageableEntity<TagEntity> tagEntityPage;
    private TagEntity tagEntity;
    private List<TagEntity> entityTagList;

    @BeforeEach
    void setUpEntities() {
        tagEntity = new TagEntity(id, name);
        List<TagEntity> entityTags = new ArrayList<>();
        entityTags.add(tagEntity);
        entityTags.add(tagEntity);
        entityTags.add(tagEntity);
        tagEntityPage = new PageableEntity(entityTags, pageSize, pageNumber, 3, 1);

        entityTagList = new ArrayList<>();
        entityTagList.add(tagEntity);
        entityTagList.add(tagEntity);
        entityTagList.add(tagEntity);
    }

    @BeforeEach
    void setUpClientModels() {
        tagClientModel = new TagClientModel(id, name);
        List<TagClientModel> clientModelTags = new ArrayList<>();
        clientModelTags.add(tagClientModel);
        clientModelTags.add(tagClientModel);
        clientModelTags.add(tagClientModel);
        tagClientModelPage = new PageableClientModel(clientModelTags, pageSize, pageNumber, 3L, 1L);

        clientModelList = new ArrayList<>();
        clientModelTags.add(tagClientModel);
        clientModelTags.add(tagClientModel);
        clientModelTags.add(tagClientModel);
    }

    @Test
    void findAllShouldThrowIllegalArgumentExceptionIfNullPageSize() {
        assertThrows(IllegalArgumentException.class, () -> service.findAll(null, 1));
    }

    @Test
    void findAllShouldThrowIllegalArgumentExceptionIfNullPageNumber() {
        assertThrows(IllegalArgumentException.class, () -> service.findAll(1, null));
    }

    @Test
    void findAllShouldReturnTagPage() {
        when(dao.findAll(pageSize, pageNumber)).thenReturn(tagEntityPage);
        when(mapper.toClientModel(tagEntityPage)).thenReturn(tagClientModelPage);
        assertEquals(tagClientModelPage, service.findAll(pageSize, pageNumber));
        verify(dao).findAll(pageSize, pageNumber);
        verify(mapper).toClientModel(tagEntityPage);
    }

    @Test
    void findByIdShouldThrowIllegalArgumentExceptionIfNull() {
        assertThrows(IllegalArgumentException.class, () -> service.findById(null));
    }

    @Test
    void findByIdShouldThrowResourceWithIdNotFoundExceptionIfNotFound() {
        when(dao.findById(id)).thenReturn(Optional.empty());
        when(dao.getEntityClass()).thenReturn(TagEntity.class);
        ResourceWithIdNotFoundException exception =
                assertThrows(ResourceWithIdNotFoundException.class, () -> service.findById(id));
        assertEquals(id, exception.getResourceId());
    }

    @Test
    void findByIdShouldReturnResult() {
        when(dao.findById(id)).thenReturn(Optional.of(tagEntity));
        when(mapper.toClientModel(tagEntity)).thenReturn(tagClientModel);
        assertEquals(tagClientModel, service.findById(id));
        verify(dao).findById(id);
        verify(mapper).toClientModel(tagEntity);
    }

    @Test
    void deleteShouldThrowIllegalArgumentExceptionIfNull() {
        assertThrows(IllegalArgumentException.class, () -> service.delete(null));
    }

    @Test
    void deleteShouldThrowResourceWithIdNotFoundExceptionIfNotFound() {
        when(dao.findById(id)).thenReturn(Optional.empty());
        when(dao.getEntityClass()).thenReturn(TagEntity.class);
        ResourceWithIdNotFoundException exception =
                assertThrows(ResourceWithIdNotFoundException.class, () -> service.delete(id));
        assertEquals(id, exception.getResourceId());
    }

    @Test
    void deleteShouldReturnResult() {
        when(dao.findById(id)).thenReturn(Optional.of(tagEntity));
        when(mapper.toClientModel(tagEntity)).thenReturn(tagClientModel);
        doNothing().when(dao).delete(id);
        assertEquals(tagClientModel, service.delete(id));
        verify(dao).findById(id);
        verify(mapper).toClientModel(tagEntity);
        verify(dao).delete(id);
    }

    @Test
    void isExistShouldReturnFalseIfNull() {
        assertFalse(service.isExist(null));
    }

    @Test
    void isExistShouldReturnFalse() {
        when(dao.isExist(id)).thenReturn(false);
        assertFalse(service.isExist(id));
        verify(dao).isExist(id);
    }

    @Test
    void isExistShouldReturnTrue() {
        when(dao.isExist(id)).thenReturn(true);
        assertTrue(service.isExist(id));
        verify(dao).isExist(id);
    }

    @Test
    void createShouldThrowIllegalArgumentExceptionIfNull() {
        assertThrows(IllegalArgumentException.class, () -> service.create(null));
    }

    @Test
    void createShouldThrowResourceWithNameExistsException() {
        doNothing().when(preparator).prepareForCreate(tagClientModel);
        when(mapper.toEntity(tagClientModel)).thenReturn(tagEntity);
        when(dao.getEntityClass()).thenReturn(TagEntity.class);
        when(dao.create(tagEntity)).thenThrow(DataIntegrityViolationException.class);
        ResourceWithNameExistsException exception =
                assertThrows(ResourceWithNameExistsException.class, () -> service.create(tagClientModel));
        assertEquals(name, exception.getNameValue());
        verify(preparator).prepareForCreate(tagClientModel);
        verify(mapper).toEntity(tagClientModel);
        verify(dao).getEntityClass();
        verify(dao).create(tagEntity);
    }

    @Test
    void createShouldReturnResult() {
        doNothing().when(preparator).prepareForCreate(tagClientModel);
        when(mapper.toEntity(tagClientModel)).thenReturn(tagEntity);
        when(dao.create(tagEntity)).thenReturn(tagEntity);
        when(mapper.toClientModel(tagEntity)).thenReturn(tagClientModel);
        assertEquals(tagClientModel, service.create(tagClientModel));
        verify(preparator).prepareForCreate(tagClientModel);
        verify(mapper).toEntity(tagClientModel);
        verify(dao).create(tagEntity);
    }

    @Test
    void findAllTagsBoundToGiftCertificateShouldThrowIllegalArgumentExceptionIfNull() {
        assertThrows(IllegalArgumentException.class, () -> service.findAllTagsBoundToGiftCertificate(null));
    }

    @Test
    void findAllTagsBoundToGiftCertificateShouldReturnResult() {

        when(dao.findAllTagsBoundToGiftCertificate(certificateId)).thenReturn(entityTagList);
        when(mapper.toClientModel(tagEntity)).thenReturn(tagClientModel);
        service.findAllTagsBoundToGiftCertificate(certificateId);
        verify(dao).findAllTagsBoundToGiftCertificate(certificateId);
        verify(mapper, times(entityTagList.size())).toClientModel(tagEntity);
    }

    @Test
    void updateExistingGiftCertificateTagsShouldThrowIllegalArgumentExceptionIfCertificateIdNull() {
        assertThrows(IllegalArgumentException.class,
                () -> service.updateExistingGiftCertificateTags(null, clientModelList));
    }

    @Test
    void updateExistingGiftCertificateTagsShouldThrowIllegalArgumentExceptionIfTagListNull() {
        assertThrows(IllegalArgumentException.class,
                () -> service.updateExistingGiftCertificateTags(certificateId, null));
    }

    @Test
    void updateExistingGiftCertificateTagsShouldThrowIllegalArgumentExceptionIfTagListContainsNull() {
        clientModelList.add(null);
        assertThrows(IllegalArgumentException.class,
                () -> service.updateExistingGiftCertificateTags(certificateId, clientModelList));
    }

    @Test
    void updateExistingGiftCertificateTagsShouldThrowResourceWithIdNotFoundExceptionIsTagNotExist() {
        List<TagClientModel> tagList = new ArrayList<>();
        tagList.add(tagClientModel);
        tagList.add(tagClientModel);
        doReturn(false).when(dao).isExist(id);
        assertThrows(ResourceWithIdNotFoundException.class,
                () -> service.updateExistingGiftCertificateTags(certificateId, tagList));
        verify(dao).isExist(id);
    }

    @Test
    void updateExistingGiftCertificateTagsShouldBoundTags() {
        List<TagClientModel> tagList = new ArrayList<>();
        tagList.add(tagClientModel);
        tagList.add(tagClientModel);
        doReturn(true).when(dao).isExist(id);
        when(dao.isTagBoundToGiftCertificate(id, certificateId)).thenReturn(false);
        doNothing().when(dao).boundTagToGiftCertificate(id, certificateId);
        service.updateExistingGiftCertificateTags(certificateId, tagList);
        verify(dao, times(tagList.size())).isExist(id);
        verify(dao, times(tagList.size())).isTagBoundToGiftCertificate(id, certificateId);
        verify(dao, times(tagList.size())).boundTagToGiftCertificate(id, certificateId);
    }

    @Test
    void updateExistingGiftCertificateTagsShouldUnboundTags() {
        List<TagClientModel> tagList = new ArrayList<>();
        long tagId = 1L;
        TagClientModel tagToUnbound = new TagClientModel(tagId, null);
        tagList.add(tagToUnbound);
        tagList.add(tagToUnbound);
        doReturn(true).when(dao).isExist(tagId);
        when(dao.isTagBoundToGiftCertificate(tagId, certificateId)).thenReturn(true);
        doNothing().when(dao).unboundTagFromGiftCertificate(tagId, certificateId);
        service.updateExistingGiftCertificateTags(certificateId, tagList);
        verify(dao, times(tagList.size())).isExist(tagId);
        verify(dao, times(tagList.size())).isTagBoundToGiftCertificate(tagId, certificateId);
        verify(dao, times(tagList.size())).unboundTagFromGiftCertificate(tagId, certificateId);
    }

    @Test
    void updateExistingGiftCertificateTagsShouldNotBoundTagsIfAlreadyBound() {
        List<TagClientModel> tagList = new ArrayList<>();
        tagList.add(tagClientModel);
        tagList.add(tagClientModel);
        doReturn(true).when(dao).isExist(id);
        when(dao.isTagBoundToGiftCertificate(id, certificateId)).thenReturn(true);
        service.updateExistingGiftCertificateTags(certificateId, tagList);
        verify(dao, times(tagList.size())).isExist(id);
        verify(dao, times(tagList.size())).isTagBoundToGiftCertificate(id, certificateId);
        verify(dao, never()).boundTagToGiftCertificate(id, certificateId);
    }

    @Test
    void updateExistingGiftCertificateTagsShouldNotUnboundTagsIfAlreadyUnbound() {
        List<TagClientModel> tagList = new ArrayList<>();
        long tagId = 1L;
        TagClientModel tagToUnbound = new TagClientModel(tagId, null);
        tagList.add(tagToUnbound);
        tagList.add(tagToUnbound);
        doReturn(true).when(dao).isExist(tagId);
        when(dao.isTagBoundToGiftCertificate(tagId, certificateId)).thenReturn(false);
        service.updateExistingGiftCertificateTags(certificateId, tagList);
        verify(dao, times(tagList.size())).isExist(tagId);
        verify(dao, times(tagList.size())).isTagBoundToGiftCertificate(tagId, certificateId);
        verify(dao, never()).unboundTagFromGiftCertificate(tagId, certificateId);
    }

    @Test
    void updateNewGiftCertificateTagsShouldBoundTags() {
        List<TagClientModel> tagList = new ArrayList<>();
        tagList.add(tagClientModel);
        tagList.add(tagClientModel);
        doReturn(true).when(dao).isExist(id);
        when(dao.isTagBoundToGiftCertificate(id, certificateId)).thenReturn(false);
        doNothing().when(dao).boundTagToGiftCertificate(id, certificateId);
        service.updateNewGiftCertificateTags(certificateId, tagList);
        verify(dao, times(tagList.size())).isExist(id);
        verify(dao, times(tagList.size())).isTagBoundToGiftCertificate(id, certificateId);
        verify(dao, times(tagList.size())).boundTagToGiftCertificate(id, certificateId);
    }

    @Test
    void updateNewGiftCertificateTagsShouldNotBoundTagsIfAlreadyBound() {
        List<TagClientModel> tagList = new ArrayList<>();
        tagList.add(tagClientModel);
        tagList.add(tagClientModel);
        doReturn(true).when(dao).isExist(id);
        when(dao.isTagBoundToGiftCertificate(id, certificateId)).thenReturn(true);
        service.updateNewGiftCertificateTags(certificateId, tagList);
        verify(dao, times(tagList.size())).isExist(id);
        verify(dao, times(tagList.size())).isTagBoundToGiftCertificate(id, certificateId);
        verify(dao, never()).boundTagToGiftCertificate(id, certificateId);
    }

    @Test
    void updateNewGiftCertificateTagsShouldThrowIllegalArgumentExceptionIfCertificateIdNull() {
        assertThrows(IllegalArgumentException.class,
                () -> service.updateNewGiftCertificateTags(null, clientModelList));
    }

    @Test
    void updateNewGiftCertificateTagsShouldThrowIllegalArgumentExceptionIfTagListNull() {
        assertThrows(IllegalArgumentException.class,
                () -> service.updateNewGiftCertificateTags(certificateId, null));
    }

    @Test
    void updateNewGiftCertificateTagsShouldThrowIllegalArgumentExceptionIfTagListContainsNull() {
        clientModelList.add(null);
        assertThrows(IllegalArgumentException.class,
                () -> service.updateNewGiftCertificateTags(certificateId, clientModelList));
    }

    @Test
    void findMostPopularTagShouldReturnNull() {
        when(dao.findMostPopularTag()).thenReturn(null);
        when(mapper.toClientModel(nullable(TagEntity.class))).thenReturn(null);
        assertNull(service.findMostPopularTag());
        verify(dao).findMostPopularTag();
    }

    @Test
    void findMostPopularTagShouldReturnResult() {
        when(dao.findMostPopularTag()).thenReturn(tagEntity);
        when(mapper.toClientModel(tagEntity)).thenReturn(tagClientModel);
        assertEquals(tagClientModel, service.findMostPopularTag());
        verify(dao).findMostPopularTag();
        verify(mapper).toClientModel(tagEntity);
    }
}
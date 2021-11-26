package com.epam.esm.service.impl;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.PageableEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.ResourceWithIdNotFoundException;
import com.epam.esm.exception.ResourceWithNameExistsException;
import com.epam.esm.exception.UnknownSortParamException;
import com.epam.esm.mapper.GiftCertificateMapperImpl;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.preparator.GiftCertificatePreparator;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {GiftCertificateDaoImpl.class, GiftCertificateMapperImpl.class, TagServiceImpl.class,
        GiftCertificatePreparator.class})
@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    @InjectMocks
    private GiftCertificateServiceImpl service;
    @MockBean
    private GiftCertificateDao dao;
    @Mock
    private Mapper<GiftCertificateEntity, GiftCertificateClientModel> mapper;
    @MockBean
    private TagService tagService;
    @Mock
    private GiftCertificatePreparator preparator;

    private long id = 1L;
    private String name = "Yellow Beach";
    private GiftCertificateClientModel clientModel;
    private List<TagClientModel> clientModelTags;
    private GiftCertificateClientModel newClientModel;
    private List<GiftCertificateClientModel> clientModelCertificates;
    private PageableClientModel<GiftCertificateClientModel> clientModelPage;

    private long orderId = 4L;
    private int pageSize = 3;
    private int pageNumber = 1;

    private GiftCertificateEntity entity;
    private List<TagEntity> entityTags;
    private PageableEntity<GiftCertificateEntity> entityPage;
    private List<GiftCertificateEntity> entityCertificates;

    @BeforeEach
    void setUpEntities() {
        List<TagEntity> entityTags = new ArrayList<>();
        TagEntity tagEntity = new TagEntity(3, "New Tag");
        entityTags.add(tagEntity);
        entity = new GiftCertificateEntity(
                1, "Yellow Beach", "Interesting and fresh", new BigDecimal("550.23"), 10,
                LocalDateTime.of(2021, 10, 29, 6, 12, 15, 156),
                LocalDateTime.of(2021, 10, 29, 6, 12, 15, 156),
                entityTags);
        entityCertificates = new ArrayList<>();
        entityCertificates.add(entity);
        entityCertificates.add(entity);
        entityCertificates.add(entity);

        entityPage = new PageableEntity<>(entityCertificates, 3, 1, 3, 1);
    }

    @BeforeEach
    void setUpClientModels() {
        clientModelTags = new ArrayList<>();
        TagClientModel tag = new TagClientModel(3L, "New Tag");
        clientModelTags.add(tag);
        clientModel = new GiftCertificateClientModel(
                id, "Yellow Beach", "Interesting and fresh", new BigDecimal("550.23"), 10,
                LocalDateTime.of(2021, 10, 29, 6, 12, 15, 156).toString(),
                LocalDateTime.of(2021, 10, 29, 6, 12, 15, 156).toString(),
                clientModelTags);

        newClientModel = new GiftCertificateClientModel(
                id, "New Name", "Interesting and fresh", new BigDecimal("550.23"), 10,
                LocalDateTime.of(2021, 10, 29, 6, 12, 15, 156).toString(),
                LocalDateTime.of(2021, 10, 29, 6, 12, 15, 156).toString(),
                clientModelTags);

        clientModelCertificates = new ArrayList<>();
        clientModelCertificates.add(clientModel);
        clientModelCertificates.add(clientModel);
        clientModelCertificates.add(clientModel);

        clientModelPage = new PageableClientModel(
                clientModelCertificates, 3, 1, 3L, 1L);
    }

    @Test
    void createShouldThrowIllegalArgumentExceptionIfNull() {
        assertThrows(IllegalArgumentException.class, () -> service.create(null));
    }

    @Test
    void createShouldThrowResourceWithNameExistsException() {
        doNothing().when(preparator).prepareForCreate(clientModel);
        when(mapper.toEntity(clientModel)).thenReturn(entity);
        when(dao.create(entity)).thenThrow(DataIntegrityViolationException.class);
        when(dao.getEntityClass()).thenReturn(GiftCertificateEntity.class);
        ResourceWithNameExistsException exception = assertThrows(ResourceWithNameExistsException.class,
                () -> service.create(clientModel));
        assertEquals(name, exception.getNameValue());
        verify(mapper).toEntity(clientModel);
        verify(dao).create(entity);
        verify(dao).getEntityClass();
    }

    @Test
    void createShouldReturnResultEmptyTagList() {
        clientModelTags.clear();
        doNothing().when(preparator).prepareForCreate(clientModel);
        when(mapper.toEntity(clientModel)).thenReturn(entity);
        when(dao.create(entity)).thenReturn(entity);
        when(dao.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toClientModel(entity)).thenReturn(clientModel);
        assertEquals(clientModel, service.create(clientModel));
        verify(preparator).prepareForCreate(clientModel);
        verify(mapper).toEntity(clientModel);
        verify(dao).findById(id);
        verify(dao).create(entity);
        verify(mapper).toClientModel(entity);
    }

    @Test
    void createShouldReturnResult() {
        doNothing().when(preparator).prepareForCreate(clientModel);
        when(mapper.toEntity(clientModel)).thenReturn(entity);
        when(dao.create(entity)).thenReturn(entity);
        when(tagService.updateNewGiftCertificateTags(id, clientModelTags)).thenReturn(clientModelTags);
        when(dao.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toClientModel(entity)).thenReturn(clientModel);
        assertEquals(clientModel, service.create(clientModel));
        verify(preparator).prepareForCreate(clientModel);
        verify(mapper).toEntity(clientModel);
        verify(dao).create(entity);
        verify(tagService).updateNewGiftCertificateTags(anyLong(), anyList());
        verify(dao).findById(id);
        verify(mapper).toClientModel(entity);
    }

    @Test
    void updateShouldThrowIllegalArgumentExceptionIfIdNull() {
        assertThrows(IllegalArgumentException.class, () -> service.update(null, clientModel));
    }

    @Test
    void updateShouldThrowIllegalArgumentExceptionIfNewModelNull() {
        assertThrows(IllegalArgumentException.class, () -> service.update(id, null));
    }

    @Test
    void updateShouReturnResultEmptyTagList() {
        clientModelTags.clear();
        when(dao.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toClientModel(entity)).thenReturn(clientModel);
        doNothing().when(preparator).prepareForMerge(clientModel, newClientModel);
        when(mapper.toEntity(clientModel)).thenReturn(entity);
        doNothing().when(dao).update(entity);
        when(dao.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toClientModel(entity)).thenReturn(clientModel);
        assertEquals(clientModel, service.update(id, newClientModel));
        verify(dao, times(2)).findById(id);
        verify(preparator).prepareForMerge(clientModel, newClientModel);
        verify(mapper).toEntity(clientModel);
        verify(dao).update(entity);
        verify(mapper, times(2)).toClientModel(entity);
    }

    @Test
    void updateShouReturnResult() {
        when(dao.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toClientModel(entity)).thenReturn(clientModel);
        doNothing().when(preparator).prepareForMerge(clientModel, newClientModel);
        when(mapper.toEntity(clientModel)).thenReturn(entity);
        doNothing().when(dao).update(entity);
        when(tagService.updateNewGiftCertificateTags(id, clientModelTags)).thenReturn(clientModelTags);
        when(dao.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toClientModel(entity)).thenReturn(clientModel);
        assertEquals(clientModel, service.update(id, newClientModel));
        verify(dao, times(2)).findById(id);
        verify(preparator).prepareForMerge(clientModel, newClientModel);
        verify(mapper).toEntity(clientModel);
        verify(dao).update(entity);
        verify(mapper, times(2)).toClientModel(entity);
    }

    @Test
    void updateShouldThrowResourceWithIdNotFoundException() {
        when(dao.findById(id)).thenReturn(Optional.empty());
        when(dao.getEntityClass()).thenReturn(GiftCertificateEntity.class);
        ResourceWithIdNotFoundException exception = assertThrows(ResourceWithIdNotFoundException.class,
                () -> service.update(id, newClientModel));
        assertEquals(id, exception.getResourceId());
        verify(dao).findById(id);
        verify(dao).getEntityClass();
    }

    @Test
    void updatePriceShouldThrowIllegalArgumentExceptionIfIdNull() {
        assertThrows(IllegalArgumentException.class, () -> service.updatePrice(null, new BigDecimal("567.39")));
    }

    @Test
    void updatePriceShouldThrowIllegalArgumentExceptionIfPriceNull() {
        assertThrows(IllegalArgumentException.class, () -> service.updatePrice(id, null));
    }

    @Test
    void updatePriceShouldThrowResourceWithIdNotFound() {
        when(dao.isExist(id)).thenReturn(false);
        ResourceWithIdNotFoundException exception =
                assertThrows(ResourceWithIdNotFoundException.class,
                        () -> service.updatePrice(id, new BigDecimal("45.65")));
        assertEquals(id, exception.getResourceId());
        verify(dao).isExist(id);
    }

    @Test
    void updatePriceShouldReturnResult() {
        BigDecimal price = new BigDecimal("333.33");
        when(dao.isExist(id)).thenReturn(true);
        doNothing().when(dao).updatePrice(id, price);
        when(dao.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toClientModel(entity)).thenReturn(clientModel);
        assertEquals(clientModel, service.updatePrice(id, price));
        verify(dao).isExist(id);
        verify(dao).updatePrice(id, price);
        verify(dao).findById(id);
        verify(mapper).toClientModel(entity);
    }

    @Test
    void updateNewOrderCertificatesShouldThrowIllegalArgumentExceptionIfOrderIdNull() {
        assertThrows(IllegalArgumentException.class,
                () -> service.updateNewOrderCertificates(null, clientModelCertificates));
    }

    @Test
    void updateNewOrderCertificatesShouldThrowIllegalArgumentExceptionIfCertificatesNull() {
        assertThrows(IllegalArgumentException.class,
                () -> service.updateNewOrderCertificates(orderId, null));
    }

    @Test
    void updateNewOrderCertificatesShouldThrowIllegalArgumentExceptionIfCertificatesContainNull() {
        clientModelCertificates.add(null);
        assertThrows(IllegalArgumentException.class,
                () -> service.updateNewOrderCertificates(orderId, clientModelCertificates));
    }

    @Test
    void updateNewOrderCertificatesShouldReturnResult() {
        when(dao.isExist(id)).thenReturn(true);
        doNothing().when(dao).boundCertificateToOrder(id, orderId);
        service.updateNewOrderCertificates(orderId, clientModelCertificates);
        verify(dao, times(clientModelCertificates.size())).isExist(id);
        verify(dao, times(clientModelCertificates.size())).boundCertificateToOrder(id, orderId);
    }

    @Test
    void updateNewOrderCertificatesShouldThrowResourceWithIdNotFoundException() {
        when(dao.isExist(id)).thenReturn(false);
        ResourceWithIdNotFoundException exception = assertThrows(ResourceWithIdNotFoundException.class,
                () -> service.updateNewOrderCertificates(orderId, clientModelCertificates));
        assertEquals(id, exception.getResourceId());
    }

    @Test
    void searchShouldThrowIllegalArgumentExceptionIfPageSizeNull() {
        assertThrows(IllegalArgumentException.class,
                () -> service.search(null, null, null, null,
                        null, null, 1));
    }

    @Test
    void searchShouldThrowIllegalArgumentExceptionIfPageNumberNull() {
        assertThrows(IllegalArgumentException.class,
                () -> service.search(null, null, null, null,
                        null, 2, null));
    }

    @Test
    void searchShouldThrowUnknownSortParamExceptionIfUnknownSortDirection() {
        String unknownSortDirectionParam = "UnknownSortDirection";
        when(dao.search(null, null, null, null, unknownSortDirectionParam, pageSize, pageNumber))
                .thenThrow(EnumConstantNotPresentException.class);
        assertThrows(UnknownSortParamException.class,
                () -> service.search(null, null, null, null,
                        unknownSortDirectionParam, pageSize, pageNumber));
    }

    @Test
    void searchShouldThrowUnknownSortParamExceptionIfUnknownSortField() {
        String unknownSortField = "UnknownSortField";
        when(dao.search(null, null, null, unknownSortField, null, pageSize, pageNumber))
                .thenThrow(EnumConstantNotPresentException.class);
        assertThrows(UnknownSortParamException.class,
                () -> service.search(null, null, null, unknownSortField,
                        null, pageSize, pageNumber));
    }

    @Test
    void searchShouldReturnResult() {
        when(dao.search(null, null, null, null, null, pageSize, pageNumber))
                .thenReturn(entityPage);
        when(mapper.toClientModel(entityPage)).thenReturn(clientModelPage);
        assertEquals(clientModelPage, service.search(
                null, null, null, null, null, pageSize, pageNumber));
        verify(dao).search(null, null, null, null, null, pageSize, pageNumber);
        verify(mapper).toClientModel(entityPage);
    }

    @Test
    void searchByTagsShouldThrowIllegalArgumentExceptionIfPageSizeNull() {
        assertThrows(IllegalArgumentException.class,
                () -> service.search(null, 1, null));
    }

    @Test
    void searchByTagsShouldThrowIllegalArgumentExceptionIfPageNumberNull() {
        assertThrows(IllegalArgumentException.class,
                () -> service.search(null, null, 1));
    }

    @Test
    void searchByTagsShouldReturnResult() {
        List<String> tagNames = new ArrayList<>();
        tagNames.add("Tag 1");
        tagNames.add("Tag 2");
        tagNames.add("Tag 3");
        when(dao.search(tagNames, pageSize, pageNumber)).thenReturn(entityPage);
        when(mapper.toClientModel(entityPage)).thenReturn(clientModelPage);
        assertEquals(clientModelPage, service.search(tagNames, pageSize, pageNumber));
        verify(dao).search(tagNames, pageSize, pageNumber);
        verify(mapper).toClientModel(entityPage);
    }
}
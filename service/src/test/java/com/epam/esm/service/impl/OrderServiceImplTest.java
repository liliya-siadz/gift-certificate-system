package com.epam.esm.service.impl;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.clientmodel.OrderClientModel;
import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.clientmodel.UserClientModel;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.impl.OrderDaoImpl;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.entity.PageableEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.exception.ResourceWithIdNotFoundException;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.OrderMapperImpl;
import com.epam.esm.preparator.OrderPreparator;
import com.epam.esm.preparator.Preparator;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {OrderDaoImpl.class, OrderMapperImpl.class, OrderPreparator.class,
        GiftCertificateServiceImpl.class, UserServiceImpl.class})
@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl service;
    @MockBean
    private OrderDao dao;
    @Mock
    private Mapper<OrderEntity, OrderClientModel> mapper;
    @Mock
    private Preparator<OrderClientModel> preparator;
    @MockBean
    private GiftCertificateService certificateService;
    @MockBean
    private UserService userService;

    private long id = 1;
    private long userId = 3;
    private long certificateId = 4;

    private int pageSize = 3;
    private int pageNumber = 1;

    private GiftCertificateClientModel certificateClientModel;
    private GiftCertificateEntity certificateEntity;

    private OrderEntity entity;
    private OrderClientModel clientModel;
    private UserEntity entityUser;
    private List<GiftCertificateEntity> entityCertificates;

    private PageableEntity<OrderEntity> entityPage;
    private PageableClientModel<OrderClientModel> clientModelPage;
    private UserClientModel clientModelUser;
    private List<GiftCertificateClientModel> clientCertificates;

    @BeforeEach
    void setUpEntities() {
        entityUser = new UserEntity(userId, "Peter Johnson");
        List<TagEntity> tags = new ArrayList<>();
        TagEntity tagEntity = new TagEntity(3, "New Tag");
        tags.add(tagEntity);
        List<GiftCertificateEntity> entityCertificates = new ArrayList<>();
        certificateEntity = new GiftCertificateEntity(
                certificateId, "Yellow Beach", "Interesting and fresh", new BigDecimal("550.23"), 10,
                LocalDateTime.of(2021, 10, 29, 6, 12, 15, 156),
                LocalDateTime.of(2021, 10, 29, 6, 12, 15, 156),
                tags);
        entityCertificates.add(certificateEntity);
        entity = new OrderEntity(id, new BigDecimal("550.23"),
                LocalDateTime.of(2020, 8, 29, 6, 12, 15, 156),
                entityUser, entityCertificates);

        List<OrderEntity> entityList = new ArrayList<>();
        entityList.add(entity);
        entityList.add(entity);
        entityList.add(entity);
        entityPage = new PageableEntity<>(entityList, pageSize, pageNumber, 3, 1);
    }

    @BeforeEach
    void setUpClientModels() {
        clientModelUser = new UserClientModel(userId, "Peter Johnson");
        List<TagClientModel> tags = new ArrayList<>();
        TagClientModel tag = new TagClientModel(3L, "New Tag");
        tags.add(tag);
        clientCertificates = new ArrayList<>();
        certificateClientModel = new GiftCertificateClientModel(
                certificateId, "Yellow Beach", "Interesting and fresh", new BigDecimal("550.23"), 10,
                LocalDateTime.of(2021, 10, 29, 6, 12, 15, 156).toString(),
                LocalDateTime.of(2021, 10, 29, 6, 12, 15, 156).toString(),
                tags);
        clientCertificates.add(certificateClientModel);
        clientModel = new OrderClientModel(id, clientModelUser, new BigDecimal("550.23"),
                LocalDateTime.of(2020, 8, 29, 6, 12, 15, 156).toString(),
                clientCertificates);

        List<OrderClientModel> clientModelList = new ArrayList<>();
        clientModelList.add(clientModel);
        clientModelList.add(clientModel);
        clientModelList.add(clientModel);
        clientModelPage = new PageableClientModel<>(clientModelList, pageSize, pageNumber, 3L, 1L);
    }

    @Test
    void createShouldReturnResult() {
        doNothing().when(preparator).prepareForCreate(clientModel);
        when(certificateService.findById(certificateId)).thenReturn(certificateClientModel);
        when(userService.findById(userId)).thenReturn(clientModelUser);
        when(mapper.toEntity(clientModel)).thenReturn(entity);
        when(dao.create(entity)).thenReturn(entity);
        doNothing().when(certificateService).updateNewOrderCertificates(id, clientCertificates);
        when(dao.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toClientModel(entity)).thenReturn(clientModel);
        assertEquals(clientModel, service.create(clientModel));
        verify(preparator).prepareForCreate(clientModel);
        verify(certificateService).findById(certificateId);
        verify(userService).findById(userId);
        verify(mapper).toEntity(clientModel);
        verify(dao).create(entity);
        verify(dao).findById(id);
        verify(mapper).toClientModel(entity);
    }

    @Test
    void createShouldThrowIllegalArgumentExceptionIfNull() {
        assertThrows(IllegalArgumentException.class, () -> service.create(null));
    }

    @Test
    void createShouldThrowResourceWithIdNotFoundIfUserNotFound() {
        doNothing().when(preparator).prepareForCreate(clientModel);
        when(certificateService.findById(certificateId)).thenReturn(certificateClientModel);
        when(userService.findById(userId)).thenThrow(ResourceWithIdNotFoundException.class);
        assertThrows(ResourceWithIdNotFoundException.class, () -> service.create(clientModel));
        verify(preparator).prepareForCreate(clientModel);
        verify(certificateService).findById(certificateId);
        verify(userService).findById(userId);
    }

    @Test
    void createShouldThrowResourceWithIdNotFoundIfCertificateNotFound() {
        doNothing().when(preparator).prepareForCreate(clientModel);
        when(certificateService.findById(certificateId)).thenThrow(ResourceWithIdNotFoundException.class);
        assertThrows(ResourceWithIdNotFoundException.class, () -> service.create(clientModel));
        verify(preparator).prepareForCreate(clientModel);
        verify(certificateService).findById(certificateId);
    }

    @Test
    void findUserOrdersShouldThrowIllegalArgumentExceptionIfUserIdNull() {
        assertThrows(IllegalArgumentException.class, () -> service.findUserOrders(null, pageSize, pageNumber));
    }

    @Test
    void findUserOrdersShouldThrowIllegalArgumentExceptionIfPageSizeNull() {
        assertThrows(IllegalArgumentException.class, () -> service.findUserOrders(userId, null, pageNumber));
    }

    @Test
    void findUserOrdersShouldThrowIllegalArgumentExceptionIfPageNumberNull() {
        assertThrows(IllegalArgumentException.class, () -> service.findUserOrders(userId, pageSize, null));
    }

    @Test
    void findUserOrdersShouldThrowResourceWithIdNotFoundException() {
        when(userService.isExist(userId)).thenReturn(false);
        assertThrows(ResourceWithIdNotFoundException.class, () -> service.findUserOrders(userId, pageSize, pageNumber));
        verify(userService).isExist(userId);
    }

    @Test
    void findUserOrdersShouldReturnResult() {
        when(userService.isExist(userId)).thenReturn(true);
        when(dao.findUserOrders(userId, pageSize, pageNumber)).thenReturn(entityPage);
        when(mapper.toClientModel(entityPage)).thenReturn(clientModelPage);
        assertEquals(clientModelPage, service.findUserOrders(userId, pageSize, pageNumber));
        verify(userService).isExist(userId);
        verify(dao).findUserOrders(userId, pageSize, pageNumber);
        verify(mapper).toClientModel(entityPage);
    }

    @Test
    void findUserOrderShouldThrowIllegalArgumentExceptionIfUserIdNull() {
        assertThrows(IllegalArgumentException.class, () -> service.findUserOrder(null, id));
    }

    @Test
    void findUserOrderShouldThrowIllegalArgumentExceptionIfOrderIdNull() {
        assertThrows(IllegalArgumentException.class, () -> service.findUserOrder(userId, null));
    }

    @Test
    void findUserShouldThrowResourceWithIdNotFoundExceptionIfUserNotFound() {
        when(userService.isExist(userId)).thenReturn(false);
        assertThrows(ResourceWithIdNotFoundException.class, () -> service.findUserOrder(userId, id));
        verify(userService).isExist(userId);
    }

    @Test
    void findUserShouldThrowResourceWithIdNotFoundExceptionIfOrderNotFound() {
        when(userService.isExist(userId)).thenReturn(true);
        when(dao.isExist(id)).thenReturn(false);
        assertThrows(ResourceWithIdNotFoundException.class, () -> service.findUserOrder(userId, id));
        verify(userService).isExist(userId);
        verify(dao).isExist(id);
    }

    @Test
    void findUserShouldReturnResult() {
        when(userService.isExist(userId)).thenReturn(true);
        when(dao.isExist(id)).thenReturn(true);
        when(dao.findUserOrder(userId, id)).thenReturn(entity);
        when(mapper.toClientModel(entity)).thenReturn(clientModel);
        assertEquals(clientModel, service.findUserOrder(userId, id));
        verify(userService).isExist(userId);
        verify(dao).isExist(id);
        verify(dao).findUserOrder(userId, id);
        verify(mapper).toClientModel(entity);
    }
}
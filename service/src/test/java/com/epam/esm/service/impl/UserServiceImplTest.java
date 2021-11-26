package com.epam.esm.service.impl;

import com.epam.esm.clientmodel.UserClientModel;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.exception.ResourceWithNameExistsException;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.UserMapperImpl;
import com.epam.esm.preparator.Preparator;
import com.epam.esm.preparator.UserPreparator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserDaoImpl.class, UserMapperImpl.class, UserPreparator.class})
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl service;
    @MockBean
    private UserDao dao;
    @Mock
    private Mapper<UserEntity, UserClientModel> mapper;
    @Mock
    private Preparator<UserClientModel> preparator;

    private long id = 2;
    private String name = "COOL GUY";
    private UserClientModel clientModel = new UserClientModel(id, name);
    private UserEntity entity = new UserEntity(id, name);

    @Test
    void createShouldThrowIllegalArgumentExceptionIfNull() {
        assertThrows(IllegalArgumentException.class, () -> service.create(null));
    }

    @Test
    void createShouldThrowResourceWithNameExistsException() {
        doNothing().when(preparator).prepareForCreate(clientModel);
        when(mapper.toEntity(clientModel)).thenReturn(entity);
        when(dao.create(entity)).thenThrow(DataIntegrityViolationException.class);
        when(dao.getEntityClass()).thenReturn(UserEntity.class);
        ResourceWithNameExistsException exception =
                assertThrows(ResourceWithNameExistsException.class, () -> service.create(clientModel));
        assertEquals(name, exception.getNameValue());
        verify(preparator).prepareForCreate(clientModel);
        verify(mapper).toEntity(clientModel);
        verify(dao).create(entity);
        verify(dao).getEntityClass();
    }

    @Test
    void createShouldReturnResult() {
        doNothing().when(preparator).prepareForCreate(clientModel);
        when(mapper.toEntity(clientModel)).thenReturn(entity);
        when(dao.create(entity)).thenReturn(entity);
        when(mapper.toClientModel(entity)).thenReturn(clientModel);
        assertEquals(clientModel, service.create(clientModel));
        verify(preparator).prepareForCreate(clientModel);
        verify(mapper).toEntity(clientModel);
        verify(dao).create(entity);
        verify(mapper).toClientModel(entity);
    }
}
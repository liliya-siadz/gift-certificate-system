package com.epam.esm.mapper;

import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.clientmodel.UserClientModel;
import com.epam.esm.entity.PageableEntity;
import com.epam.esm.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = UserMapperImpl.class)
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    private UserEntity entity;
    private PageableEntity<UserEntity> entityPage;
    private UserClientModel clientModel;
    private PageableClientModel<UserClientModel> clientModelPage;

    @BeforeEach
    private void setUp() {
        entity = new UserEntity(3L, "name");
        List<UserEntity> entityList = new ArrayList<>();
        entityList.add(entity);
        entityList.add(entity);
        entityList.add(entity);
        entityPage = new PageableEntity<UserEntity>(entityList, 3, 1,3, 1);

        clientModel = new UserClientModel(3L, "name");
        List<UserClientModel> clientModelList = new ArrayList<>();
        clientModelList.add(clientModel);
        clientModelList.add(clientModel);
        clientModelList.add(clientModel);
        clientModelPage = new PageableClientModel<UserClientModel>(
                clientModelList, 3, 1,3L, 1L);
    }

    @Test
    void toEntityShouldBeEqualToClientModel() {
        UserEntity actual = userMapper.toEntity(clientModel);
        assertEquals(entity, actual);
    }

    @Test
    void toClientModelShouldBeEqualToEntity() {
        UserClientModel actual = userMapper.toClientModel(entity);
        assertEquals(clientModel, actual);
    }

    @Test
    void toEntityShouldBeEqualToEntityPage() {
        PageableEntity<UserEntity> actual = userMapper.toEntity(clientModelPage);
        assertEquals(entityPage, actual);
    }

    @Test
    void toClientModelShouldBeEqualToClientModelPage() {
        PageableClientModel<UserClientModel> actual = userMapper.toClientModel(entityPage);
        assertEquals(clientModelPage, actual);
    }
}
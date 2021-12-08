package com.epam.esm.mapper;

import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.entity.PageableEntity;
import com.epam.esm.entity.TagEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TagMapperImpl.class)
class TagMapperTest {
    @Autowired
    private TagMapper tagMapper;

    private TagEntity entity;
    private PageableEntity<TagEntity> entityPage;
    private TagClientModel clientModel;
    private PageableClientModel<TagClientModel> clientModelPage;

    @BeforeEach
    private void setUp() {
        entity = new TagEntity(1, "New Tag");
        List<TagEntity> entityList = new ArrayList<>();
        entityList.add(entity);
        entityList.add(entity);
        entityList.add(entity);
        entityPage = new PageableEntity<>(entityList, 3, 1, 3, 1);

        clientModel = new TagClientModel(1L, "New Tag");
        List<TagClientModel> clientModelList = new ArrayList<>();
        clientModelList.add(clientModel);
        clientModelList.add(clientModel);
        clientModelList.add(clientModel);
        clientModelPage = new PageableClientModel<>(
                clientModelList, 3, 1, 3L, 1L);
    }

    @Test
    void toEntityShouldBeEqualToClientModel() {
        TagEntity actual = tagMapper.toEntity(clientModel);
        assertEquals(entity, actual);
    }

    @Test
    void toClientModelShouldBeEqualToEntity() {
        TagClientModel actual = tagMapper.toClientModel(entity);
        assertEquals(clientModel, actual);
    }

    @Test
    void toEntityShouldBeEqualToEntityPage() {
        PageableEntity<TagEntity> actual = tagMapper.toEntity(clientModelPage);
        assertEquals(entityPage, actual);
    }

    @Test
    void toClientModelShouldBeEqualToClientModelPage() {
        PageableClientModel<TagClientModel> actual = tagMapper.toClientModel(entityPage);
        assertEquals(clientModelPage, actual);
    }
}
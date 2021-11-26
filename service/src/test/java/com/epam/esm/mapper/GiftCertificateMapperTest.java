package com.epam.esm.mapper;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.PageableEntity;
import com.epam.esm.entity.TagEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = GiftCertificateMapperImpl.class)
public class GiftCertificateMapperTest {
    @Autowired
    private GiftCertificateMapper certificateMapper;

    private GiftCertificateEntity entity;
    private PageableEntity<GiftCertificateEntity> entityPage;
    private GiftCertificateClientModel clientModel;
    private PageableClientModel<GiftCertificateClientModel> clientModelPage;

    @BeforeEach
    private void setUpEntities() {
        List<TagEntity> tags = new ArrayList<>();
        TagEntity tagEntity = new TagEntity(3, "New Tag");
        tags.add(tagEntity);
        entity = new GiftCertificateEntity(
                1, "Yellow Beach", "Interesting and fresh", new BigDecimal("550.23"), 10,
                LocalDateTime.of(2021, 10, 29, 6, 12, 15, 156),
                LocalDateTime.of(2021, 10, 29, 6, 12, 15, 156),
                tags);

        List<GiftCertificateEntity> entityList = new ArrayList<>();
        entityList.add(entity);
        entityList.add(entity);
        entityList.add(entity);
        entityPage = new PageableEntity<>(entityList, 3, 1, 3, 1);
    }

    @BeforeEach
    public void setUpClientModels() {
        List<TagClientModel> tags = new ArrayList<>();
        TagClientModel tag = new TagClientModel(3L, "New Tag");
        tags.add(tag);
        clientModel = new GiftCertificateClientModel(
                1L, "Yellow Beach", "Interesting and fresh", new BigDecimal("550.23"), 10,
                LocalDateTime.of(2021, 10, 29, 6, 12, 15, 156).toString(),
                LocalDateTime.of(2021, 10, 29, 6, 12, 15, 156).toString(),
                tags);

        List<GiftCertificateClientModel> clientModelList = new ArrayList<>();
        clientModelList.add(clientModel);
        clientModelList.add(clientModel);
        clientModelList.add(clientModel);
        clientModelPage = new PageableClientModel(clientModelList, 3, 1, 3L, 1L);
    }

    @Test
    void toEntityShouldBeEqualToClientModel() {
        GiftCertificateEntity actual = certificateMapper.toEntity(clientModel);
        assertEquals(entity, actual);
    }

    @Test
    void toClientModelShouldBeEqualToEntity() {
        GiftCertificateClientModel actual = certificateMapper.toClientModel(entity);
        assertEquals(clientModel, actual);
    }

    @Test
    void toEntityShouldBeEqualToEntityPage() {
        PageableEntity<GiftCertificateEntity> actual = certificateMapper.toEntity(clientModelPage);
        assertEquals(entityPage, actual);
    }

    @Test
    void toClientModelShouldBeEqualToClientModelPage() {
        PageableClientModel<GiftCertificateClientModel> actual = certificateMapper.toClientModel(entityPage);
        assertEquals(clientModelPage, actual);
    }
}

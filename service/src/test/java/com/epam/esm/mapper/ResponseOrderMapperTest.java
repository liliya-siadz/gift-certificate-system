package com.epam.esm.mapper;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.clientmodel.ResponseOrderClientModel;
import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.OrderEntity;
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

@SpringBootTest(classes = ResponseOrderMapperImpl.class)
public class ResponseOrderMapperTest {
    @Autowired
    private ResponseOrderMapper responseOrderMapper;

    private OrderEntity entity;
    private PageableEntity<OrderEntity> entityPage;
    private ResponseOrderClientModel clientModel;
    private PageableClientModel<ResponseOrderClientModel> clientModelPage;

    @BeforeEach
    private void setUpEntities() {
        List<TagEntity> tags = new ArrayList<>();
        TagEntity tagEntity = new TagEntity(3, "New Tag");
        tags.add(tagEntity);
        List<GiftCertificateEntity> certificates = new ArrayList<>();
        GiftCertificateEntity certificate = new GiftCertificateEntity(
                1, "Yellow Beach", "Interesting and fresh", new BigDecimal("550.23"), 10,
                LocalDateTime.of(2021, 10, 29, 6, 12, 15, 156),
                LocalDateTime.of(2021, 10, 29, 6, 12, 15, 156),
                tags);
        certificates.add(certificate);
        entity = OrderEntity.builder().id(1).cost(new BigDecimal("100.01"))
                .purchaseDate(LocalDateTime.of(2020, 8, 29, 6, 12, 15, 156))
                .certificates(certificates).build();

        List<OrderEntity> entityList = new ArrayList<>();
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
        List<GiftCertificateClientModel> certificates = new ArrayList<>();
        GiftCertificateClientModel certificate = new GiftCertificateClientModel(
                1L, "Yellow Beach", "Interesting and fresh", new BigDecimal("550.23"), 10,
                LocalDateTime.of(2021, 10, 29, 6, 12, 15, 156).toString(),
                LocalDateTime.of(2021, 10, 29, 6, 12, 15, 156).toString(),
                tags);
        certificates.add(certificate);
        clientModel = ResponseOrderClientModel.builder().id(1L).cost(new BigDecimal("100.01"))
                .purchaseDate(LocalDateTime.of(2020, 8, 29, 6, 12, 15, 156).toString())
                .certificates(certificates).build();

        List<ResponseOrderClientModel> clientModelList = new ArrayList<>();
        clientModelList.add(clientModel);
        clientModelList.add(clientModel);
        clientModelList.add(clientModel);
        clientModelPage = new PageableClientModel<>(clientModelList, 3, 1, 3L, 1L);
    }

    @Test
    void toEntityShouldBeEqualToClientModel() {
        OrderEntity actual = responseOrderMapper.toEntity(clientModel);
        assertEquals(entity, actual);
    }

    @Test
    void toClientModelShouldBeEqualToEntity() {
        ResponseOrderClientModel actual = responseOrderMapper.toClientModel(entity);
        assertEquals(clientModel, actual);
    }

    @Test
    void toEntityShouldBeEqualToEntityPage() {
        PageableEntity<OrderEntity> actual = responseOrderMapper.toEntity(clientModelPage);
        assertEquals(entityPage, actual);
    }

    @Test
    void toClientModelShouldBeEqualToClientModelPage() {
        PageableClientModel<ResponseOrderClientModel> actual = responseOrderMapper.toClientModel(entityPage);
        assertEquals(clientModelPage, actual);
    }
}

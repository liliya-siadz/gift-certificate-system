package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.model.GiftCertificateModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {TestServiceConfiguration.class})
@ActiveProfiles("prod")
class GiftCertificateModelMapperTest {
    @Autowired
    private GiftCertificateMapper mapper;

    private GiftCertificateModel clientModel;
    private GiftCertificate entityModel;

    @BeforeEach
    void setUp() {
        long id = 10L;
        int duration = 209;
        String name = "Tag name";
        BigDecimal price = new BigDecimal("345.34");
        String description = "Lorem ipsum gift certificate";
        LocalDateTime createDate = LocalDateTime.of(2020, 8, 29, 6, 12, 15, 156);
        String createDateString = createDate.toString();

        clientModel = GiftCertificateModel.builder()
                .id(id).duration(duration).name(name).description(description).price(price)
                .createDate(createDateString)
                .lastUpdateDate(createDateString).build();

        entityModel = GiftCertificate.builder()
                .id(id).duration(duration).name(name).description(description).price(price)
                .createDate(createDate)
                .lastUpdateDate(createDate).build();
    }

    @Test
    void toClientModelShouldReturnEqualClientModel() {
        GiftCertificateModel actual = mapper.toModel(entityModel);
        assertEquals(clientModel, actual);
    }

    @Test
    void toEntityShouldReturnEqualEntity() {
        GiftCertificate actual = mapper.toEntity(clientModel);
        assertEquals(entityModel, actual);
    }
}
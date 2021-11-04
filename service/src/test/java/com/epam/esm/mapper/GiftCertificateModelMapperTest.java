package com.epam.esm.mapper;

import com.epam.esm.configuration.TestServiceConfiguration;
import com.epam.esm.model.GiftCertificateClientModel;
import com.epam.esm.model.GiftCertificateEntityModel;
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
    private GiftCertificateModelMapper mapper;

    private GiftCertificateClientModel clientModel;
    private GiftCertificateEntityModel entityModel;

    @BeforeEach
    void setUp() {
        long id = 10L;
        int duration = 209;
        String name = "Tag name";
        BigDecimal price = new BigDecimal("345.34");
        String description = "Lorem ipsum gift certificate";
        LocalDateTime createDate = LocalDateTime.of(2020, 8, 29, 6, 12, 15, 156);
        String createDateString = createDate.toString();

        clientModel = GiftCertificateClientModel.builder()
                .id(id).duration(duration).name(name).description(description).price(price)
                .createDate(createDateString)
                .lastUpdateDate(createDateString).build();

        entityModel = GiftCertificateEntityModel.builder()
                .id(id).duration(duration).name(name).description(description).price(price)
                .createDate(createDate)
                .lastUpdateDate(createDate).build();
    }

    @Test
    void toClientModelShouldReturnEqualClientModel() {
        GiftCertificateClientModel actual = mapper.toClientModel(entityModel);
        assertEquals(clientModel, actual);
    }

    @Test
    void toEntityShouldReturnEqualEntity() {
        GiftCertificateEntityModel actual = mapper.toEntity(clientModel);
        assertEquals(entityModel, actual);
    }
}
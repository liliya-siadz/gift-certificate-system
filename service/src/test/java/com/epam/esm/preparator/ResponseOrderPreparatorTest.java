package com.epam.esm.preparator;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.clientmodel.ResponseOrderClientModel;
import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.clientmodel.UserClientModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = ResponseOrderPreparator.class)
class ResponseOrderPreparatorTest {
    @Autowired
    private Preparator<ResponseOrderClientModel> preparator;

    private ResponseOrderClientModel order;

    @BeforeEach
    private void setUp() {
        UserClientModel user = UserClientModel.builder().id(2L).name("Peter Johnson").build();
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
        order = ResponseOrderClientModel.builder().id(1L).cost(new BigDecimal("100.01"))
                .purchaseDate(LocalDateTime.of(2020, 8, 29, 6, 12, 15, 156).toString())
                .certificates(certificates).build();
    }

    @Test
    void prepareForCreateShouldSetIdToNull() {
        preparator.prepareForCreate(order);
        assertNull(order.getId());
    }

    @Test
    void prepareForCreateShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> preparator.prepareForCreate(null));
    }
}
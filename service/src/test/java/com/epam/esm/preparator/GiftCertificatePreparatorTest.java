package com.epam.esm.preparator;

import com.epam.esm.clientmodel.GiftCertificateClientModel;
import com.epam.esm.clientmodel.TagClientModel;
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

@SpringBootTest(classes = GiftCertificatePreparator.class)
class GiftCertificatePreparatorTest {
    @Autowired
    private Preparator<GiftCertificateClientModel> preparator;

    private GiftCertificateClientModel certificate;

    @BeforeEach
    public void setUp() {
        List<TagClientModel> tags = new ArrayList<>();
        TagClientModel tag = new TagClientModel(3L, "New Tag");
        tags.add(tag);
        certificate = new GiftCertificateClientModel(
                1L, "Yellow Beach", "Interesting and fresh", new BigDecimal("550.23"), 10,
                LocalDateTime.of(2021, 10, 29, 6, 12, 15, 156).toString(),
                LocalDateTime.of(2021, 10, 29, 6, 12, 15, 156).toString(),
                tags);
    }

    @Test
    void prepareForCreateShouldSetIdToNull() {
        preparator.prepareForCreate(certificate);
        assertNull(certificate.getId());
    }

    @Test
    void prepareForCreateShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> preparator.prepareForCreate(null));
    }
}
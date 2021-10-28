package com.epam.esm.dao.impl;

import com.epam.esm.configuration.TestDaoConfiguration;
import com.epam.esm.model.GiftCertificateEntityModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {TestDaoConfiguration.class})
@ActiveProfiles("test")
class GiftCertificateDaoImplTest {

    @Autowired
    private GiftCertificateDaoImpl dao;

    private List<GiftCertificateEntityModel> allCertificates;
    private GiftCertificateEntityModel certificate1;
    private GiftCertificateEntityModel certificate2;
    private GiftCertificateEntityModel certificate3;
    private GiftCertificateEntityModel certificate4;
    private GiftCertificateEntityModel certificate5;
    private GiftCertificateEntityModel unknownCertificate;

    @BeforeEach
    void setUpCommons() {
        LocalDateTime lastUpdate = LocalDateTime.of(2021, 8, 29, 6, 12, 15, 156000000);

        certificate1 = GiftCertificateEntityModel.builder()
                .id(1).name("Paul Sander").description("Star wars inspiration art therapy").duration(15)
                .price(new BigDecimal("345.78")).createDate(LocalDateTime.of(1999, 8, 29, 6, 12, 15, 156000000))
                .lastUpdateDate(lastUpdate).build();

        certificate2 = GiftCertificateEntityModel.builder()
                .id(2).name("Sandy Clare").description("10 lessons of crossfit").duration(60)
                .price(new BigDecimal("499.99")).createDate(LocalDateTime.of(2020, 8, 29, 22, 12, 15, 156000000))
                .lastUpdateDate(lastUpdate).build();

        certificate3 = GiftCertificateEntityModel.builder()
                .id(3).name("Eric Z").description("Crazy cooking with japan chef").duration(100)
                .price(new BigDecimal("800.78")).createDate(LocalDateTime.of(2021, 7, 1, 5, 12, 15, 576000000))
                .lastUpdateDate(lastUpdate).build();

        certificate4 = GiftCertificateEntityModel.builder()
                .id(4).name("Lola Richardson").description("Swimming with dolphins").duration(40)
                .price(new BigDecimal("1700")).createDate(LocalDateTime.of(2021, 3, 5, 13, 45, 7, 200000000))
                .lastUpdateDate(lastUpdate).build();

        certificate5 = GiftCertificateEntityModel.builder()
                .id(5).name("Bob Di La Ve").description("Free E-books at Chare De Kav").duration(250)
                .price(new BigDecimal("300.30")).createDate(LocalDateTime.of(2021, 8, 29, 6, 12, 15, 156000000))
                .lastUpdateDate(lastUpdate).build();

        allCertificates = new ArrayList<>();
        allCertificates.add(certificate1);
        allCertificates.add(certificate2);
        allCertificates.add(certificate3);
        allCertificates.add(certificate4);
        allCertificates.add(certificate5);

        unknownCertificate = GiftCertificateEntityModel.builder()
                .id(10).name("Katy K").description("Audience with Madonna signer")
                .price(new BigDecimal(100000)).duration(5).createDate(lastUpdate)
                .lastUpdateDate(lastUpdate).build();
    }

    @Test
    void createShouldThrowIllegalArgumentExceptionIfNull() {
        assertThrows(IllegalArgumentException.class, () -> dao.create(null));
    }

    @Test
    void createShouldTReturnResultBiggerThanZero() {
        long actual = dao.create(unknownCertificate);
        assertTrue(actual > 0);
    }

    @Test
    void findAllShouldReturnResult() {
        List<GiftCertificateEntityModel> actual = dao.findAll();
        assertEquals(allCertificates, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L})
    void findByIdResultShouldBePresent(long id) {
        Optional<GiftCertificateEntityModel> actual = dao.findById(id);
        assertTrue(actual.isPresent());
    }

    @ParameterizedTest
    @ValueSource(longs = {12L, 20L, 32L, 178L})
    void findByIdShouldResultShouldNotBePresent(long id) {
        Optional<GiftCertificateEntityModel> actual = dao.findById(id);
        assertFalse(actual.isPresent());
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L})
    void deleteShouldReturnTrue(long id) {
        boolean actual = dao.delete(id);
        assertTrue(actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {12L, 20L, 32L, 178L})
    void deleteShouldReturnFalse(long id) {
        boolean actual = dao.delete(id);
        assertFalse(actual);
    }

    @Test
    void updateShouldReturnResult() {
        GiftCertificateEntityModel newData = new GiftCertificateEntityModel();
        newData.setName("Sandy Clare Senior");

        GiftCertificateEntityModel expected = GiftCertificateEntityModel.builder()
                .id(2).name("Sandy Clare Senior").description("10 lessons of crossfit").duration(60)
                .price(new BigDecimal("499.99")).createDate(LocalDateTime.of(2020, 8, 29, 22, 12, 15, 156000000)).build();
        long targetCertificateId = expected.getId();

        GiftCertificateEntityModel actual = dao.update(targetCertificateId, newData);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getCreateDate(), actual.getCreateDate());
    }

    @Test
    void updateShouldReturnOriginalEntity() {
        GiftCertificateEntityModel newData = new GiftCertificateEntityModel();
        GiftCertificateEntityModel expected = certificate2;
        long targetCertificateId = expected.getId();
        GiftCertificateEntityModel actual = dao.update(targetCertificateId, newData);
        assertEquals(expected, actual);
    }

    @Test
    void updateShouldThrowIllegalArgumentExceptionIfNull() {
        assertThrows(IllegalArgumentException.class, () -> dao.update(Mockito.anyLong(), null));
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L})
    void isExistShouldReturnTrue(long id) {
        boolean actual = dao.isExist(id);
        assertTrue(actual);
    }

    @Test
    void isExistShouldReturnFalse() {
        boolean actual = dao.isExist(unknownCertificate.getId());
        assertFalse(actual);
    }

    @Test
    void searchShouldReturnAllValuesIfAllArgsIsNull() {
        List<GiftCertificateEntityModel> actual = dao.search(null, null, null, null);
        assertTrue(actual.containsAll(allCertificates));
        assertEquals(allCertificates.size(), actual.size());
    }

    @Test
    void searchShouldReturnCertificatesWithBoundedTargetTagName() {
        List<GiftCertificateEntityModel> actual = dao.search("New year 3000", null, null, null);
        List<GiftCertificateEntityModel> expected = new ArrayList<>();
        expected.add(certificate1);
        expected.add(certificate2);
        expected.add(certificate4);
        assertTrue(actual.containsAll(expected));
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void searchShouldReturnSortedByNameCertificates() {
        List<GiftCertificateEntityModel> actual = dao.search(null, null, null, "1name");
        List<GiftCertificateEntityModel> expected = new ArrayList<>();
        expected.add(certificate5);
        expected.add(certificate3);
        expected.add(certificate4);
        expected.add(certificate1);
        expected.add(certificate2);
        assertEquals(expected, actual);
    }
}
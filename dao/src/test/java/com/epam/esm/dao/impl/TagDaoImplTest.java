package com.epam.esm.dao.impl;

import com.epam.esm.configuration.TestDaoConfiguration;
import com.epam.esm.model.TagEntityModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestDaoConfiguration.class})
@Transactional
@ActiveProfiles("test")
class TagDaoImplTest {

    @Autowired
    private TagDaoImpl dao;

    private List<TagEntityModel> tagsInDb;
    private long targetGiftCertificateIdB = 4;
    private TagEntityModel tag1 = new TagEntityModel(1, "Best friend");
    private TagEntityModel tag2 = new TagEntityModel(2, "From Colleagues");
    private TagEntityModel tag3 = new TagEntityModel(3, "New year 3000");
    private TagEntityModel tag4 = new TagEntityModel(4, "The Power of idea");

    @BeforeEach
    void setUpCommons() {
        tagsInDb = new ArrayList<>();
        tagsInDb.add(tag1);
        tagsInDb.add(tag2);
        tagsInDb.add(tag3);
        tagsInDb.add(tag4);
    }

    static Stream<Long> idTagsInDb() {
        return Stream.of(1L, 2L, 3L, 4L);
    }

    @ParameterizedTest
    @MethodSource("idTagsInDb")
    void findByIdResultShouldBePresent(long id) {
        Optional<TagEntityModel> actual = dao.findById(id);
        assertTrue(actual.isPresent());
    }

    static Stream<Long> idTagsNotInDb() {
        return Stream.of(5L, 100L, 38L, 14L);
    }

    @ParameterizedTest
    @MethodSource("idTagsNotInDb")
    void findByIdResultShouldNotBePresent(long id) {
        Optional<TagEntityModel> actual = dao.findById(id);
        assertFalse(actual.isPresent());
    }

    static Stream<TagEntityModel> tagsInDb() {
        return Stream.of(new TagEntityModel(1, "Best friend"),
                new TagEntityModel(2, "From Colleagues"),
                new TagEntityModel(3, "New year 3000"),
                new TagEntityModel(4, "The Power of idea"));
    }

    @ParameterizedTest
    @MethodSource("tagsInDb")
    void findByIdReturnedResultShouldBeEqualToParam(TagEntityModel tag) {
        Optional<TagEntityModel> actual = dao.findById(tag.getId());
        Optional<TagEntityModel> expected = Optional.of(tag);
        assertEquals(expected, actual);
    }

    @Test
    void findAllShouldReturnResult() {
        List<TagEntityModel> actual = dao.findAll();
        assertEquals(tagsInDb, actual);
    }

    @Test
    void findAllTagsBoundToGiftCertificateShouldReturnResult() {
        List<TagEntityModel> expected = Stream.of(tag1, tag3).collect(Collectors.toList());
        List<TagEntityModel> actual = dao.findAllTagsBoundToGiftCertificate(targetGiftCertificateIdB);
        assertEquals(expected, actual);
    }

    @Test
    void createShouldReturnResultBiggerThanZero() {
        long actual = dao.create(new TagEntityModel(0, "Another one tag"));
        assertTrue(actual > 0L);
    }

    @Test
    void createShouldThrowIllegalArgumentExceptionIfNull() {
        assertThrows(IllegalArgumentException.class, () -> dao.create(null));
    }

    @ParameterizedTest
    @MethodSource("idTagsInDb")
    void deleteShouldReturnTrue(long id) {
        boolean actual = dao.isExist(id);
        assertTrue(actual);
    }

    @ParameterizedTest
    @MethodSource("idTagsNotInDb")
    void deleteShouldReturnFalse(long id) {
        boolean actual = dao.isExist(id);
        assertTrue(actual);
    }

    @ParameterizedTest
    @MethodSource("idTagsInDb")
    void isExistShouldReturnTrue(long id) {
        boolean actual = dao.isExist(id);
        assertTrue(actual);
    }

    @ParameterizedTest
    @MethodSource("idTagsNotInDb")
    void isExistShouldReturnFalse(long id) {
        boolean actual = dao.isExist(id);
        assertTrue(actual);
    }

    @Test
    void boundTagToGiftCertificateShouldFail() {
        try {
            dao.boundTagToGiftCertificate(3, targetGiftCertificateIdB);
            fail("Should be failed cause action is constraint violation"
                    + " (tags are already bounded to gift certificate).");
        } catch (Throwable throwable) {
            assertTrue(true);
        }
    }

    @Test
    void boundTagToGiftCertificateShouldNotFail() {
        try {
            dao.boundTagToGiftCertificate(1, targetGiftCertificateIdB);
            dao.boundTagToGiftCertificate(3, targetGiftCertificateIdB);
            assertTrue(true);
        } catch (Throwable throwable) {
            fail();
        }
    }

    @Test
    void unboundTagFromGiftCertificateShouldFail() {
        try {
            dao.unboundTagFromGiftCertificate(2, targetGiftCertificateIdB);
            fail("Should be failed cause action is constraint violation"
                    + " (tags are already bounded to gift certificate).");
        } catch (Throwable throwable) {
            assertTrue(true);
        }
    }

    @Test
    void unboundTagFromGiftCertificateShouldNotFail() {
        try {
            dao.unboundTagFromGiftCertificate(1, targetGiftCertificateIdB);
            dao.unboundTagFromGiftCertificate(3, targetGiftCertificateIdB);
            assertTrue(true);
        } catch (Throwable throwable) {
            fail();
        }
    }

    @Test
    void isTagBoundToGiftCertificateShouldReturnTrue() {
        assertTrue(dao.isTagBoundToGiftCertificate(1, targetGiftCertificateIdB));
        assertTrue(dao.isTagBoundToGiftCertificate(3, targetGiftCertificateIdB));
    }

    @Test
    void isTagBoundToGiftCertificateShouldReturnFalse() {
        assertFalse(dao.isTagBoundToGiftCertificate(2, targetGiftCertificateIdB));
        assertFalse(dao.isTagBoundToGiftCertificate(4, targetGiftCertificateIdB));
    }
}
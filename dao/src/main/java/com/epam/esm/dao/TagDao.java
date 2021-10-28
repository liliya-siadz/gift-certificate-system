package com.epam.esm.dao;

import com.epam.esm.model.TagEntityModel;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    Optional<TagEntityModel> findById(long id);

    List<TagEntityModel> findAll();

    List<TagEntityModel> findAllTagsBoundToGiftCertificate(long certificateId);

    long create(TagEntityModel entityModel);

    boolean delete(long id);

    boolean isExist(long id);

    void boundTagToGiftCertificate(long id, long giftCertificateId);

    void unboundTagFromGiftCertificate(long id, long giftCertificateId);

    boolean isTagBoundToGiftCertificate(long id, long giftCertificateId);
}

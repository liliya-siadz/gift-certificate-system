package com.epam.esm.dao;

import com.epam.esm.model.GiftCertificateEntityModel;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao {
    long create(GiftCertificateEntityModel entity);

    List<GiftCertificateEntityModel> findAll();

    Optional<GiftCertificateEntityModel> findById(long id);

    boolean delete(long id);

    GiftCertificateEntityModel update(long id, GiftCertificateEntityModel entity);

    boolean isExist(long id);

    List<GiftCertificateEntityModel> search(String tagName, String name, String description, String sort);
}

package com.epam.esm.service;

import com.epam.esm.model.GiftCertificateClientModel;

import java.util.List;

public interface GiftCertificateService {
    GiftCertificateClientModel create(GiftCertificateClientModel clientModel);

    List<GiftCertificateClientModel> findAll();

    GiftCertificateClientModel findById(Long id);

    GiftCertificateClientModel delete(Long id);

    GiftCertificateClientModel update(Long id, GiftCertificateClientModel clientModel);

    boolean isExist(Long id);

    List<GiftCertificateClientModel> search(String tagName, String name, String description, String sort);
}

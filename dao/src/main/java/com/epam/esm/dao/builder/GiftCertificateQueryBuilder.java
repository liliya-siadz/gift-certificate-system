package com.epam.esm.dao.builder;

import com.epam.esm.model.GiftCertificateEntityModel;

import java.util.Map;

public interface GiftCertificateQueryBuilder {
    String EMPTY_STRING = "";

    String buildUpdateQuery(long id, GiftCertificateEntityModel entity);

    String buildSearchQuery(String tagName, String name, String description, String sort);

    Map<String, FieldDescription> findUpdateParams(GiftCertificateEntityModel entity);
}

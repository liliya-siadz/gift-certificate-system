package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.model.GiftCertificateModel;
import org.mapstruct.Mapper;

/**
 * Maps Gift Certificate entity model to client model and client model to entity model .
 */
@Mapper(componentModel = "spring")
public interface GiftCertificateMapper
        extends EntityModelMapper<GiftCertificate, GiftCertificateModel> {

    /**
     * Maps Gift Certificate entity to client model .
     *
     * @param entity entity model
     * @return client model
     */
    GiftCertificateModel toModel(GiftCertificate entity);

    /**
     * Maps Gift Certificate client model to entity model
     *
     * @param model client model
     * @return entity model
     */
    GiftCertificate toEntity(GiftCertificateModel model);
}

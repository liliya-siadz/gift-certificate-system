package com.epam.esm.mapper;

import com.epam.esm.model.GiftCertificateClientModel;
import com.epam.esm.model.GiftCertificateEntityModel;
import org.mapstruct.Mapper;

/**
 * Maps Gift Certificate entity model to client model and client model to entity model .
 */
@Mapper(componentModel = "spring")
public interface GiftCertificateModelMapper {

    /**
     * Maps Gift Certificate entity to client model .
     *
     * @param entity entity model
     * @return client model
     */
    GiftCertificateClientModel toClientModel(GiftCertificateEntityModel entity);

    /**
     * Maps Gift Certificate client model to entity model
     *
     * @param clientModel client model
     * @return entity model
     */
    GiftCertificateEntityModel toEntity(GiftCertificateClientModel clientModel);
}

package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.clientmodel.GiftCertificateClientModel;

/**
 * Maps Gift Certificate entity model to client model and client model to entity model .
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface GiftCertificateMapper
        extends Mapper<GiftCertificateEntity, GiftCertificateClientModel> {
}

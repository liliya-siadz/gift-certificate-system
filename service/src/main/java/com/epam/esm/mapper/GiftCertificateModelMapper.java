package com.epam.esm.mapper;

import com.epam.esm.model.GiftCertificateClientModel;
import com.epam.esm.model.GiftCertificateEntityModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GiftCertificateModelMapper {
    GiftCertificateClientModel toClientModel(GiftCertificateEntityModel entity);

    GiftCertificateEntityModel toEntity(GiftCertificateClientModel clientModel);
}

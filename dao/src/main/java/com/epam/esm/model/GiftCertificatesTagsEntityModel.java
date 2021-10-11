package com.epam.esm.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GiftCertificatesTagsEntityModel {
    private long id;
    private long giftCertificateId;
    private long tagId;
}

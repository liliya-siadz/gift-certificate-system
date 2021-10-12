package com.epam.esm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GiftCertificatesTagsEntityModel extends AbstractEntityModel {
    private long giftCertificateId;
    private long tagId;
}

package com.epam.esm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GiftCertificatesTagsEntityModel extends AbstractEntityModel {
    private long giftCertificateId;
    private long tagId;

    @Override
    public String toString() {
        return "GiftCertificatesTagsEntityModel{"
                + "id='" + getId() + '\''
                + "giftCertificateId=" + giftCertificateId
                + ", tagId=" + tagId
                + '}';
    }
}

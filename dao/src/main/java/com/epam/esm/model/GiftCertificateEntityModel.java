package com.epam.esm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class GiftCertificateEntityModel extends AbstractEntityModel {
    private String name;
    private String description;
    private double price;
    private int duration;
    private Date createDate;
    private Date lastUpdateDate;

    @Override
    public String toString() {
        return "GiftCertificateEntityModel{"
                + "id='" + getId() + '\''
                + "name='" + name + '\''
                + ", description='" + description + '\''
                + ", price=" + price
                + ", duration=" + duration
                + ", createDate=" + createDate
                + ", lastUpdateDate=" + lastUpdateDate
                + '}';
    }
}

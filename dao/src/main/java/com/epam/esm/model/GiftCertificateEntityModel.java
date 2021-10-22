package com.epam.esm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class GiftCertificateEntityModel extends AbstractEntityModel {
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;

    @Override
    public String toString() {
        return "GiftCertificateEntityModel{"
                + "id='" + getId() + '\''
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", price=" + price + '\''
                + ", duration=" + duration + '\''
                + ", createDate=" + createDate + '\''
                + ", lastUpdateDate=" + lastUpdateDate + '\''
                + '}';
    }
}

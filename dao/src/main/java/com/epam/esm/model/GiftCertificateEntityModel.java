package com.epam.esm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity model for Gift Certificate entity  .
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
public class GiftCertificateEntityModel extends AbstractEntityModel {

    /**
     * Represents attribute 'name' .
     */
    private String name;

    /**
     * Represents attribute 'description' .
     */
    private String description;

    /**
     * Represents attribute 'price' .
     */
    private BigDecimal price;

    /**
     * Represents attribute 'duration' .
     */
    private int duration;

    /**
     * Represents attribute 'create_date' .
     */
    private LocalDateTime createDate;

    /**
     * Represents attribute 'last_update_date' .
     */
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

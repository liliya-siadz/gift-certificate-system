package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Client model of Gift Certificate .
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateModel extends AbstractModel {

    /**
     * Represents property 'name' .
     */
    private String name;

    /**
     * Represents property 'description' .
     */
    private String description;

    /**
     * Represents property 'price' .
     */
    private BigDecimal price;

    /**
     * Represents property 'duration' .
     */
    private Integer duration;

    /**
     * Represents property 'createDate' .
     */
    private String createDate;

    /**
     * Represents property 'lastUpdateDate' .
     */
    private String lastUpdateDate;

    /**
     * Represents related to Gift Certificate list of Tags .
     */
    private Set<TagModel> tags = new HashSet<>();

    @Override
    public String toString() {
        return "GiftCertificate{"
                + "id='" + getId() + '\''
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", price=" + price
                + ", duration=" + duration
                + ", createDate=" + createDate
                + ", lastUpdateDate=" + lastUpdateDate
                + ", tags=" + tags
                + '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof GiftCertificateModel) {
            GiftCertificateModel someCertificate = (GiftCertificateModel) object;
            String someCertificateName = someCertificate.getName();
            return (Objects.equals(name, someCertificateName));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

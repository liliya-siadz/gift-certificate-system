package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Client model for model 'gift_certificate' .
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GiftCertificateClientModel extends AbstractClientModel {

    /**
     * Represents parameter 'name' .
     */
    private String name;

    /**
     * Represents parameter 'description' .
     */
    private String description;

    /**
     * Represents parameter 'price' .
     */
    private BigDecimal price;

    /**
     * Represents parameter 'duration' .
     */
    private Integer duration;

    /**
     * Represents parameter 'createDate' .
     */
    private String createDate;

    /**
     * Represents parameter 'lastUpdateDate' .
     */
    private String lastUpdateDate;

    /**
     * Represents parameter 'tags', may contain bounded to certificate tags .
     */
    private List<TagClientModel> tags;

    @Override
    public String toString() {
        return "GiftCertificateClientModel{"
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
}

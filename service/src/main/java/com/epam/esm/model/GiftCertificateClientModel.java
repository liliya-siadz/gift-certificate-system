package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateClientModel extends AbstractClientModel {
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private String createDate;
    private String lastUpdateDate;
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

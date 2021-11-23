package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity for Gift Certificate, represents table 'gift_certificate' .
 */
@Entity
@Table(name = "gift_certificate")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateEntity {

    /**
     * Represents column 'id' .
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Represents column 'name' .
     */
    @Column(unique = true, nullable = false, length = 200)
    private String name;

    /**
     * Represents column 'description' .
     */
    @Column(length = 2000)
    private String description;

    /**
     * Represents column 'price' .
     */
    @Column(nullable = false)
    private BigDecimal price;

    /**
     * Represents column 'duration' .
     */
    @Column(nullable = false)
    private int duration;

    /**
     * Represents column 'create_date' .
     */
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    /**
     * Represents column 'last_update_date' .
     */
    @Column(name = "last_update_date", nullable = false, updatable = false)
    private LocalDateTime lastUpdateDate;

    /**
     * Represents related to Gift Certificate list of Tags
     * (using many-to-many relation table 'gift_certificate_tags') .
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "gift_certificates_tags",
            joinColumns = @JoinColumn(name = "gift_certificate_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id", table = "tag"))
    private List<TagEntity> tags = new ArrayList<>();

    /**
     * Pre persist operation for setting value
     * for column 'create_date' if it is not present
     * and copying create date value to column 'last_update_date' .
     */
    @PrePersist
    public void prePersist() {
        if (createDate == null) {
            createDate = LocalDateTime.now();
        }
        lastUpdateDate = createDate;
    }

    /**
     * Pre update operation for setting date for column 'last_update_date' .
     */
    @PreUpdate
    public void preUpdate() {
        lastUpdateDate = LocalDateTime.now();
    }
}

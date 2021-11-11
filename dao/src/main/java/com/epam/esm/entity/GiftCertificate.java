package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entity for Gift Certificate, represents table 'gift_certificate' .
 */
@Entity
@Table(name = "gift_certificate")
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificate {

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
    private Set<Tag> tags = new HashSet<>();

    /**
     * Pre persist operation for setting value
     * for column 'create_date' if it is not present
     * and copying create date value to column 'last_update_date' .
     */
    @PrePersist
    public void prePersist() {
        if(createDate == null) {
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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof GiftCertificate) {
            GiftCertificate someCertificate = (GiftCertificate) object;
            String someCertificateId = someCertificate.getName();
            return (Objects.equals(name, someCertificateId));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

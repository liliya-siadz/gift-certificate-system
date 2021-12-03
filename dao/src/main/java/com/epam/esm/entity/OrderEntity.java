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
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity for Order, represents table 'order' .
 */
@Entity
@Table(name = "\"order\"")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    /**
     * Represents column 'id' .
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Represents column 'cost' .
     */
    @Column(name = "cost", nullable = false, updatable = false)
    private BigDecimal cost;

    /**
     * Represents column 'purchase_date' .
     */
    @Column(name = "purchase_date", nullable = false, updatable = false)
    private LocalDateTime purchaseDate;

    /**
     * Represents column 'user_id' .
     */
    @Column(name = "user_id", nullable = false, updatable = false)
    private long userId;

    /**
     * Represents related Gift Certificates .
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "orders_gift_certificates",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_id", referencedColumnName = "id"))
    private List<GiftCertificateEntity> certificates = new ArrayList<>();

    /**
     * Pre persist operation for setting value
     * for column 'purchase_date' if it is not present .
     */
    @PrePersist
    public void prePersist() {
        if (purchaseDate == null) {
            purchaseDate = LocalDateTime.now();
        }
    }
}

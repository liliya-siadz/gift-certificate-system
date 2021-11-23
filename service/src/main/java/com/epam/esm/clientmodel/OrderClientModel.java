package com.epam.esm.clientmodel;

import com.epam.esm.validator.constraint.Iso8601LocalDateTime;
import com.epam.esm.validator.constraint.PastOrPresent;
import com.epam.esm.validator.group.IdChecks;
import com.epam.esm.validator.group.OrderChecks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Client model of Order .
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderClientModel {

    /**
     * Order's id.
     */
    @Range(min = 1, max = 2147483647, groups = IdChecks.class)
    private Long id;

    /**
     * Order's user .
     */
    @NotNull(groups = OrderChecks.class)
    @Valid
    private UserClientModel user;

    /**
     * Order's price .
     */
    @Null(groups = OrderChecks.class)
    @Positive(groups = OrderChecks.class)
    private BigDecimal cost;

    /**
     * Order's purchase date .
     */
    @PastOrPresent(groups = OrderChecks.class)
    @Iso8601LocalDateTime(groups = OrderChecks.class)
    private String purchaseDate;

    /**
     * Order's Gift Certificates .
     */
    @NotNull(groups = OrderChecks.class)
    @Size(min = 1, groups = OrderChecks.class)
    @Valid
    private List<GiftCertificateClientModel> certificates = new ArrayList<>();
}
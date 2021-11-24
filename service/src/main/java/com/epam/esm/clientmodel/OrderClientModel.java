package com.epam.esm.clientmodel;

import com.epam.esm.validator.constraint.Iso8601LocalDateTime;
import com.epam.esm.validator.constraint.PastOrPresent;
import com.epam.esm.validator.constraint.UniqueCertificates;
import com.epam.esm.validator.group.CreateChecks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
public class OrderClientModel extends AbstractClientModel {

    /**
     * Order's user .
     */
    @NotNull(groups = CreateChecks.class)
    @Valid
    private UserClientModel user;

    /**
     * Order's price .
     */
    @NotNull(groups = CreateChecks.class)
    @Positive(groups = CreateChecks.class)
    private BigDecimal cost;

    /**
     * Order's purchase date .
     */
    @PastOrPresent(groups = CreateChecks.class)
    @Iso8601LocalDateTime(groups = CreateChecks.class)
    private String purchaseDate;

    /**
     * Order's Gift Certificates .
     */
    @NotNull(groups = CreateChecks.class)
    @Size(min = 1, groups = CreateChecks.class)
    @UniqueCertificates (groups = CreateChecks.class)
    @Valid
    private List<GiftCertificateClientModel> certificates = new ArrayList<>();
}

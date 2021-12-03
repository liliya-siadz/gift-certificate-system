package com.epam.esm.clientmodel;

import com.epam.esm.validator.constraint.Iso8601LocalDateTime;
import com.epam.esm.validator.constraint.PastOrPresent;
import com.epam.esm.validator.group.IdChecks;
import com.epam.esm.validator.group.OrderChecks;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Client model of Order for request .
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderClientModel extends RepresentationModel<OrderClientModel> {

    /**
     * Order's id.
     */
    @Range(min = 1, max = 2147483647, groups = IdChecks.class)
    private Long id;

    /**
     * User's id of order .
     */
    @NotNull(groups = OrderChecks.class)
    @Range(min = 1, max = 2147483647, groups = OrderChecks.class)
    private Long userId;

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

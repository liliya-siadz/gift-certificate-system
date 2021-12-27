package com.epam.esm.clientmodel;

import com.epam.esm.validator.constraint.Iso8601LocalDateTime;
import com.epam.esm.validator.constraint.PastOrPresent;
import com.epam.esm.validator.constraint.UniqueTags;
import com.epam.esm.validator.group.CreateChecks;
import com.epam.esm.validator.group.OrderChecks;
import com.epam.esm.validator.group.UpdateChecks;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Client model of Gift Certificate .
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateClientModel extends RepresentationModel<GiftCertificateClientModel> {

    /**
     * Id of client model .
     */
    @NotNull(groups = OrderChecks.class)
    @Range(min = 1, max = 2147483647, groups = OrderChecks.class)
    private Long id;

    /**
     * Gift Certificate's name.
     */
    @NotNull(groups = CreateChecks.class)
    @Length(min = 1, max = 200, groups = {CreateChecks.class, UpdateChecks.class})
    @Pattern(regexp = "[\\w\\s',&×.()-]{2,}", groups = {CreateChecks.class, UpdateChecks.class})
    private String name;

    /**
     * Gift Certificate's description .
     */
    @Length(min = 1, max = 2000, groups = {CreateChecks.class, UpdateChecks.class})
    @Pattern(regexp = ".*[\\w\\s'&×,.()-]+.*", groups = {CreateChecks.class, UpdateChecks.class})
    private String description;

    /**
     * Gift Certificate's price .
     */
    @NotNull(groups = CreateChecks.class)
    @Positive(groups = {CreateChecks.class, UpdateChecks.class})
    private BigDecimal price;

    /**
     * Gift Certificate's duration .
     */
    @NotNull(groups = CreateChecks.class)
    @Range(min = 1, max = 32768, groups = {CreateChecks.class, UpdateChecks.class})
    private Integer duration;

    /**
     * Gift Certificate's createDate .
     */
    @PastOrPresent(groups = {CreateChecks.class, UpdateChecks.class})
    @Iso8601LocalDateTime(groups = {CreateChecks.class, UpdateChecks.class})
    private String createDate;

    /**
     * Gift Certificate's lastUpdateDate .
     */
    @PastOrPresent(groups = {CreateChecks.class, UpdateChecks.class})
    @Iso8601LocalDateTime(groups = {CreateChecks.class, UpdateChecks.class})
    private String lastUpdateDate;

    /**
     * Related to Gift Certificate list of Tags .
     */
    @UniqueTags(groups = {CreateChecks.class, UpdateChecks.class})
    @Valid
    private List<TagClientModel> tags = new ArrayList<>();
}

package com.epam.esm.clientmodel;

import com.epam.esm.validator.constraint.IsoLocalDateTimeConstraint;
import com.epam.esm.validator.constraint.PastOrPresentConstraint;
import com.epam.esm.validator.constraint.TagsConstraint;
import com.epam.esm.validator.constraint.TagsSetConstraint;
import com.epam.esm.validator.group.CreateChecks;
import com.epam.esm.validator.group.SpecialMessageChecks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

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
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateClientModel extends AbstractClientModel {

    /**
     * Gift Certificate's name.
     */
    @NotNull(groups = {CreateChecks.class})
    @Length(min = 1, max = 200)
    @Pattern(regexp = ".*[a-zA-Z]+.*")
    private String name;

    /**
     * Gift Certificate's description .
     */
    @Length(min = 1, max = 2000)
    @Pattern(regexp = ".*[a-zA-Z]+.*")
    private String description;

    /**
     * Gift Certificate's price .
     */
    @NotNull(groups = {CreateChecks.class})
    @Positive
    private BigDecimal price;

    /**
     * Gift Certificate's duration .
     */
    @NotNull(groups = {CreateChecks.class})
    @Range(min = 1, max = 32768)
    private Integer duration;

    /**
     * Gift Certificate's createDate .
     */
    @PastOrPresentConstraint
    @IsoLocalDateTimeConstraint
    private String createDate;

    /**
     * Gift Certificate's lastUpdateDate .
     */
    @PastOrPresentConstraint
    @IsoLocalDateTimeConstraint
    private String lastUpdateDate;

    /**
     * Related to Gift Certificate list of Tags .
     */
    @TagsSetConstraint
    @TagsConstraint (groups = {SpecialMessageChecks.class})
    private List<TagClientModel> tags = new ArrayList<>();
}

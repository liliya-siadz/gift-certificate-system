package com.epam.esm.clientmodel;

import com.epam.esm.validator.group.CreateChecks;
import com.epam.esm.validator.group.IdChecks;
import com.epam.esm.validator.group.UpdateChecks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Client model of Tag .
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TagClientModel extends RepresentationModel<TagClientModel> {

    /**
     * Tag's id .
     */
    @Range(min = 1, max = 2147483647, groups = IdChecks.class)
    private Long id;

    /**
     * Tag's name .
     */
    @NotNull(groups = CreateChecks.class)
    @Length(min = 1, max = 200, groups = {CreateChecks.class, UpdateChecks.class})
    @Pattern(regexp = "[\\w\\s,.'&×()-]{2,}", groups = {CreateChecks.class, UpdateChecks.class})
    private String name;
}

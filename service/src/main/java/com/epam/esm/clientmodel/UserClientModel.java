package com.epam.esm.clientmodel;

import com.epam.esm.validator.group.OrderChecks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Client model of User .
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserClientModel extends RepresentationModel<UserClientModel> {

    /**
     * Id of client model .
     */
    @NotNull(groups = OrderChecks.class)
    @Range(min = 1, max = 2147483647, groups = OrderChecks.class)
    private Long id;

    /**
     * User's name .
     */
    @Length(min = 1, max = 200)
    @Pattern(regexp = "[\\w\\s()-]{2,}")
    private String name;
}

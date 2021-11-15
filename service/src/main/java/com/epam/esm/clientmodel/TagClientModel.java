package com.epam.esm.clientmodel;

import com.epam.esm.validator.group.CreateChecks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Client model of Tag .
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TagClientModel extends AbstractClientModel {

    /**
     * Tag's name .
     */
    @NotNull (groups = {CreateChecks.class})
    @Length(min = 1, max = 200)
    @Pattern(regexp = ".*[a-zA-Z]+.*")
    private String name;
}

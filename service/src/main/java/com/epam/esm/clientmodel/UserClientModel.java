package com.epam.esm.clientmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * Client model of User .
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserClientModel extends AbstractClientModel {

    /**
     * User's name .
     */
    @Length(min = 1, max = 200)
    @Pattern(regexp = "[a-zA-Z]{2,}")
    private String name;
}

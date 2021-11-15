package com.epam.esm.clientmodel;

import com.epam.esm.validator.group.IdChecks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

/**
 * Abstract base client model .
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class AbstractClientModel {

    /**
     * Id of client model .
     */
    @Range(min = 1, max = 200, groups = {IdChecks.class})
    private Long id;
}

package com.epam.esm.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TagEntityModel implements EntityModel {
    private long id;
    private String name;
}

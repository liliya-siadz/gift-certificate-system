package com.epam.esm.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TagClientModel implements ClientModel {
    private long id;
    private String name;
}

package com.epam.esm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TagEntityModel extends AbstractEntityModel {
    private String name;

    public TagEntityModel(long id, String name) {
        super(id);
        this.name = name;
    }
}

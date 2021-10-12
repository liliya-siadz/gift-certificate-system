package com.epam.esm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TagClientModel extends AbstractClientModel {
    private String name;

    public TagClientModel(long id, String name) {
        super(id);
        this.name = name;
    }
}

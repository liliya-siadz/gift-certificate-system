package com.epam.esm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TagEntityModel extends AbstractEntityModel {
    private String name;

    public TagEntityModel(long id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return "TagEntityModel{"
                + "id='" + getId() + '\''
                + ", name='" + name + '\''
                + '}';
    }
}

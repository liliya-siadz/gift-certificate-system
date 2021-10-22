package com.epam.esm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TagClientModel extends AbstractClientModel {

    private String name;

    public TagClientModel(Long id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return "TagClientModel{"
                + "id='" + getId() + '\''
                + ", name='" + name + '\''
                + '}';
    }
}

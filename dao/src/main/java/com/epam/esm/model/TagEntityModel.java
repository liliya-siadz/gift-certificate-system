package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity model for Tag entity .
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TagEntityModel extends AbstractEntityModel {

    /**
     * Represents attribute 'name' .
     */
    private String name;

    /**
     * Constructs Tag entity with id and name .
     *
     * @param id   attribute 'id' of entity
     * @param name attribute 'name' of entity
     */
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

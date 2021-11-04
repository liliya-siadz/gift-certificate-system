package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Client model of Tag .
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TagClientModel extends AbstractClientModel {

    /**
     * Represents parameter 'name' .
     */
    private String name;

    /**
     * Constructs client model Tag with specified id and name .
     *
     * @param id tag's id
     * @param name tag's name
     */
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

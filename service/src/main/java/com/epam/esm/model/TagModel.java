package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

/**
 * Client model of Tag .
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TagModel extends AbstractModel {

    /**
     * Represents property 'name' .
     */
    private String name;

    /**
     * Constructs client model Tag with specified id and name .
     *
     * @param id   tag's id
     * @param name tag's name
     */
    public TagModel(Long id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof TagModel) {
            TagModel someCertificate = (TagModel) object;
            String someTagName = someCertificate.getName();
            return (Objects.equals(name, someTagName));
        }
        return false;
    }

    @Override
    public String toString() {
        return "Tag{"
                + "id='" + getId() + '\''
                + ", name='" + name + '\''
                + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

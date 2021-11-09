package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Entity for Tag, represents table 'tag' .
 */
@Entity
@Table(name = "tag")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    /**
     * Represents column 'id' .
     */
    @Id
    @Column(table = "tag")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Represents column 'name' .
     */
    @Column(name = "name", table = "tag",unique = true, nullable = false, length = 200)
    private String name;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof Tag) {
            Tag someTag = (Tag) object;
            String someTagName = someTag.getName();
            return (Objects.equals(name, someTagName));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

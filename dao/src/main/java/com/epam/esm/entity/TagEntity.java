package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity for Tag, represents table 'tag' .
 */
@Entity
@Table(name = "tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagEntity {

    /**
     * Represents column 'id' .
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Represents column 'name' .
     */
    @Column(name = "name", unique = true, nullable = false, length = 200)
    private String name;
}

package com.epam.esm.mapper;

import com.epam.esm.entity.Tag;
import com.epam.esm.model.TagModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {TestServiceConfiguration.class})
@ActiveProfiles("prod")
class TagModelMapperTest {
    @Autowired
    private TagMapper mapper;

    private Tag entity;
    private TagModel clientModel;

    @BeforeEach
    void setUpModels() {
        long id = 10L;
        String name = "Name name name name 1 2 3";
        entity = new Tag(id, name);
        clientModel = new TagModel(id, name);
    }

    @Test
    void toClientModel() {
        TagModel actual = mapper.toModel(entity);
        assertEquals(clientModel, actual);
    }

    @Test
    void toEntity() {
        Tag actual = mapper.toEntity(clientModel);
        assertEquals(entity, actual);
    }
}
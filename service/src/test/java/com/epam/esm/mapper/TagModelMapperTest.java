package com.epam.esm.mapper;

import com.epam.esm.configuration.TestServiceConfiguration;
import com.epam.esm.model.TagClientModel;
import com.epam.esm.model.TagEntityModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {TestServiceConfiguration.class})
class TagModelMapperTest {

    @Autowired
    private TagModelMapper mapper;

    private TagEntityModel entity;
    private TagClientModel clientModel;

    @BeforeEach
    void setUpModels() {
        long id = 10L;
        String name = "Name name name name 1 2 3";
        entity = new TagEntityModel(id, name);
        clientModel = new TagClientModel(id, name);
    }

    @Test
    void toClientModel() {
        TagClientModel actual = mapper.toClientModel(entity);
        assertEquals(clientModel, actual);
    }

    @Test
    void toEntity() {
        TagEntityModel actual = mapper.toEntity(clientModel);
        assertEquals(entity, actual);
    }
}
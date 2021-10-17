package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.mapper.TagClientEntityModelMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.nullable;

@ExtendWith({MockitoExtension.class})
class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagDaoImpl tagDao;

    @Spy
    private TagClientEntityModelMapper tagClientEntityModelMapper
            = Mappers.getMapper(TagClientEntityModelMapper.class);

    @Test
    void findById() {
       doThrow(new IllegalArgumentException("Id of tag is null"))
               .when(tagService).findById(nullable(Long.class));

    }
}
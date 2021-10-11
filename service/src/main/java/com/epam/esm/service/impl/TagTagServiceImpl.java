package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.mapper.impl.TagClientEntityModelMapperImpl;
import com.epam.esm.model.TagClientModel;
import com.epam.esm.model.TagEntityModel;
import com.epam.esm.service.TagService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@NoArgsConstructor
@Service
public class TagTagServiceImpl implements TagService<TagClientModel> {

    @Autowired
    private TagDao tagDao;

    @Autowired
    private TagClientEntityModelMapperImpl tagClientEntityModelMapper;

    @Override
    public TagClientModel findById(long id) {
        TagEntityModel tagEntityModel = tagDao.findById(id);
        TagClientModel tagClientModel =
                tagClientEntityModelMapper.entityToClient(tagEntityModel);
        return tagClientModel;
    }
}

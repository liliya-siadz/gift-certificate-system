package com.epam.esm.service.impl;

import com.epam.esm.clientmodel.TagClientModel;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.ResourceWithNameExistsException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.preparator.Preparator;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.ResourceNames;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link TagService} interface,
 * for presenting access to service operations with Tag .
 */
@Service
public class TagServiceImpl extends AbstractService<TagEntity, TagClientModel> implements TagService {

    /**
     * Preparator for preparing Tag client models to service operations .
     */
    @Autowired
    private Preparator<TagClientModel> preparator;

    /**
     * Constructs <code>TagServiceImpl</code> class with dao, mapper, validator .
     *
     * @param repository {@link #repository}
     * @param mapper     {@link #mapper}
     */
    public TagServiceImpl(TagRepository repository, TagMapper mapper) {
        super(repository, mapper);
    }

    @Override
    @Transactional
    public TagClientModel create(TagClientModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Parameter 'model' is null.");
        }
        try {
            preparator.prepareForCreate(model);
            return mapper.toClientModel(repository.save(mapper.toEntity(model)));
        } catch (DataIntegrityViolationException exception) {
            throw new ResourceWithNameExistsException(
                    ResourceNames.getResourceName(repository.getEntityClass()), model.getName(), exception);
        }
    }

    @Override
    public TagClientModel findMostPopularTag() {
        return mapper.toClientModel(((TagRepository) repository).findMostPopularTag());
    }
}

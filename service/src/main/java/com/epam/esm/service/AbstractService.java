package com.epam.esm.service;

import com.epam.esm.dao.Dao;
import com.epam.esm.exception.ResourceWithIdNotFoundException;
import com.epam.esm.mapper.EntityModelMapper;
import com.epam.esm.model.AbstractModel;
import com.epam.esm.validator.Validator;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Base abstract class for classes in package {@link com.epam.esm.service.impl} .
 *
 * @param <T> type of entity that used in class
 * @param <S> type of model that used in class,
 *            must extend class {@link com.epam.esm.model.AbstractModel}
 */
public abstract class AbstractService<T, S extends AbstractModel> implements BaseService<S> {

    /**
     * Dao class for repository operations .
     */
    protected final Dao<T> dao;

    /**
     * Mapper for mapping from entity to client model and otherwise .
     */
    protected final EntityModelMapper<T, S> mapper;

    /**
     * Validator for validating client models .
     */
    protected final Validator<S> validator;

    /**
     * Constructs <code>AbstractService</code> class
     * with passed dao, mapper and validator .
     *
     * @param dao       {@link #dao}
     * @param mapper    {@link #mapper}
     * @param validator {@link #validator}
     */
    public AbstractService(Dao dao, EntityModelMapper mapper,
                           Validator<S> validator) {
        this.dao = dao;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override
    public abstract S create(S model);

    @Override
    public Set<S> findAll() {
        return dao.findAll().stream().map(mapper::toModel).collect(Collectors.toSet());
    }

    @Override
    public S findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is null.");
        } else {
            Optional<T> optionalEntity = dao.findById(id);
            if (optionalEntity.isPresent()) {
                return mapper.toModel(optionalEntity.get());
            } else {
                throw new ResourceWithIdNotFoundException(dao.retrieveEntityClass().getSimpleName(), id);
            }
        }
    }

    @Override
    @Transactional
    public S delete(Long id) {
        Optional<T> optionalEntity = dao.findById(id);
        if (!optionalEntity.isPresent()) {
            throw new ResourceWithIdNotFoundException(dao.retrieveEntityClass().getSimpleName(), id);
        }
        S model = mapper.toModel(optionalEntity.get());
        dao.delete(id);
        return model;
    }

    @Override
    public boolean isExist(Long id) {
        return ((id != null) && (dao.isExist(id)));
    }
}

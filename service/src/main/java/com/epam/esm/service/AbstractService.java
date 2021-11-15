package com.epam.esm.service;

import com.epam.esm.clientmodel.AbstractClientModel;
import com.epam.esm.dao.Dao;
import com.epam.esm.exception.ResourceWithIdNotFoundException;
import com.epam.esm.mapper.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Base abstract class for classes in package {@link com.epam.esm.service.impl} .
 *
 * @param <T> type of entity that used in class
 * @param <S> type of model that used in class,
 *            must extend class {@link AbstractClientModel}
 */
public abstract class AbstractService<T, S extends AbstractClientModel> implements BaseService<S> {

    /**
     * Dao class for repository operations .
     */
    private final Dao<T> dao;

    /**
     * Mapper for mapping from entity to client model and otherwise .
     */
    private final Mapper<T, S> mapper;

    /**
     * Constructs <code>AbstractService</code> class
     * with passed dao, mapper and validator .
     *
     * @param dao       {@link #dao}
     * @param mapper    {@link #mapper}
     */
    public AbstractService(Dao dao, Mapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public abstract S create(S model);

    @Override
    public List<S> findAll() {
        return dao.findAll().stream().map(mapper::toClientModel).collect(Collectors.toList());
    }

    @Override
    public S findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is null.");
        } else {
            Optional<T> optionalEntity = dao.findById(id);
            if (optionalEntity.isPresent()) {
                return mapper.toClientModel(optionalEntity.get());
            } else {
                throw new ResourceWithIdNotFoundException(dao.getEntityClass().getSimpleName(), id);
            }
        }
    }

    @Override
    @Transactional
    public S delete(Long id) {
        Optional<T> optionalEntity = dao.findById(id);
        if (!optionalEntity.isPresent()) {
            throw new ResourceWithIdNotFoundException(dao.getEntityClass().getSimpleName(), id);
        }
        S model = mapper.toClientModel(optionalEntity.get());
        dao.delete(id);
        return model;
    }

    @Override
    public boolean isExist(Long id) {
        return ((id != null) && (dao.isExist(id)));
    }
}

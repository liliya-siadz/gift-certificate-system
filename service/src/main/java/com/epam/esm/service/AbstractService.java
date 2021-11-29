package com.epam.esm.service;

import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.dao.Dao;
import com.epam.esm.exception.ResourceWithIdNotFoundException;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.preparator.Preparator;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.epam.esm.service.ResourceNames.getResourceName;

/**
 * Base abstract class for classes in package {@link com.epam.esm.service.impl} .
 *
 * @param <T> type of entity that used in class
 * @param <S> type of client model that used in class,
 */
public abstract class AbstractService<T, S> implements BaseService<S> {
    protected static final int DEFAULT_PAGE_SIZE = 5;
    protected static final int DEFAULT_PAGE_NUMBER = 1;

    /**
     * Dao class for repository operations .
     */
    private final Dao<T> dao;

    /**
     * Mapper for mapping from entity to client model and otherwise .
     */
    private final Mapper<T, S> mapper;

    /**
     * Preparator for update/create operations
     */
    private Preparator<S> preparator;

    /**
     * Constructs <code>AbstractService</code> class
     * with passed dao, mapper and validator .
     *
     * @param dao    {@link #dao}
     * @param mapper {@link #mapper}
     */
    public AbstractService(Dao dao, Mapper mapper, Preparator<S> preparator) {
        this.dao = dao;
        this.mapper = mapper;
        this.preparator = preparator;
    }

    @Override
    public abstract S create(S model);

    @Override
    public PageableClientModel<S> findAll(Integer pageSize, Integer pageNumber) {
        if ((pageSize == null) || (pageNumber == null)) {
            throw new IllegalArgumentException("Parameter 'pageSize' or 'pageNumber' is null.");
        }
        return mapper.toClientModel(dao.findAll(pageSize, pageNumber));
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
                throw new ResourceWithIdNotFoundException(getResourceName(dao.getEntityClass()), id);
            }
        }
    }

    @Override
    @Transactional
    public S delete(Long id) {
        Optional<T> optionalEntity = dao.findById(id);
        if (!optionalEntity.isPresent()) {
            throw new ResourceWithIdNotFoundException(getResourceName(dao.getEntityClass()), id);
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

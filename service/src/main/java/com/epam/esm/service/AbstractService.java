package com.epam.esm.service;

import com.epam.esm.clientmodel.PageableClientModel;
import com.epam.esm.exception.ResourceWithIdNotFoundException;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.repository.Repository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.epam.esm.service.ResourceNames.getResourceName;

/**
 * Base abstract class for classes in package {@link com.epam.esm.service.impl} .
 *
 * @param <T> type of entity that used in class
 * @param <S> type of client model that used in class,
 */
@Component
public abstract class AbstractService<T, S> implements BaseService<S> {

    /**
     * Dao class for repository operations .
     */
    protected Repository<T> repository;

    /**
     * Mapper for mapping from entity to client model and otherwise .
     */
    protected Mapper<T, S> mapper;

    /**
     * Constructs <code>AbstractService</code> class
     * with passed dao, mapper and validator .
     *
     * @param repository {@link #repository}
     * @param mapper     {@link #mapper}
     */
    public AbstractService(Repository<T> repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public abstract S create(S model);

    @Override
    public PageableClientModel<S> findAll(Integer pageSize, Integer pageNumber) {
        if ((pageSize == null) || (pageNumber == null)) {
            throw new IllegalArgumentException("Parameter 'pageSize' or 'pageNumber' is null.");
        }
        return mapper.toClientModel(repository.findAll(PageRequest.of(--pageNumber, pageSize)));
    }

    @Override
    public S findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is null.");
        } else {
            return mapper.toClientModel(repository.findById(id).orElseThrow(() -> new ResourceWithIdNotFoundException
                    (getResourceName(repository.getEntityClass()), id)));
        }
    }

    @Override
    @Transactional
    public S delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Parameter 'id' is null.");
        }
        S model = mapper.toClientModel(repository.findById(id).orElseThrow(() -> new ResourceWithIdNotFoundException
                (getResourceName(repository.getEntityClass()), id)));
        repository.deleteById(id);
        return model;
    }

    @Override
    public boolean isExist(Long id) {
        return ((id != null) && (repository.existsById(id)));
    }
}

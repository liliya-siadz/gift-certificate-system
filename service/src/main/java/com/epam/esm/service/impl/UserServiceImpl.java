package com.epam.esm.service.impl;

import com.epam.esm.clientmodel.UserClientModel;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.exception.ResourceWithNameExistsException;
import com.epam.esm.exception.UserWithNameNotFoundException;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.preparator.Preparator;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.ResourceNames;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link UserService} interface,
 * for presenting access to service operations with User .
 */
@Service
public class UserServiceImpl extends AbstractService<UserEntity, UserClientModel> implements UserService {

    /**
     * Preparator for preparing User client models to service operations .
     */
    @Autowired
    private Preparator<UserClientModel> preparator;

    /**
     * Constructs <code>UserServiceImpl</code> class with repository and mapper  .
     *
     * @param repository {@link #repository}
     * @param mapper     {@link #mapper}
     */
    public UserServiceImpl(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }

    @Override
    @Transactional
    public UserClientModel create(UserClientModel model) {
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
    public UserClientModel findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Parameter 'name' is null.");
        }
        return ((UserRepository) repository).findByName(name)
                .map(mapper::toClientModel)
                .orElseThrow(() -> new UserWithNameNotFoundException(name));
    }
}
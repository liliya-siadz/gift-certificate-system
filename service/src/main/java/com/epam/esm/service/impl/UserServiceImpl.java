package com.epam.esm.service.impl;

import com.epam.esm.clientmodel.UserClientModel;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.exception.ResourceWithNameExistsException;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.preparator.Preparator;
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
     * Dao class for repository operations .
     */
    @Autowired
    private UserDao dao;

    /**
     * Mapper for mapping User from entity to client model and otherwise .
     */
    @Autowired
    private Mapper<UserEntity, UserClientModel> mapper;

    /**
     * Preparator for preparing User client models to service operations .
     */
    @Autowired
    private Preparator<UserClientModel> preparator;

    /**
     * Constructs <code>UserServiceImpl</code> class with dao, mapper  .
     *
     * @param dao    {@link #dao}
     * @param mapper {@link #mapper}
     */
    public UserServiceImpl(UserDao dao, UserMapper mapper) {
        super(dao, mapper);
    }

    @Override
    @Transactional
    public UserClientModel create(UserClientModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Parameter 'model' is null.");
        }
        try {
            preparator.prepareForCreate(model);
            return mapper.toClientModel(dao.create(mapper.toEntity(model)));
        } catch (DataIntegrityViolationException exception) {
            throw new ResourceWithNameExistsException(
                    ResourceNames.getResourceName(dao.getEntityClass()), model.getName(), exception);
        }
    }

    @Override
    public UserClientModel findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Parameter 'name' is null.");
        }
        return mapper.toClientModel(dao.findByName(name));
    }
}
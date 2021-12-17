package com.epam.esm.service;

import com.epam.esm.clientmodel.UserClientModel;

/**
 * Presents access to service operations with User .
 */
public interface UserService extends BaseService<UserClientModel> {

    /**
     * Finds User by it's name .
     *
     * @param name User's name
     * @return found User
     */
    UserClientModel findByName(String name);
}

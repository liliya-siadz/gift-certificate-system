package com.epam.esm.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Thrown to indicate, that User with passed name not found .
 */
public class UserWithNameNotFoundException extends UsernameNotFoundException {

    /**
     * Constructs <code>UserWithNameNotFoundException</code> class
     * with passed name .
     *
     * @param name User's name
     */
    public UserWithNameNotFoundException(String name) {
        super(name);
    }

    /**
     * Retrieves error message key .
     *
     * @return error message key of exception
     */
    public String getErrorMessageKey() {
        return "user_with_name_not_found";
    }
}

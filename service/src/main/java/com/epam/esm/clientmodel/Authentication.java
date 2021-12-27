package com.epam.esm.clientmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Client model of User for Authentication process .
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authentication {

    /**
     * User's username .
     */
    private String username;

    /**
     * User's encrypted password .
     */
    private String password;

    /**
     * User's jwt token .
     */
    private String jwtToken;
}

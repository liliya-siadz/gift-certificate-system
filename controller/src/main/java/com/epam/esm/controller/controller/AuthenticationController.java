package com.epam.esm.controller.controller;

import com.epam.esm.clientmodel.Authentication;
import com.epam.esm.clientmodel.UserClientModel;
import com.epam.esm.security.JwtTokenProvider;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for processing REST-api  sign up and login requests .
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    /**
     * Service for User resources .
     */
    private final UserService userService;

    /**
     * Jwt token provider .
     */
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Processes authentication requests .
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Constructs <code>AuthenticationController</code> class
     * with injected service, jwt token provider and authentication manager .
     *
     * @param userService           {@link #userService}
     * @param jwtTokenProvider      {@link #jwtTokenProvider}
     * @param authenticationManager {@link #authenticationManager}
     */

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(UserService userService, JwtTokenProvider jwtTokenProvider,
                                    AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Processes user's signup request, creates User resource .
     *
     * @param user client model of User resource to sing up
     * @return created client model of User
     */
    @PostMapping("/signup")
    public UserClientModel signup(@RequestBody UserClientModel user) {
        return userService.create(user);
    }

    /**
     * Processes login operations, authenticates User and creates
     * Jwt token for User .
     *
     * @param authentication client model for authentication process
     * @return result of authentication(login) process
     */

    @PostMapping("/signup")
    public UserClientModel signUp(@RequestBody UserClientModel user) {
        return userService.create(user);
    }

    @PostMapping("/login")
    public Authentication authenticate(@RequestBody Authentication authentication) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authentication.getUsername(), authentication.getPassword()));
        UserClientModel user = userService.findByName(authentication.getUsername());
        String token = jwtTokenProvider.createToken(user.getName(), user.getRole());
        return new Authentication(authentication.getUsername(), "******", token);
    }
}

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
        authentication.setPassword("**********");
        authentication.setJwtToken(token);
        return authentication;
    }
}

package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.SecurityConfig.AuthenticationService;
import org.example.SecurityConfig.JwtService;
import org.example.user.dto.*;
import org.example.user.model.User;
import org.example.util.auth.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public UserDto register(@RequestBody UserAuthDto userAuthDto) {
        logger.info(String.format("sign up user with name %s", userAuthDto.getName()));
        UserDto registeredUser = authenticationService.signup(userAuthDto);
        return registeredUser;
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse authenticate(@RequestBody UserAuthDto userAuthDto) {
        logger.info(String.format("user %s sign in", userAuthDto.getName()));
        User authenticatedUser = authenticationService.authenticate(userAuthDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .roles(authenticatedUser.getRoles().stream().map(x -> x.getName().name()).collect(Collectors.toList()))
                .build();
        return loginResponse;
    }
}

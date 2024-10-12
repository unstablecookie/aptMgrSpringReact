package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.security.AuthenticationService;
import org.example.user.dto.*;
import org.example.util.auth.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
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
        return authenticationService.authenticate(userAuthDto);
    }
}

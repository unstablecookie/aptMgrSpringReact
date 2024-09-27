package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.SecurityConfig.AuthenticationService;
import org.example.SecurityConfig.JwtService;
import org.example.user.dto.*;
import org.example.user.model.User;
import org.example.util.auth.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public UserDto register(@RequestBody UserAuthDto userAuthDto) {
        UserDto registeredUser = authenticationService.signup(userAuthDto);
        return registeredUser;
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse authenticate(@RequestBody UserAuthDto userAuthDto) {
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

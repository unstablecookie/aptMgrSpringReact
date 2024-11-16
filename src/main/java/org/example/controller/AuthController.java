package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.aspect.LogBeforeExecutionNoPasswd;
import org.example.security.AuthenticationService;
import org.example.user.dto.*;
import org.example.util.auth.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @LogBeforeExecutionNoPasswd
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public UserDto register(@RequestBody UserAuthDto userAuthDto) {
        UserDto registeredUser = authenticationService.signup(userAuthDto);
        return registeredUser;
    }

    @LogBeforeExecutionNoPasswd
    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse authenticate(@RequestBody UserAuthDto userAuthDto) {
        return authenticationService.authenticate(userAuthDto);
    }
}

package org.example.security;

import org.example.user.dto.UserAuthDto;
import org.example.user.dto.UserDto;
import org.example.util.auth.LoginResponse;

public interface AuthenticationService {
    UserDto signup(UserAuthDto userAuthDto);
    LoginResponse authenticate(UserAuthDto userAuthDto);
}

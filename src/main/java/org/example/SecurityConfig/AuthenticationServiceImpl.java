package org.example.SecurityConfig;

import lombok.AllArgsConstructor;
import org.example.user.RoleRepository;
import org.example.user.UserRepository;
import org.example.user.dto.UserAuthDto;
import org.example.user.dto.UserDto;
import org.example.user.dto.UserMapper;
import org.example.user.model.Role;
import org.example.user.model.User;
import org.example.util.auth.LoginResponse;
import org.example.util.error.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserDto signup(UserAuthDto userAuthDto) {
        Role role = roleRepository.findById(1L).orElseThrow(
                () -> new EntityNotFoundException(String.format("Role with id=%d was not found", 1L),
                        "The required object was not found."));
        User user = UserMapper.toUser(userAuthDto, passwordEncoder);
        user.setRoles(List.of(role));
        return UserMapper.toUserDto(userRepository.save(user));
    }

    public LoginResponse authenticate(UserAuthDto userAuthDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthDto.getName(),
                userAuthDto.getPassword()));
        User user = userRepository.findByName(userAuthDto.getName()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .roles(user.getRoles().stream().map(x -> x.getName().name()).collect(Collectors.toList()))
                .build();

    }
}

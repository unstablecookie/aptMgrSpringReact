package org.example.SecurityConfig;

import lombok.AllArgsConstructor;
import org.example.user.RoleRepository;
import org.example.user.UserRepository;
import org.example.user.dto.UserAuthDto;
import org.example.user.dto.UserDto;
import org.example.user.dto.UserMapper;
import org.example.user.model.Role;
import org.example.user.model.User;
import org.example.util.error.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserDto signup(UserAuthDto userAuthDto) {
        Role role = roleRepository.findById(1L).orElseThrow(
                () -> new EntityNotFoundException(String.format("Role with id=%d was not found", 1L),
                        "The required object was not found."));
        User user = UserMapper.toUser(userAuthDto, passwordEncoder);
        user.setRoles(List.of(role));
        System.out.println("user.getAuthorities():" + user.getAuthorities());
        return UserMapper.toUserDto(userRepository.save(user));
    }

    public User authenticate(UserAuthDto userAuthDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthDto.getName(),
                userAuthDto.getPassword()));

        return userRepository.findByName(userAuthDto.getName())
                .orElseThrow();
    }
}

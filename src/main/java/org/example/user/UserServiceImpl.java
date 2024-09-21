package org.example.user;

import lombok.RequiredArgsConstructor;
import org.example.user.dto.UserDto;
import org.example.user.dto.UserFormDto;
import org.example.user.dto.UserMapper;
import org.example.user.model.Role;
import org.example.user.model.User;
import org.example.util.error.EntityNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetails;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
                .map(x -> UserMapper.toUserDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id=%d was not found", userId),
                        "The required object was not found."));
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public UserDto addUser(UserFormDto userFormDto) {
        Role role = roleRepository.findById(userFormDto.getRoleId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("Role with id=%d was not found", userFormDto.getRoleId()),
                        "The required object was not found."));
        User user = UserMapper.toUser(userFormDto, role, passwordEncoder);
        return UserMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id=%d was not found", userId),
                        "The required object was not found."));
        User updatedUser = UserMapper.toUser(userDto);
        UserMapper.updateUser(user, updatedUser);
        return UserMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id=%d was not found", userId),
                        "The required object was not found."));
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByName(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with username=%s was not found", username),
                        "The required object was not found."));
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                user.getAuthorities());
    }
}

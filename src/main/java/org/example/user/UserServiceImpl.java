package org.example.user;

import lombok.RequiredArgsConstructor;
import org.example.aspect.LogAfterThrow;
import org.example.aspect.LogTimeTracker;
import org.example.user.dto.*;
import org.example.user.model.Role;
import org.example.user.model.User;
import org.example.util.error.EntityNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @LogAfterThrow
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
                .map(x -> UserMapper.toUserDto(x))
                .collect(Collectors.toList());
    }

    @LogAfterThrow
    @LogTimeTracker
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Override
    public List<UserDto> getUsers(int from, int size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        List<UserDto> users = userRepository.findAll(page).stream()
                .map(x -> UserMapper.toUserDto(x))
                .collect(Collectors.toList());
        return users;
    }

    @LogAfterThrow
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Override
    public List<UserDto> searchForTheUsers(String name, int from, int size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        List<UserDto> users = userRepository.findByNameContainingIgnoreCase(name.toLowerCase(), page).stream()
                .map(x -> UserMapper.toUserDto(x))
                .collect(Collectors.toList());
        return users;
    }

    @LogAfterThrow
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Override
    @Cacheable(value = "user", key = "#userId")
    public UserFullDto getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id=%d was not found", userId),
                        "The required object was not found."));
        return UserMapper.toUserFullDto(user);
    }

    @LogAfterThrow
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Override
    public UserDto addUser(UserFormRoleDto userFormDto) {
        Role role = roleRepository.findById(userFormDto.getRoleId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("Role with id=%d was not found", userFormDto.getRoleId()),
                        "The required object was not found."));
        User user = UserMapper.toUser(userFormDto, role, passwordEncoder);
        return UserMapper.toUserDto(userRepository.save(user));
    }

    @LogAfterThrow
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Override
    @CacheEvict(value = "user", key = "#userId")
    public UserFullDto updateUser(Long userId, UserFullDto userFullDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id=%d was not found", userId),
                        "The required object was not found."));
        User updatedUser = UserMapper.toUser(userFullDto);
        UserMapper.updateUser(user, updatedUser);
        return UserMapper.toUserFullDto(userRepository.save(user));
    }

    @LogAfterThrow
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Override
    @CacheEvict(value = "user", key = "#userId")
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id=%d was not found", userId),
                        "The required object was not found."));
        userRepository.delete(user);
    }

    @LogAfterThrow
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Override
    @CacheEvict(value = "user", key = "#userId")
    public UserDto updateUserLock(Long userId, Boolean isNotLocked, String token) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id=%d was not found", userId),
                        "The required object was not found."));
        user.setIsNotLocked(isNotLocked);
        return UserMapper.toUserDto(userRepository.save(user));
    }

    @LogAfterThrow
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Long countUsers() {
        return userRepository.count();
    }

    @Override
    public List<RoleDto> getRoles() {
        return roleRepository.findAll().stream()
                .map(x -> RoleMapper.toRoleDto(x))
                .collect(Collectors.toList());
    }
}

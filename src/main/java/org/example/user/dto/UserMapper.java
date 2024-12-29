package org.example.user.dto;

import org.example.user.model.Role;
import org.example.user.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId() != null ? user.getId() : null)
                .name(user.getName() != null ? user.getName() : null)
                .build();
    }

    public static UserFullDto toUserFullDto(User user) {
        return UserFullDto.builder()
                .id(user.getId() != null ? user.getId() : null)
                .name(user.getName() != null ? user.getName() : null)
                .isNotLocked(user.getIsNotLocked() != null ? user.getIsNotLocked() : null)
                .build();
    }

    public static User toUser(UserDto userDto) {
        return User.builder()
                .name(userDto.getName() != null ? userDto.getName() : null)
                .build();
    }

    public static User toUser(UserFullDto userFullDto) {
        return User.builder()
                .name(userFullDto.getName() != null ? userFullDto.getName() : null)
                .isNotLocked(userFullDto.getIsNotLocked() != null ? userFullDto.getIsNotLocked() : Boolean.TRUE)
                .build();
    }

    public static void updateUser(User user, User updatedUser) {
        if (updatedUser.getName() != null) user.setName(updatedUser.getName());
    }

    public static User toUser(UserFormRoleDto userFormRoleDto, Role role, PasswordEncoder passwordEncoder) {
        return User.builder()
                .name(userFormRoleDto.getName() != null ? userFormRoleDto.getName() : null)
                .password(userFormRoleDto.getPassword() != null ? passwordEncoder.encode(userFormRoleDto.getPassword()) : null)
                .isNotLocked(userFormRoleDto.getIsNotLocked() != null ? Boolean.valueOf(userFormRoleDto.getIsNotLocked()) : Boolean.TRUE)
                .roles(List.of(role))
                .build();
    }

    public static User toUser(UserAuthDto userAuthDto, Role role, PasswordEncoder passwordEncoder) {
        return User.builder()
                .name(userAuthDto.getName() != null ? userAuthDto.getName() : null)
                .password(userAuthDto.getPassword() != null ? passwordEncoder.encode(userAuthDto.getPassword()) : null)
                .roles(List.of(role))
                .build();
    }

    public static User toUser(UserAuthDto userAuthDto, Role role) {
        return User.builder()
                .name(userAuthDto.getName() != null ? userAuthDto.getName() : null)
                .roles(List.of(role))
                .build();
    }

    public static User toUser(UserAuthDto userAuthDto, List<Role> roles) {
        return User.builder()
                .name(userAuthDto.getName() != null ? userAuthDto.getName() : null)
                .roles(roles)
                .build();
    }

    public static User toUser(UserAuthDto userAuthDto, PasswordEncoder passwordEncoder) {
        return User.builder()
                .name(userAuthDto.getName())
                .password(passwordEncoder.encode(userAuthDto.getPassword()))
                .isNotLocked(userAuthDto.getIsNotLocked() != null ? Boolean.valueOf(userAuthDto.getIsNotLocked()) : Boolean.TRUE)
                .build();
    }
}

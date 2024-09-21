package org.example.user.dto;

import org.example.user.model.Role;
import org.example.user.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .name(user.getName() != null ? user.getName() : null)
                .build();
    }

    public static User toUser(UserDto userDto) {
        return User.builder()
                .name(userDto.getName() != null ? userDto.getName() : null)
                .build();
    }
    
    public static void updateUser(User user, User updatedUser) {
        if (updatedUser.getName() != null) user.setName(updatedUser.getName());
    }

    public static User toUser(UserFormDto userFormDto, Role role, PasswordEncoder passwordEncoder) {
        return User.builder()
                .name(userFormDto.getName() != null ? userFormDto.getName() : null)
                .password(userFormDto.getPassword() != null ? passwordEncoder.encode(userFormDto.getPassword()) : null)
                .roles(List.of(role))
                .build();
    }
}

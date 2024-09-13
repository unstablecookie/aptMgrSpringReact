package org.example.user.dto;

import org.example.user.model.User;

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
}

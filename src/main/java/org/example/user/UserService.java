package org.example.user;

import org.example.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
    
    void addUser(UserDto userDto);
}

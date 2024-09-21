package org.example.user;

import org.example.user.dto.UserDto;
import org.example.user.dto.UserFormDto;
import org.example.user.model.User;
import org.example.util.error.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

public interface UserService {
    List<UserDto> getUsers();

    UserDto addUser(UserDto userDto);

    UserDto addUser(UserFormDto userFormDto);

    UserDto updateUser(Long userId, UserDto userDto);

    UserDto getUser(Long userId);

    void deleteUser(Long userId);

    UserDetails loadUserByUsername(String username);
}

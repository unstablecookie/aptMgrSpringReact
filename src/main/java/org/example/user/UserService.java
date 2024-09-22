package org.example.user;

import org.example.user.dto.RoleDto;
import org.example.user.dto.UserDto;
import org.example.user.dto.UserFormRoleDto;
import org.example.user.model.Role;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();

    UserDto addUser(UserFormRoleDto userFormDto);

    UserDto updateUser(Long userId, UserDto userDto);

    UserDto getUser(Long userId);

    void deleteUser(Long userId);
    
    List<RoleDto> getRoles();
}

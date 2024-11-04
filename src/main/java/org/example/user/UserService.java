package org.example.user;

import org.example.user.dto.RoleDto;
import org.example.user.dto.UserDto;
import org.example.user.dto.UserFormRoleDto;
import org.example.user.dto.UserFullDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();

    List<UserDto> getUsers(int from, int size);

    List<UserDto> searchForTheUsers(String name, int from, int size);

    UserDto addUser(UserFormRoleDto userFormDto);

    UserFullDto updateUser(Long userId, UserFullDto userFullDto);

    UserFullDto getUser(Long userId);

    void deleteUser(Long userId);

    List<RoleDto> getRoles();

    UserDto updateUserLock(Long userId, Boolean isNotLocked, String token);

    Long countUsers();
}

package org.example.user;

import org.example.user.dto.RoleDto;
import org.example.user.dto.UserDto;
import org.example.user.dto.UserFormRoleDto;
import org.example.util.error.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestUserServiceRoles {
    @Autowired
    private UserService userService;

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void getUsers_success() {
        //then
        assertThat(userService.getUsers())
                .isNotNull()
                .isInstanceOf(List.class);
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    public void getUsers_failed_noRolePermissions() {
        //then
        assertThrows(AuthorizationDeniedException.class, () -> userService.getUsers());
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void getUser_success() {
        //given
        Long userId = 1L;
        //then
        assertThat(userService.getUser(userId))
                .isNotNull()
                .isInstanceOf(UserDto.class);
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    public void getUser_failed_noRolePermissions() {
        //given
        Long userId = 1L;
        //then
        assertThrows(AuthorizationDeniedException.class, () -> userService.getUser(userId));
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void addUser_success() {
        //given
        Long userId = 1L;
        String userName = "userName";
        String password = "password";
        //when
        UserFormRoleDto userFormRoleDto = UserFormRoleDto.builder()
                .roleId(2L)
                        .name(userName)
                                .password(password)
                                        .build();
        //then
        assertThat(userService.addUser(userFormRoleDto))
                .isNotNull()
                .isInstanceOf(UserDto.class);
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    public void addUser_failed_noRolePermissions() {
        //given
        Long userId = 1L;
        String userName = "userName";
        String password = "password";
        //when
        UserFormRoleDto userFormRoleDto = UserFormRoleDto.builder()
                .roleId(2L)
                .name(userName)
                .password(password)
                .build();
        //then
        assertThrows(AuthorizationDeniedException.class, () -> userService.addUser(userFormRoleDto));
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void updateUser_success() {
        //given
        Long userId = 1L;
        String changedName = "changedName";
        //when
        UserDto updateUser = UserDto.builder()
                .name(changedName)
                .build();
        //then
        assertThat(userService.updateUser(userId, updateUser))
                .isNotNull()
                .isInstanceOf(UserDto.class);
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    public void updateUser_failed_noRolePermissions() {
        //given
        Long userId = 1L;
        String changedName = "changedName";
        //when
        UserDto updateUser = UserDto.builder()
                .name(changedName)
                .build();
        //then
        assertThrows(AuthorizationDeniedException.class, () -> userService.updateUser(userId, updateUser));
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void deleteUser_success() {
        //given
        Long userId = 2L;
        String userName = "userName";
        String password = "password";
        UserFormRoleDto userFormRoleDto = UserFormRoleDto.builder()
                .roleId(2L)
                .name(userName)
                .password(password)
                .build();
        //when
        userService.addUser(userFormRoleDto);
        userService.deleteUser(userId);
        //then
        assertThrows(EntityNotFoundException.class, () -> userService.getUser(userId));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    public void deleteUser_failed_noRolePermissions() {
        //given
        Long userId = 1L;
        //then
        assertThrows(AuthorizationDeniedException.class, () -> userService.deleteUser(userId));
    }

    @Test
    public void getRoles_success() {
        //when
        List<RoleDto> roles = userService.getRoles();
        //then
        assertThat(roles)
                .isNotNull()
                .isInstanceOf(List.class)
                .hasSize(2);
    }
}

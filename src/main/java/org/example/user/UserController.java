package org.example.user;

import lombok.RequiredArgsConstructor;
import org.example.user.dto.RoleDto;
import org.example.user.dto.UserDto;
import org.example.user.dto.UserFormRoleDto;
import org.example.user.dto.UserFullDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public List<UserDto> getUsers() {
        logger.info("get users");
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public UserFullDto getUser(@PathVariable Long userId) {
        logger.info(String.format("get user id=%d", userId));
        return userService.getUser(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody UserFormRoleDto userFormRoleDto) {
        logger.info(String.format("add user name=%s", userFormRoleDto.getName()));
        return userService.addUser(userFormRoleDto);
    }

    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserFullDto updateUser(@PathVariable Long userId, @RequestBody UserFullDto userFullDto) {
        logger.info(String.format("update user id=%d", userId));
        return userService.updateUser(userId, userFullDto);
    }

    @PatchMapping("/{userId}/lock/{isNotLocked}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUserLock(@RequestHeader("authorization") String token, 
                                  @PathVariable Long userId, @PathVariable String isNotLocked) {
        logger.info(String.format("update user lock id=%d", userId));
        return userService.updateUserLock(userId, Boolean.valueOf(isNotLocked), token);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        logger.info(String.format("delete user id=%d", userId));
        userService.deleteUser(userId);
    }

    @GetMapping("/roles")
    @ResponseStatus(HttpStatus.OK)
    public List<RoleDto> getRoles() {
        return userService.getRoles();
    }
}

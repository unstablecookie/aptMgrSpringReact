package org.example.user;

import lombok.RequiredArgsConstructor;
import org.example.aspect.*;
import org.example.user.dto.RoleDto;
import org.example.user.dto.UserDto;
import org.example.user.dto.UserFormRoleDto;
import org.example.user.dto.UserFullDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @LogBeforeExecution
    @LogAfterReturningList
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers(@RequestParam(required = false, defaultValue = "0") int from,
                                  @RequestParam(required = false, defaultValue = "10") int size) {
        return userService.getUsers(from, size);
    }

    @LogBeforeExecution
    @LogAfterReturningList
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> searchForTheUsers(@RequestParam String name,
                                           @RequestParam(required = false, defaultValue = "0") int from,
                                           @RequestParam(required = false, defaultValue = "10") int size) {
        return userService.searchForTheUsers(name, from, size);
    }

    @LogBeforeExecution
    @LogAfterReturningSingleObject
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserFullDto getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @LogBeforeExecutionNoPasswd
    @LogAfterReturningSingleObject
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody UserFormRoleDto userFormRoleDto) {
        return userService.addUser(userFormRoleDto);
    }

    @LogBeforeExecution
    @LogAfterReturningSingleObject
    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserFullDto updateUser(@PathVariable Long userId, @RequestBody UserFullDto userFullDto) {
        return userService.updateUser(userId, userFullDto);
    }

    @LogBeforeExecution
    @LogAfterReturningSingleObject
    @PatchMapping("/{userId}/lock/{isNotLocked}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUserLock(@RequestHeader("authorization") String token,
                                  @PathVariable Long userId, @PathVariable String isNotLocked) {
        return userService.updateUserLock(userId, Boolean.valueOf(isNotLocked), token);
    }

    @LogBeforeExecution
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @LogBeforeExecutionNoArgs
    @LogAfterReturningList
    @GetMapping("/roles")
    @ResponseStatus(HttpStatus.OK)
    public List<RoleDto> getRoles() {
        return userService.getRoles();
    }

    @LogBeforeExecutionNoArgs
    @LogAfterReturningSingleObject
    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public Long countUsers() {
        return userService.countUsers();
    }
}

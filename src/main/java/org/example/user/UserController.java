package org.example.user;

import lombok.RequiredArgsConstructor;
import org.example.user.dto.UserDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    public String test() {
        return "hello from controller";
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public void addUser(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
    }
}

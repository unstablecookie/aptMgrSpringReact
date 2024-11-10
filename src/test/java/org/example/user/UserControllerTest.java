package org.example.user;

import lombok.RequiredArgsConstructor;
import org.example.security.JwtService;
import org.example.user.dto.UserDto;
import org.example.user.dto.UserFullDto;
import org.example.user.dto.UserMapper;
import org.example.user.model.Role;
import org.example.user.model.RoleEnum;
import org.example.user.model.User;
import org.example.util.error.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = UserController.class)
@MockBean(SecurityFilterChain.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserControllerTest {
    @MockBean
    private UserService userService;

    @MockBean
    private final JwtService jwtService;

    @Autowired
    private MockMvc mvc;
    private String role = "ADMIN";
    private User user;
    private int from = 0;
    private int size = 5;

    @BeforeEach
    public void init() {
        user = User.builder()
                .id(1L)
                .name("name")
                .password("password")
                .roles(List.of(Role.builder().name(RoleEnum.ADMIN).build()))
                .build();
    }

    @Test
    public void getUsers_success() throws Exception {
        //given
        List<UserDto> users = List.of(UserMapper.toUserDto(user));
        //when
        when(userService.getUsers(from, size)).thenReturn(users);
        //then
        mvc.perform(get("/users?from=0&size=5")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is(user.getName())));
    }

    @Test
    public void getUsers_success_noUsers() throws Exception {
        //given
        List<UserDto> users = List.of();
        //when
        when(userService.getUsers()).thenReturn(users);
        //then
        mvc.perform(get("/users")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getUser_success() throws Exception {
        //given
        Long userId = 1L;
        UserFullDto userFullDto = UserMapper.toUserFullDto(user);
        userFullDto.setIsNotLocked(Boolean.TRUE);
        //when
        when(userService.getUser(userId)).thenReturn(userFullDto);
        //then
        mvc.perform(get("/users/" + userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.isNotLocked", is(true)));
    }

    @Test
    public void getUser_failure_nonExisting() throws Exception {
        //given
        Long userId = -1L;
        UserFullDto userFullDto = UserMapper.toUserFullDto(user);
        userFullDto.setIsNotLocked(Boolean.TRUE);
        //when
        when(userService.getUser(userId)).thenThrow(new EntityNotFoundException(String.format("User with id=%d was not found", userId),
                "The required object was not found."));
        //then
        mvc.perform(get("/users/" + userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.reason", is("The required object was not found.")))
                .andExpect(jsonPath("$.message", is(String.format("User with id=%d was not found", userId))))
                .andExpect(jsonPath("$.timestamp", startsWith(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString())));
    }

    @Test
    public void searchForTheUsers_success() throws Exception {
        //given
        String userName = "name";
        //when
        when(userService.searchForTheUsers(userName, from, size)).thenReturn(List.of(UserMapper.toUserDto(user)));
        //then
        mvc.perform(get("/users/search?name=" + userName + "&from=0&size=5")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is(user.getName())));
    }
}

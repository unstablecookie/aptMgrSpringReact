package org.example.user;

import lombok.RequiredArgsConstructor;
import org.example.security.JwtService;
import org.example.user.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = UserController.class)
@MockBean(SecurityFilterChain.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestUserController {
    @MockBean
    private UserService userService;

    @MockBean
    private final JwtService jwtService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void getUsers_success() throws Exception {
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
}

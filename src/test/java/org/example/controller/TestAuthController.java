package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.security.AuthenticationService;
import org.example.security.JwtService;
import org.example.user.dto.UserAuthDto;
import org.example.user.dto.UserDto;
import org.example.user.model.Role;
import org.example.user.model.RoleEnum;
import org.example.user.model.User;
import org.example.util.auth.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = AuthController.class)
@MockBean(SecurityFilterChain.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestAuthController {
    @MockBean
    private final JwtService jwtService;

    @MockBean
    private final AuthenticationService authenticationService;

    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testIdTest() {
        assertThat(1L).isEqualTo(1L);
    }
    private UserAuthDto userAuthDto;
    private UserDto userDto;
    private User user;

    @BeforeEach
    public void prep() {
        userAuthDto = UserAuthDto.builder()
                .name("user")
                .password("password")
                .build();
        userDto = UserDto.builder()
                .name("user")
                .build();
        user = User.builder()
                .name("user")
                .password("password")
                .roles(List.of(Role.builder().name(RoleEnum.USER).build()))
                .build();
    }

    @Test
    public void signup_success() throws Exception {
        //given
        when(authenticationService.signup(ArgumentMatchers.any(UserAuthDto.class))).thenReturn(userDto);
        //then
        mvc.perform(post("/auth/signup")
                .with(csrf())
                .content(mapper.writeValueAsString(userAuthDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(userDto.getName())));
    }

    @Test
    public void signin_success() throws Exception {
        //given
        String jwtToken = "secret";
        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(36000L)
                .roles(List.of("USER"))
                .build();
        //when
        when(jwtService.generateToken(any())).thenReturn(jwtToken);
        when(authenticationService.authenticate(ArgumentMatchers.any(UserAuthDto.class))).thenReturn(loginResponse);
        //then
        mvc.perform(post("/auth/signin")
                        .with(csrf())
                        .content(mapper.writeValueAsString(userAuthDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is(loginResponse.getToken())))
                .andExpect(jsonPath("$.expiresIn", is(36000)))
                .andExpect(jsonPath("$.roles", is(loginResponse.getRoles())));
    }
}

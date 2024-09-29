package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.SecurityConfig.AuthenticationService;
import org.example.SecurityConfig.JwtAuthenticationFilter;
import org.example.SecurityConfig.JwtService;
import org.example.SecurityConfig.SecurityConfiguration;
import org.example.user.dto.UserAuthDto;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;

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
    
    @Test
    public void signup_success() throws Exception {
        //given
        
        UserAuthDto userAuthDto = UserAuthDto.builder()
                .name("user")
                .password("password")
                .build();
        UserDto userDto = UserDto.builder()
                        .name("user")
                        .build();
        when(authenticationService.signup(any())).thenReturn(userDto);
        //when
        mvc.perform(post("/auth/signup")
                .with(csrf())
                .content(mapper.writeValueAsString(userAuthDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

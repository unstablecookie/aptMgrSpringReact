package org.example.user;

import lombok.RequiredArgsConstructor;
import org.example.SecurityConfig.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = UserController.class)
@RequiredArgsConstructor
public class TestUserController {
    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mvc;

//    @Test
//    public void getUsers_success() {
//        mvc.
//    }


    @Test
    public void testIdTest() {
        assertThat(1L).isEqualTo(1L);
    }
}

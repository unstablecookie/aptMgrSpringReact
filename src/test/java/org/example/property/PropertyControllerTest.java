package org.example.property;

import lombok.RequiredArgsConstructor;
import org.example.property.dto.PropertyImageDto;
import org.example.security.JwtService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = PropertyController.class)
@MockBean(SecurityFilterChain.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PropertyControllerTest {

    @MockBean
    private PropertyService propertyService;

    @MockBean
    private final JwtService jwtService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void getProperties_success() throws Exception {
        //given
        List<PropertyImageDto> properties = List.of();
        int from = 0;
        int size = 5;
        //when
        when(propertyService.getPropertiesWithImages(from, size)).thenReturn(properties);
        //then
        mvc.perform(get("/properties")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

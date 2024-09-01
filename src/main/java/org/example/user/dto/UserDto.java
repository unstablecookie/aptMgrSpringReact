package org.example.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
public class UserDto {
    @NotBlank
    String name;
}

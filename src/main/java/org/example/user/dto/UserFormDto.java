package org.example.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.user.model.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFormDto {
    private String name;
    private String password;
    private Long roleId;
}

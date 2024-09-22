package org.example.user.dto;

import org.example.user.model.Role;

public class RoleMapper {
    public static RoleDto toRoleDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName().toString())
                .build();
    }
}

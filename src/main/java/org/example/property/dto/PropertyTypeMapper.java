package org.example.property.dto;

import org.example.property.model.PropertyType;

public class PropertyTypeMapper {
    public static PropertyTypeDto toPropertyTypeDto(PropertyType propertyType) {
        return PropertyTypeDto.builder()
                .id(propertyType.getId() != null ? propertyType.getId() : null)
                .type(propertyType.getType() != null ? propertyType.getType() : null)
                .build();
    }
}

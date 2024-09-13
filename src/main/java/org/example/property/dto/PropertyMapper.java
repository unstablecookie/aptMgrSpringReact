package org.example.property.dto;

import org.example.property.model.Property;

public class PropertyMapper {

    public static PropertyDto toPropertyDto(Property property) {
        return PropertyDto.builder()
                .title(property.getTitle() != null ? property.getTitle() : null)
                .build();
    }

    public static Property toProperty(PropertyDto propertyDto) {
        return Property.builder()
                .title(propertyDto.getTitle() != null ? propertyDto.getTitle() : null)
                .build();
    }

    public static void updateProperty(Property property, Property updatedProperty) {
        if (updatedProperty.getTitle() != null) property.setTitle(updatedProperty.getTitle());
    }
}

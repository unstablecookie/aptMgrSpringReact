package org.example.property.dto;

import org.example.property.model.Property;
import org.example.user.model.User;

public class PropertyMapper {

    public static PropertyDto toPropertyDto(Property property) {
        return PropertyDto.builder()
                .id(property.getId() != null ? property.getId() : null)
                .title(property.getTitle() != null ? property.getTitle() : null)
                .propertyTypeId(property.getPropertyTypeId() != null ? property.getPropertyTypeId() : null)
                .ownerId(property.getUser().getId() != null ? property.getUser().getId() : null)
                .build();
    }

    public static PropertyImageDto toPropertyImageDto(Property property) {
        return PropertyImageDto.builder()
                .id(property.getId() != null ? property.getId() : null)
                .title(property.getTitle() != null ? property.getTitle() : null)
                .propertyTypeId(property.getPropertyTypeId() != null ? property.getPropertyTypeId() : null)
                .ownerId(property.getUser().getId() != null ? property.getUser().getId() : null)
                .build();
    }

    public static Property toProperty(PropertyDto propertyDto) {
        return Property.builder()
                .title(propertyDto.getTitle() != null ? propertyDto.getTitle() : null)
                .propertyTypeId(propertyDto.getPropertyTypeId() != null ? propertyDto.getPropertyTypeId() : null)
                .build();
    }

    public static Property toPropertyWithUser(PropertyDto propertyDto, User user) {
        return Property.builder()
                .title(propertyDto.getTitle() != null ? propertyDto.getTitle() : null)
                .propertyTypeId(propertyDto.getPropertyTypeId() != null ? propertyDto.getPropertyTypeId() : null)
                .user(user)
                .build();
    }

    public static void updateProperty(Property property, Property updatedProperty) {
        if (updatedProperty.getTitle() != null) property.setTitle(updatedProperty.getTitle());
        if (updatedProperty.getPropertyTypeId() != null) property.setPropertyTypeId(updatedProperty.getPropertyTypeId());
    }
}

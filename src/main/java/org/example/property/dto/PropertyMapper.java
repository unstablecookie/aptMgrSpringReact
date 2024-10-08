package org.example.property.dto;

import org.example.property.model.Property;
import org.example.property.model.PropertyType;
import org.example.user.model.User;

public class PropertyMapper {

    public static PropertyDto toPropertyDto(Property property) {
        return PropertyDto.builder()
                .id(property.getId() != null ? property.getId() : null)
                .title(property.getTitle() != null ? property.getTitle() : null)
                .propertyType(property.getPropertyType() != null ? property.getPropertyType().getType() : null)
                .ownerId(property.getUser().getId() != null ? property.getUser().getId() : null)
                .build();
    }

    public static PropertyImageDto toPropertyImageDto(Property property) {
        return PropertyImageDto.builder()
                .id(property.getId() != null ? property.getId() : null)
                .title(property.getTitle() != null ? property.getTitle() : null)
                .propertyType(property.getPropertyType() != null ? property.getPropertyType().getType() : null)
                .ownerId(property.getUser().getId() != null ? property.getUser().getId() : null)
                .build();
    }

    public static Property toProperty(PropertySaveDto propertySaveDto, PropertyType propertyType) {
        return Property.builder()
                .title(propertySaveDto.getTitle() != null ? propertySaveDto.getTitle() : null)
                .propertyType(propertyType != null ? propertyType : null)
                .build();
    }

    public static Property toPropertyWithUser(PropertySaveDto propertySaveDto, PropertyType propertyType, User user) {
        return Property.builder()
                .title(propertySaveDto.getTitle() != null ? propertySaveDto.getTitle() : null)
                .propertyType(propertyType != null ? propertyType : null)
                .user(user)
                .build();
    }

    public static void updateProperty(Property property, PropertyType propertyType, Property updatedProperty) {
        if (updatedProperty.getTitle() != null) property.setTitle(updatedProperty.getTitle());
        if (propertyType != null) property.setPropertyType(propertyType);
    }
}

package org.example.property.dto;

import org.example.property.model.Property;
import org.example.property.model.PropertyType;
import org.example.user.model.User;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PropertyMapper {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
                .buildIn(property.getBuildIn() != null ? property.getBuildIn() : null)
                .floorsNumb(property.getFloorsNumb() != null ? property.getFloorsNumb() : null)
                .sqrMeters(property.getSqrMeters() != null ? property.getSqrMeters() : null)
                .lastPayment(property.getLastPayment() != null ? property.getLastPayment().format(formatter) : null)
                .monthlyPaid(property.getMonthlyPaid() != null ? property.getMonthlyPaid() : null)
                .build();
    }

    public static Property toProperty(PropertySaveDto propertySaveDto, PropertyType propertyType) {
        return Property.builder()
                .title(propertySaveDto.getTitle() != null ? propertySaveDto.getTitle() : null)
                .propertyType(propertyType != null ? propertyType : null)
                .buildIn(propertySaveDto.getBuildIn() != null ? propertySaveDto.getBuildIn() : null)
                .floorsNumb(propertySaveDto.getFloorsNumb() != null ? propertySaveDto.getFloorsNumb() : null)
                .sqrMeters(propertySaveDto.getSqrMeters() != null ? propertySaveDto.getSqrMeters() : null)
                .lastPayment(propertySaveDto.getLastPayment() != null ? LocalDateTime.parse(propertySaveDto.getLastPayment(), formatter) : null)
                .monthlyPaid(propertySaveDto.getMonthlyPaid() != null ? propertySaveDto.getMonthlyPaid() : null)
                .build();
    }

    public static Property toPropertyWithUser(PropertySaveDto propertySaveDto, PropertyType propertyType, User user) {
        return Property.builder()
                .title(propertySaveDto.getTitle() != null ? propertySaveDto.getTitle() : null)
                .propertyType(propertyType != null ? propertyType : null)
                .user(user)
                .buildIn(propertySaveDto.getBuildIn() != null ? propertySaveDto.getBuildIn() : null)
                .floorsNumb(propertySaveDto.getFloorsNumb() != null ? propertySaveDto.getFloorsNumb() : null)
                .sqrMeters(propertySaveDto.getSqrMeters() != null ? propertySaveDto.getSqrMeters() : null)
                .lastPayment(propertySaveDto.getLastPayment() != null ? LocalDateTime.parse(propertySaveDto.getLastPayment(), formatter) : null)
                .monthlyPaid(propertySaveDto.getMonthlyPaid() != null ? propertySaveDto.getMonthlyPaid() : null)
                .build();
    }

    public static void updateProperty(Property property, PropertyType propertyType, Property updatedProperty) {
        if (updatedProperty.getTitle() != null) property.setTitle(updatedProperty.getTitle());
        if (propertyType != null) property.setPropertyType(propertyType);
    }

    public static Property updatePropertyWithPayment(Property property, PropertyPaidUpdateDto propertyPaidUpdateDto) {
        if (propertyPaidUpdateDto.getLastPayment() != null) {
            property.setLastPayment(LocalDateTime.parse(propertyPaidUpdateDto.getLastPayment(), formatter));
        }
        if (propertyPaidUpdateDto.getMonthlyPaid() != null) {
            property.setMonthlyPaid(propertyPaidUpdateDto.getMonthlyPaid());
        }
        return property;
    }
}

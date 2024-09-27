package org.example.property;

import org.example.property.dto.PropertyDto;
import org.example.property.dto.PropertyTypeDto;
import org.example.property.model.PropertyType;

import java.util.List;

public interface PropertyService {
    List<PropertyDto> getProperties();

    PropertyDto addProperty(PropertyDto propertyDto);

    PropertyDto updateProperty(Long propertyId, PropertyDto propertyDto);

    PropertyDto getProperty(Long propertyId);

    void deleteProperty(Long propertyId);

    List<PropertyTypeDto> getPropertyTypes();

    List<PropertyDto> getOwnerProperties(String token);

    PropertyDto addPropertyByOwner(PropertyDto propertyDto, String token);
}

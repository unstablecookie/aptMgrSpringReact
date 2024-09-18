package org.example.property;

import org.example.property.dto.PropertyDto;

import java.util.List;

public interface PropertyService {
    List<PropertyDto> getProperties();

    PropertyDto addProperty(PropertyDto propertyDto);

    PropertyDto updateProperty(Long propertyId, PropertyDto propertyDto);

    PropertyDto getProperty(Long propertyId);

    void deleteProperty(Long propertyId);
}

package org.example.property;

import org.example.property.dto.*;
import org.example.user.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PropertyService {
    List<PropertyDto> getProperties();

    PropertyDto addProperty(PropertySaveDto propertySaveDto);

    PropertyDto updateProperty(Long propertyId, PropertySaveDto propertySaveDto, String token);

    PropertyImageDto getProperty(Long propertyId, String token);

    PropertyImageDto getPropertyByOwner(Long propertyId, String token);

    void deleteProperty(Long propertyId, String token);

    List<PropertyTypeDto> getPropertyTypes();

    List<PropertyDto> getOwnerProperties(String token);

    PropertyDto addPropertyByOwner(PropertySaveDto propertyDto, String token);

    void addPropertyImage(MultipartFile multipartFile, Long propertyId, String token);

    List<PropertyImageDto> getPropertiesWithImages(int from, int size);

    List<PropertyImageDto> getOwnerPropertiesWithImages(String token, int from, int size);

    PropertyDto updatePropertyPaidTime(Long propertyId, PropertyPaidUpdateDto propertyPaidUpdateDto, String token);

    Long countProperty();

    Long countOwnerProperty(String token);
}

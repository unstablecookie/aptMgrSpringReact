package org.example.property;

import lombok.RequiredArgsConstructor;
import org.example.property.model.PropertyType;
import org.example.security.JwtService;
import org.example.property.dto.*;
import org.example.property.model.Property;
import org.example.property.model.PropertyImage;
import org.example.user.UserRepository;
import org.example.user.model.User;
import org.example.util.error.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final PropertyTypeRepository propertyTypeRepository;

    private final PropertyImageRepository propertyImageRepository;
    private final JwtService jwtService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Override
    public List<PropertyDto> getProperties() {
        return propertyRepository.findAll().stream()
                .map(x -> PropertyMapper.toPropertyDto(x))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Override
    public PropertyImageDto getProperty(Long propertyId, String token) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Property with id=%d was not found", propertyId),
                        "The required object was not found."));
        PropertyImageDto propertyImageDto = PropertyMapper.toPropertyImageDto(property);
        updatePropertyDtoWithImages(List.of(propertyImageDto));
        return propertyImageDto;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Override
    public PropertyImageDto getPropertyByOwner(Long propertyId, String token) {
        User user = getUserFromToken(token);
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Property with id=%d was not found", propertyId),
                        "The required object was not found."));
        if (!user.getId().equals(property.getUser().getId())) {
            throw new EntityNotFoundException(String.format("Property with id=%d was not found", propertyId),
                    "The required object was not found.");
        }
        PropertyImageDto propertyImageDto = PropertyMapper.toPropertyImageDto(property);
        updatePropertyDtoWithImages(List.of(propertyImageDto));
        return propertyImageDto;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Override
    public PropertyDto addProperty(PropertySaveDto propertySaveDto) {
        PropertyType propertyType = propertyTypeRepository.findById(propertySaveDto.getPropertyTypeId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("Type with id=%d was not found", propertySaveDto.getPropertyTypeId()),
                        "The required object was not found."));
        Property property = PropertyMapper.toProperty(propertySaveDto, propertyType);
        return PropertyMapper.toPropertyDto(propertyRepository.save(property));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Override
    public PropertyDto addPropertyByOwner(PropertySaveDto propertySaveDto, String token) {
        User user = getUserFromToken(token);
        PropertyType propertyType = propertyTypeRepository.findById(propertySaveDto.getPropertyTypeId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("Type with id=%d was not found", propertySaveDto.getPropertyTypeId()),
                        "The required object was not found."));
        Property property = PropertyMapper.toPropertyWithUser(propertySaveDto, propertyType, user);
        return PropertyMapper.toPropertyDto(propertyRepository.save(property));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Override
    public PropertyDto updateProperty(Long propertyId, PropertySaveDto propertySaveDto) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Property with id=%d was not found", propertyId),
                        "The required object was not found."));
        PropertyType propertyType = propertyTypeRepository.findById(propertySaveDto.getPropertyTypeId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("Type with id=%d was not found", propertySaveDto.getPropertyTypeId()),
                        "The required object was not found."));
        Property updatedProperty = PropertyMapper.toProperty(propertySaveDto, propertyType);
        PropertyMapper.updateProperty(property, propertyType, updatedProperty);
        return PropertyMapper.toPropertyDto(propertyRepository.save(property));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Override
    public void deleteProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Property with id=%d was not found", propertyId),
                        "The required object was not found."));
        propertyRepository.delete(property);
    }

    @Override
    public List<PropertyTypeDto> getPropertyTypes() {
        return propertyTypeRepository.findAll().stream()
                .map(x -> PropertyTypeMapper.toPropertyTypeDto(x))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Override
    public List<PropertyDto> getOwnerProperties(String token) {
        User user = getUserFromToken(token);
        return propertyRepository.findByUserId(user.getId()).stream()
                .map(x -> PropertyMapper.toPropertyDto(x))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Override
    public List<PropertyImageDto> getOwnerPropertiesWithImages(String token) {
        User user = getUserFromToken(token);
        List<PropertyImageDto> properties = propertyRepository.findByUserId(user.getId()).stream()
                .map(x -> PropertyMapper.toPropertyImageDto(x))
                .collect(Collectors.toList());
        updatePropertyDtoWithImages(properties);
        return properties;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Override
    public void addPropertyImage(MultipartFile multipartFile, Long propertyId, String token) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Property with id=%d was not found", propertyId),
                        "The required object was not found."));
        try {
            PropertyImage image = PropertyImage.builder()
                    .data(multipartFile.getBytes())
                    .property(property)
                    .build();
            propertyImageRepository.save(image);
        } catch (IOException e) {
            e.printStackTrace();//TODO
        }
    }

    private User getUserFromToken(String token) {
        String jwt = token.substring(7);
        String userName = jwtService.extractUsername(jwt);
        return userRepository.findByName(userName).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with name=%s was not found", userName),
                        "The required object was not found."));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Override
    public List<PropertyImageDto> getPropertiesWithImages() {
        List<PropertyImageDto> properties = propertyRepository.findAll().stream()
                .map(x -> PropertyMapper.toPropertyImageDto(x))
                .collect(Collectors.toList());
        updatePropertyDtoWithImages(properties);
        return properties;
    }

    private void updatePropertyDtoWithImages(List<PropertyImageDto> properties) {
        properties.stream().forEach(x -> {
            PropertyImage propertyImage = propertyImageRepository.findByPropertyId(x.getId());
            if (propertyImage != null) {
                x.setData(propertyImage.getData());
            }
        });
    }
}

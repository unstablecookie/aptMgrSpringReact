package org.example.property;

import lombok.RequiredArgsConstructor;
import org.example.property.dto.PropertyDto;
import org.example.property.dto.PropertyMapper;
import org.example.property.model.Property;
import org.example.util.error.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;

    @Override
    public List<PropertyDto> getProperties() {
        return propertyRepository.findAll().stream()
                .map(x -> PropertyMapper.toPropertyDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public PropertyDto getProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Property with id=%d was not found", propertyId),
                        "The required object was not found."));
        return PropertyMapper.toPropertyDto(property);
    }

    @Override
    public PropertyDto addProperty(PropertyDto propertyDto) {
        Property property = PropertyMapper.toProperty(propertyDto);
        return PropertyMapper.toPropertyDto(propertyRepository.save(property));
    }

    @Override
    public PropertyDto updateProperty(Long propertyId, PropertyDto propertyDto) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Property with id=%d was not found", propertyId),
                        "The required object was not found."));
        Property updatedProperty = PropertyMapper.toProperty(propertyDto);
        PropertyMapper.updateProperty(property, updatedProperty);
        return PropertyMapper.toPropertyDto(propertyRepository.save(property));
    }

    @Override
    public void deleteProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Property with id=%d was not found", propertyId),
                        "The required object was not found."));
        propertyRepository.delete(property);
    }
}

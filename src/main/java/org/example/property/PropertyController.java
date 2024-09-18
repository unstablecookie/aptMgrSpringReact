package org.example.property;

import lombok.RequiredArgsConstructor;
import org.example.property.dto.PropertyDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @GetMapping
    public List<PropertyDto> getProperties() {
        return propertyService.getProperties();
    }

    @GetMapping("/{propertyId}")
    public PropertyDto getProperty(@PathVariable Long propertyId) {
        return propertyService.getProperty(propertyId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyDto addProperty(@RequestBody PropertyDto propertyDto) {
        return propertyService.addProperty(propertyDto);
    }

    @PatchMapping("/{propertyId}")
    @ResponseStatus(HttpStatus.OK)
    public PropertyDto updateProperty(@PathVariable Long propertyId, @RequestBody PropertyDto propertyDto) {
        return propertyService.updateProperty(propertyId, propertyDto);
    }

    @DeleteMapping("/{propertyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProperty(@PathVariable Long propertyId) {
        propertyService.deleteProperty(propertyId);
    }
}

package org.example.property;

import lombok.RequiredArgsConstructor;
import org.example.property.dto.PropertyDto;
import org.example.property.dto.PropertyTypeDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;
    Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PropertyDto> getProperties() {
        logger.info("get properties");
        return propertyService.getProperties();
    }

    @GetMapping("/{propertyId}")
    @ResponseStatus(HttpStatus.OK)
    public PropertyDto getProperty(@PathVariable Long propertyId) {
        logger.info("get properties");
        return propertyService.getProperty(propertyId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyDto addProperty(@RequestBody PropertyDto propertyDto) {
        logger.info(String.format("add property with title %s", propertyDto.getTitle()));
        return propertyService.addProperty(propertyDto);
    }

    @PostMapping("/owner")
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyDto addPropertyByOwner(@RequestBody PropertyDto propertyDto,
                                          @RequestHeader("authorization") String token) {
        logger.info(String.format("add property by owner with title %s", propertyDto.getTitle()));
        return propertyService.addPropertyByOwner(propertyDto, token);
    }

    @PatchMapping("/{propertyId}")
    @ResponseStatus(HttpStatus.OK)
    public PropertyDto updateProperty(@PathVariable Long propertyId, @RequestBody PropertyDto propertyDto) {
        logger.info(String.format("update property with id=%d", propertyId));
        return propertyService.updateProperty(propertyId, propertyDto);
    }

    @DeleteMapping("/{propertyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProperty(@PathVariable Long propertyId) {
        logger.info(String.format("delete property with id=%d", propertyId));
        propertyService.deleteProperty(propertyId);
    }

    @GetMapping(value = "/types")
    @ResponseStatus(HttpStatus.OK)
    public List<PropertyTypeDto> getTypes() {
        return propertyService.getPropertyTypes();
    }

    @GetMapping(value = "/owner")
    @ResponseStatus(HttpStatus.OK)
    public List<PropertyDto> getOwnerProperties(@RequestHeader("authorization") String token) {
        logger.info("get properties by owner");
        return propertyService.getOwnerProperties(token);
    }
}

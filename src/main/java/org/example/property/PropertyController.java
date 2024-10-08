package org.example.property;

import lombok.RequiredArgsConstructor;
import org.example.property.dto.PropertyDto;
import org.example.property.dto.PropertyImageDto;
import org.example.property.dto.PropertySaveDto;
import org.example.property.dto.PropertyTypeDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;
    Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PropertyImageDto> getProperties() {
        logger.info("get properties");
        return propertyService.getPropertiesWithImages();
    }

    @GetMapping("/{propertyId}")
    @ResponseStatus(HttpStatus.OK)
    public PropertyImageDto getProperty(@PathVariable Long propertyId,
                                        @RequestHeader("authorization") String token) {
        logger.info("get property");
        return propertyService.getProperty(propertyId, token);
    }

    @GetMapping("/owner/{propertyId}")
    @ResponseStatus(HttpStatus.OK)
    public PropertyImageDto getPropertyByOwner(@PathVariable Long propertyId,
                                        @RequestHeader("authorization") String token) {
        logger.info("get property");
        return propertyService.getPropertyByOwner(propertyId, token);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyDto addProperty(@RequestBody PropertySaveDto propertySaveDto) {
        logger.info(String.format("add property with title %s", propertySaveDto.getTitle()));
        return propertyService.addProperty(propertySaveDto);
    }

    @PostMapping("/owner")
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyDto addPropertyByOwner(@RequestBody PropertySaveDto propertySaveDto,
                                          @RequestHeader("authorization") String token) {
        logger.info(String.format("add property by owner with title %s", propertySaveDto.getTitle()));
        return propertyService.addPropertyByOwner(propertySaveDto, token);
    }

    @PatchMapping("/{propertyId}")
    @ResponseStatus(HttpStatus.OK)
    public PropertyDto updateProperty(@PathVariable Long propertyId, @RequestBody PropertySaveDto propertySaveDto) {
        logger.info(String.format("update property with id=%d", propertyId));
        return propertyService.updateProperty(propertyId, propertySaveDto);
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
    public List<PropertyImageDto> getOwnerProperties(@RequestHeader("authorization") String token) {
        logger.info("get properties by owner");
        return propertyService.getOwnerPropertiesWithImages(token);
    }

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPropertyImage(@RequestParam("imageFile") MultipartFile multipartFile,
                                 @RequestHeader("X-property-id") Long propertyId,
                                          @RequestHeader("authorization") String token) {
        logger.info(String.format("add property image"));
        propertyService.addPropertyImage(multipartFile, propertyId, token);
    }
}

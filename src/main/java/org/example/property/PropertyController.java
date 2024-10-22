package org.example.property;

import lombok.RequiredArgsConstructor;
import org.example.property.dto.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    public List<PropertyImageDto> getProperties(@RequestParam(required = false, defaultValue = "0") int from,
                                                @RequestParam(required = false, defaultValue = "10") int size) {
        logger.info("get properties from " + from + " by size " + size);
        return propertyService.getPropertiesWithImages(from, size);
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
    public PropertyDto updateProperty(@PathVariable Long propertyId, @RequestBody PropertySaveDto propertySaveDto,
                                      @RequestHeader("authorization") String token) {
        logger.info(String.format("update property with id=%d", propertyId));
        return propertyService.updateProperty(propertyId, propertySaveDto, token);
    }

    @DeleteMapping("/{propertyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProperty(@PathVariable Long propertyId, @RequestHeader("authorization") String token) {
        logger.info(String.format("delete property with id=%d", propertyId));
        propertyService.deleteProperty(propertyId, token);
    }

    @GetMapping(value = "/types")
    @ResponseStatus(HttpStatus.OK)
    public List<PropertyTypeDto> getTypes() {
        return propertyService.getPropertyTypes();
    }

    @GetMapping(value = "/owner")
    @ResponseStatus(HttpStatus.OK)
    public List<PropertyImageDto> getOwnerProperties(@RequestHeader("authorization") String token,
                                                     @RequestParam(required = false, defaultValue = "0") int from,
                                                     @RequestParam(required = false, defaultValue = "10") int size) {
        logger.info("get properties by owner from " + from + " by size " + size);
        return propertyService.getOwnerPropertiesWithImages(token, from, size);
    }

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPropertyImage(@RequestParam("imageFile") MultipartFile multipartFile,
                                 @RequestHeader("X-property-id") Long propertyId,
                                          @RequestHeader("authorization") String token) {
        logger.info(String.format("add property image"));
        propertyService.addPropertyImage(multipartFile, propertyId, token);
    }

    @PatchMapping("/owner/{propertyId}/paid")
    @ResponseStatus(HttpStatus.OK)
    public PropertyDto updatePropertyPaidTime(@PathVariable Long propertyId,
                                              @RequestBody PropertyPaidUpdateDto propertyPaidUpdateDto,
                                              @RequestHeader("authorization") String token) {
        logger.info(String.format("update property id=%d payment status", propertyId));
        return propertyService.updatePropertyPaidTime(propertyId, propertyPaidUpdateDto, token);
    }

    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public Long countProperty() {
        logger.info("count properties");
        return propertyService.countProperty();
    }

    @GetMapping("/owner/count")
    @ResponseStatus(HttpStatus.OK)
    public Long countOwnerProperty(@RequestHeader("authorization") String token) {
        logger.info("count owner properties");
        return propertyService.countOwnerProperty(token);
    }
}

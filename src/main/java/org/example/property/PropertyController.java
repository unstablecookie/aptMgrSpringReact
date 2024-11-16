package org.example.property;

import lombok.RequiredArgsConstructor;
import org.example.aspect.*;
import org.example.property.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @LogBeforeExecution
    @LogAfterReturningList
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PropertyImageDto> getProperties(@RequestParam(required = false, defaultValue = "0") int from,
                                                @RequestParam(required = false, defaultValue = "10") int size) {
        return propertyService.getPropertiesWithImages(from, size);
    }

    @LogBeforeExecution
    @LogAfterReturningList
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<PropertyImageDto> searchForTheProperty(@RequestParam String title,
                                           @RequestParam(required = false, defaultValue = "0") int from,
                                           @RequestParam(required = false, defaultValue = "10") int size) {
        return propertyService.searchForTheProperty(title, from, size);
    }

    @LogBeforeExecutionNoToken
    @LogAfterReturningList
    @GetMapping("/search/owner")
    @ResponseStatus(HttpStatus.OK)
    public List<PropertyImageDto> searchForThePropertyByOwner(@RequestParam String title,
                                                       @RequestParam(required = false, defaultValue = "0") int from,
                                                       @RequestParam(required = false, defaultValue = "10") int size,
                                                       @RequestHeader("authorization") String token) {
        return propertyService.searchForThePropertyByOwner(title, from, size, token);
    }

    @LogBeforeExecutionNoToken
    @LogAfterReturningSingleObjectNoData
    @GetMapping("/{propertyId}")
    @ResponseStatus(HttpStatus.OK)
    public PropertyImageDto getProperty(@PathVariable Long propertyId,
                                        @RequestHeader("authorization") String token) {
        return propertyService.getProperty(propertyId, token);
    }

    @LogBeforeExecutionNoToken
    @LogAfterReturningSingleObjectNoData
    @GetMapping("/owner/{propertyId}")
    @ResponseStatus(HttpStatus.OK)
    public PropertyImageDto getPropertyByOwner(@PathVariable Long propertyId,
                                        @RequestHeader("authorization") String token) {
        return propertyService.getPropertyByOwner(propertyId, token);
    }

    @LogBeforeExecution
    @LogAfterReturningSingleObject
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyDto addProperty(@RequestBody PropertySaveDto propertySaveDto) {
        return propertyService.addProperty(propertySaveDto);
    }

    @LogBeforeExecutionNoToken
    @PostMapping("/owner")
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyDto addPropertyByOwner(@RequestBody PropertySaveDto propertySaveDto,
                                          @RequestHeader("authorization") String token) {
        return propertyService.addPropertyByOwner(propertySaveDto, token);
    }

    @LogBeforeExecutionNoToken
    @PatchMapping("/{propertyId}")
    @ResponseStatus(HttpStatus.OK)
    public PropertyDto updateProperty(@PathVariable Long propertyId, @RequestBody PropertySaveDto propertySaveDto,
                                      @RequestHeader("authorization") String token) {
        return propertyService.updateProperty(propertyId, propertySaveDto, token);
    }

    @LogBeforeExecutionNoToken
    @DeleteMapping("/{propertyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProperty(@PathVariable Long propertyId, @RequestHeader("authorization") String token) {
        propertyService.deleteProperty(propertyId, token);
    }

    @LogBeforeExecutionNoArgs
    @LogAfterReturningList
    @GetMapping(value = "/types")
    @ResponseStatus(HttpStatus.OK)
    public List<PropertyTypeDto> getTypes() {
        return propertyService.getPropertyTypes();
    }

    @LogBeforeExecutionNoToken
    @LogAfterReturningList
    @GetMapping(value = "/owner")
    @ResponseStatus(HttpStatus.OK)
    public List<PropertyImageDto> getOwnerProperties(@RequestHeader("authorization") String token,
                                                     @RequestParam(required = false, defaultValue = "0") int from,
                                                     @RequestParam(required = false, defaultValue = "10") int size) {
        return propertyService.getOwnerPropertiesWithImages(token, from, size);
    }

    @LogBeforeExecutionNoArgs
    @PostMapping("/image")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPropertyImage(@RequestParam("imageFile") MultipartFile multipartFile,
                                 @RequestHeader("X-property-id") Long propertyId,
                                          @RequestHeader("authorization") String token) {
        propertyService.addPropertyImage(multipartFile, propertyId, token);
    }

    @LogBeforeExecutionNoToken
    @LogAfterReturningSingleObject
    @PatchMapping("/owner/{propertyId}/paid")
    @ResponseStatus(HttpStatus.OK)
    public PropertyDto updatePropertyPaidTime(@PathVariable Long propertyId,
                                              @RequestBody PropertyPaidUpdateDto propertyPaidUpdateDto,
                                              @RequestHeader("authorization") String token) {
        return propertyService.updatePropertyPaidTime(propertyId, propertyPaidUpdateDto, token);
    }

    @LogBeforeExecutionNoArgs
    @LogAfterReturningSingleObject
    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public Long countProperty() {
        return propertyService.countProperty();
    }

    @LogBeforeExecutionNoArgs
    @LogAfterReturningSingleObject
    @GetMapping("/owner/count")
    @ResponseStatus(HttpStatus.OK)
    public Long countOwnerProperty(@RequestHeader("authorization") String token) {
        return propertyService.countOwnerProperty(token);
    }
}

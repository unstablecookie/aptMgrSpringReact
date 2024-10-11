package org.example.property.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertySaveDto {
    private Long id;
    private String title;
    private Long propertyTypeId;
    private Long ownerId;
    private Integer buildIn;
    private Integer floorsNumb;
    private Integer sqrMeters;
    private String lastPayment;
    private Boolean monthlyPaid;
}

package org.example.property.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyImageDto {
    private Long id;
    private String title;
    private String propertyType;
    private Long ownerId;
    private byte[] data;
    private Integer buildIn;
    private Integer floorsNumb;
    private Integer sqrMeters;
    private String lastPayment;
    private Boolean monthlyPaid;
}

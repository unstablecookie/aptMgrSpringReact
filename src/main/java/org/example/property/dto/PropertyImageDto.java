package org.example.property.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyImageDto implements Serializable {
    private Long id;
    private String title;
    private String propertyType;
    private String ownerUid;
    private byte[] data;
    private Integer buildIn;
    private Integer floorsNumb;
    private Integer sqrMeters;
    private String lastPayment;
    private Boolean monthlyPaid;
}

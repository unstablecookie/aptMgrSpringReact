package org.example.property.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyPaidUpdateDto {
    private String lastPayment;
    private Boolean monthlyPaid;
}

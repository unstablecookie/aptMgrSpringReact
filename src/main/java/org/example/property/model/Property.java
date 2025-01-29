package org.example.property.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.user.model.User;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_type_id")
    private PropertyType propertyType;

    @Column(name = "owner_uid")
    private String ownerUid;

    @Column(name = "build_in")
    private Integer buildIn;

    @Column(name = "floors_numb")
    private Integer floorsNumb;

    @Column(name = "sqr_meters")
    private Integer sqrMeters;

    @Column(name = "last_payment")
    private LocalDateTime lastPayment;

    @Column(name = "monthly_paid")
    private Boolean monthlyPaid;
}

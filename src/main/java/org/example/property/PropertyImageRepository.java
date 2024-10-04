package org.example.property;

import org.example.property.model.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyImageRepository extends JpaRepository<PropertyImage, Long> {
    PropertyImage findByPropertyId(Long id);
}

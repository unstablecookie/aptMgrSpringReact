package org.example.property;

import org.example.property.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
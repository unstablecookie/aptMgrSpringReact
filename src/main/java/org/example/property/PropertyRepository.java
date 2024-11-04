package org.example.property;

import org.example.property.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByUserId(Long userId);

    Long countByUserId(Long userId);

    Page<Property> findByUserId(Long userId, Pageable page);

    Page<Property> findByTitleContainingIgnoreCase(String title, Pageable page);

    Page<Property> findByUserIdAndTitleContainingIgnoreCase(Long userId, String title, Pageable page);
}
package org.example.property;

import org.example.property.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByOwnerUid(String ownerUid);

    Long countByOwnerUid(String ownerUid);

    Page<Property> findByOwnerUid(String ownerUid, Pageable page);

    Page<Property> findByTitleContainingIgnoreCase(String title, Pageable page);

    Page<Property> findByOwnerUidAndTitleContainingIgnoreCase(String ownerUid, String title, Pageable page);
}
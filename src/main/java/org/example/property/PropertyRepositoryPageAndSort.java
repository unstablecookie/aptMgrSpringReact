package org.example.property;

import org.example.property.model.Property;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PropertyRepositoryPageAndSort extends PagingAndSortingRepository<Property, Long> {
}

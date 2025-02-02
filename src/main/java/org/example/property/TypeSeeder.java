package org.example.property;

import lombok.AllArgsConstructor;
import org.example.property.model.PropertyType;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TypeSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final PropertyTypeRepository propertyTypeRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.seedTypes();
    }

    public void seedTypes() {
        List<PropertyType> types = propertyTypeRepository.findAll();
        if (types.size() < 2) {
            propertyTypeRepository.save(PropertyType.builder().type("HOUSE").build());
            propertyTypeRepository.save(PropertyType.builder().type("APARTMENT").build());
        }
    }
}

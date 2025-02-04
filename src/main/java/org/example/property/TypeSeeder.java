package org.example.property;

import lombok.AllArgsConstructor;
import org.example.property.model.PropertyType;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TypeSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final PropertyTypeRepository propertyTypeRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.seedTypes();
    }

    public void seedTypes() {
        propertyTypeRepository.save(PropertyType.builder().type("HOUSE").build());
        propertyTypeRepository.save(PropertyType.builder().type("APARTMENT").build());
    }
}

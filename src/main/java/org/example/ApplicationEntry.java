package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })
@EnableCaching
public class ApplicationEntry {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationEntry.class, args);
    }
}

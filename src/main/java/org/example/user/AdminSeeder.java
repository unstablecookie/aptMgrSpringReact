package org.example.user;

import lombok.AllArgsConstructor;
import org.example.user.model.Role;
import org.example.user.model.RoleEnum;
import org.example.user.model.User;
import org.example.util.error.EntityNotFoundException;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.seedRoles();
        this.seedAdmin();
    }

    private void seedRoles() {
        for (RoleEnum re : RoleEnum.values()) {
            roleRepository.save(Role.builder().name(re).build());
        }
    }

    private void seedAdmin() {
        Role role = roleRepository.findById(2L).orElseThrow(
                () -> new EntityNotFoundException(String.format("Role with id=%d was not found", 2L),
                        "The required object was not found."));
        User user = User.builder()
                .name("admin")
                .password(passwordEncoder.encode("admin"))
                .roles(List.of(role))
                .build();
        userRepository.save(user);
    }
}

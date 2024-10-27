package org.example.security;

import lombok.RequiredArgsConstructor;
import org.example.user.UserRepository;
import org.example.user.model.User;
import org.example.util.error.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenExtractor {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public User getUserFromToken(String token) {
        String jwt = token.substring(7);
        String userName = jwtService.extractUsername(jwt);
        return userRepository.findByName(userName).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with name=%s was not found", userName),
                        "The required object was not found."));
    }
}

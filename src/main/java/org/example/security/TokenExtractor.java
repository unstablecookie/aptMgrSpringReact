package org.example.security;

import lombok.RequiredArgsConstructor;
import org.example.user.model.User;
import org.example.util.error.EntityNotFoundException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
@RequiredArgsConstructor
public class TokenExtractor {
    private final JwtService jwtService;

    private final LdapTemplate ldapTemplate;

    public User getUserFromToken(String token) {
        String jwt = token.substring(7);
        String userName = jwtService.extractUsername(jwt);
        List<String> names = ldapTemplate.search(query()
                .attributes("uid")
                .where("objectclass").is("person").and("uid").is(userName),
                (AttributesMapper<String>) attrs -> attrs.get("uid").get().toString());
        if (names.size() > 1 || names.size() == 0) {
            throw new EntityNotFoundException(String.format("Too much listed for selected user name=%s", userName),
                    "The required object was not found.");
        }
        return User.builder()
                .name(userName)
                .build();
    }
}

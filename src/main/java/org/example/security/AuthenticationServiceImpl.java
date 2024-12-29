package org.example.security;

import lombok.AllArgsConstructor;
import org.example.aspect.LogAfterExecution;
import org.example.aspect.LogBeforeExecution;
import org.example.user.RoleRepository;
import org.example.user.UserRepository;
import org.example.user.dto.UserAuthDto;
import org.example.user.dto.UserDto;
import org.example.user.dto.UserMapper;
import org.example.user.model.Role;
import org.example.user.model.RoleEnum;
import org.example.user.model.User;
import org.example.util.auth.LoginResponse;
import org.example.util.error.EntityNotFoundException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final LdapTemplate ldapTemplate;

    @LogAfterExecution
    public UserDto signup(UserAuthDto userAuthDto) {
        Role role = roleRepository.findById(1L).orElseThrow(
                () -> new EntityNotFoundException(String.format("Role with id=%d was not found", 1L),
                        "The required object was not found."));
        User user = UserMapper.toUser(userAuthDto, passwordEncoder);
        user.setRoles(List.of(role));
        return UserMapper.toUserDto(userRepository.save(user));
    }

    @LogBeforeExecution
    @LogAfterExecution
    public LoginResponse authenticate(UserAuthDto userAuthDto) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthDto.getName(),
                userAuthDto.getPassword()));

        List<Role> roles = new ArrayList<>();
        try {
            List<String> allGroups = ldapTemplate.search(query()
                            .attributes("memberOf")
                            .where("objectclass").is("person").and("uid").is(userAuthDto.getName()),
                    (AttributesMapper<String>) attrs -> attrs.get("memberOf").get().toString());
            allGroups.stream().map(x -> {
                String[] arr = x.split(",");
                return arr[0].substring(3).toUpperCase();
            }).forEach( x -> roles.add(Role.builder().name(RoleEnum.valueOf(x)).build()));
        } catch (Exception e) {
            throw new EntityNotFoundException("can't find user groups", "The required object was not found.");
        }

        User user = UserMapper.toUser(userAuthDto, roles);
        String jwtToken = jwtService.generateToken(user);
        return LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .roles(user.getRoles().stream().map(x -> x.getName().name()).collect(Collectors.toList()))
                .build();
    }
}

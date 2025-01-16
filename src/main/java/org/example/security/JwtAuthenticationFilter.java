package org.example.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.user.model.Role;
import org.example.user.model.RoleEnum;
import org.example.user.model.User;
import org.springframework.lang.NonNull;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Slf4j
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final LdapTemplate ldapTemplate;
//    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            //logger.info("LOGGED .(authHeader == null || !authHeader.startsWith(Bearer )) ");
            return;
        }
        //logger.info("LOGGED .authHeader : " + authHeader);
        try {
            final String jwt = authHeader.substring(7);
            final String userName = jwtService.extractUsername(jwt);
            logger.info("LOGGED .userName : " + userName);
            //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            logger.info("LOGGED .authentication : " + authentication);
//            if (userName != null && authentication == null) {
//                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
//                if (jwtService.isTokenValid(jwt, userDetails)) {
//                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                            userDetails,
//                            null,
//                            userDetails.getAuthorities()
//                    );
//
//                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                }
//            }
            List<Role> roles = new ArrayList<>();
            try {
                List<String> allGroups = ldapTemplate.search(query()
                        .attributes("memberOf")
                        .where("objectclass").is("person").and("uid").is(userName),
                        (AttributesMapper<String>) attrs -> attrs.get("memberOf").get().toString());
                allGroups.stream().map(x -> {
                    String[] arr = x.split(",");
                    return arr[0].substring(3).toUpperCase();
                }).forEach( x -> roles.add(Role.builder().name(RoleEnum.valueOf(x)).build()));
            } catch (Exception e) {
                logger.error("guser groups query error : " + e.getMessage());
            }
            if (userName != null ) {
                User user = User.builder()
                        .name(userName)
                        .roles(roles).build();
                if (jwtService.isTokenValid(jwt, user)) {
                    logger.info("LOGGED .isTokenValid : true");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}

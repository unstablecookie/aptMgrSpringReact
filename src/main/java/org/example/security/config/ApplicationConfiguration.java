package org.example.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@AllArgsConstructor
public class ApplicationConfiguration {

    @Value("${ldap.server.ip}")
    private String ldapServerIp;

    @Value("${ldap.server.port}")
    private String ldapServerPort;

    @Value("${ldap.domain}")
    private String ldapDomain;

    @Value("${ldap.user.dn}")
    private String ldapUserDn;

    @Value("${ldap.user.password}")
    private String ldapUserPassword;

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    BaseLdapPathContextSource contextSource() {
        LdapContextSource contextSource = new DefaultSpringSecurityContextSource("ldap://" + ldapServerIp + ":" + ldapServerPort + "/" + ldapDomain);
        contextSource.setUserDn("cn=" + ldapUserDn + "," + ldapDomain);
        contextSource.setPassword(ldapUserPassword);
        return contextSource;
    }

    @Bean
    public AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource) throws Exception {
        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(contextSource);
        factory.setUserDnPatterns("uid={0},ou=users");
        return factory.createAuthenticationManager();
    }

    @Bean
    public LdapTemplate ldapTemplate(ContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }

}

package com.example.organizationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
                .oauth2ResourceServer(oauth2 -> oauth2.jwt().jwtAuthenticationConverter(jwtAuthenticationConverter()))
                .authorizeRequests(auth -> auth
                        .requestMatchers("/**")
                        .hasAuthority("ADMIN")
                        .anyRequest()
                        .authenticated()
                );

        return http.build();
    }

    Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(""); // No prefix for roles in Keycloak by default

        Converter<Jwt, Collection<GrantedAuthority>> keycloakConverter = jwt -> {
            Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
            Stream<String> realmRoles = realmAccess != null && realmAccess.get("roles") instanceof Collection ?
                    ((Collection<String>) realmAccess.get("roles")).stream() : Stream.empty();

            Map<String, Object> resourceAccess = (Map<String, Object>) jwt.getClaims().get("resource_access");
            Stream<String> resourceRoles = Stream.empty();
            if (resourceAccess != null && resourceAccess.get("ostock") instanceof Map) {
                Map<String, Object> ostockRoles = (Map<String, Object>) resourceAccess.get("ostock");
                if (ostockRoles.get("roles") instanceof Collection) {
                    resourceRoles = ((Collection<String>) ostockRoles.get("roles")).stream();
                }
            }

            Stream<String> allRoles = Stream.concat(realmRoles, resourceRoles);
            return allRoles.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        };

        return jwt -> {
            Collection<GrantedAuthority> authorities = Stream.concat(
                    jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                    Objects.requireNonNull(keycloakConverter.convert(jwt)).stream()
            ).collect(Collectors.toList());

            AbstractAuthenticationToken authToken = new AbstractAuthenticationToken(authorities) {
                @Override
                public Object getCredentials() {
                    return jwt;
                }

                @Override
                public Object getPrincipal() {
                    return jwt;
                }
            };
            authToken.setAuthenticated(true);
            return authToken;
        };
    }
}


package com.example.Java4.security;

import com.example.Java4.utils.RSAKeyProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {

    private final RSAKeyProperties keys;

    // Constructor to inject RSAKeyProperties bean
    public SecurityConfiguration(RSAKeyProperties keys){
        this.keys = keys;
    }

    // Bean definition for PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Bean definition for AuthenticationManager
    @Bean
    public AuthenticationManager authManager(UserDetailsService detailsService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(detailsService);
        daoProvider.setPasswordEncoder(passwordEncoder); // Set the PasswordEncoder
        return new ProviderManager(daoProvider);
    }

    // Bean definition for SecurityFilterChain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                // Configure CSRF protection
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    // Allow access to certain paths without authentication
                    auth.requestMatchers("/auth/**").permitAll();

                    // Allow access to specific paths for users with the ADMIN role
                    auth.requestMatchers("/admin/**").hasRole("ADMIN");
                    auth.requestMatchers("/user/s").hasRole("ADMIN");
                    auth.requestMatchers("/item/s").hasRole("ADMIN");
                    auth.requestMatchers("/list/s").hasRole("ADMIN");

                    // Allow access to specific paths for users with the USER role
                    auth.requestMatchers("/user/").hasRole("USER");
                    auth.requestMatchers("/user/{id}").hasRole("USER");
                    auth.requestMatchers("/item/").hasRole("USER");
                    auth.requestMatchers("/item/{itemId}/list/{listId}").hasRole("USER");
                    auth.requestMatchers("/item/{id}").hasRole("USER");

                    auth.requestMatchers("/list/**").hasRole("USER");

                    // Require authentication for any other requests
                    auth.anyRequest().authenticated();
                })
                // Configure OAuth2 Resource Server and JWT authentication
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
        // Configure session management
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
    }
    @Bean
    public JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));

        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtConverter;
    }

    public RSAKeyProperties getKeys(){
        return this.keys;
    }
    public int getUserIdFromJwt(String jwtToken) {
        try {
            Jwt jwt = jwtDecoder().decode(jwtToken);

            // Extract the userId from the JWT claims
            return jwt.getClaim("userId");
        } catch (JwtException e) {
            // Handle exception (invalid JWT)
            throw new RuntimeException("Failed to decode JWT", e);
        }
    }

}

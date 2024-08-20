package com.ilshan.autosell.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityBeans {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizedHttpRequests -> authorizedHttpRequests
                        .requestMatchers(HttpMethod.POST, "/autosell-api/cars")
                        .hasAuthority("SCOPE_edit_autosell")
                        .requestMatchers(HttpMethod.PATCH, "/autosell-api/car/{carId:\\d}")
                        .hasAuthority("SCOPE_edit_autosell")
                        .requestMatchers(HttpMethod.DELETE, "/autosell-api/car/{carId:\\d}")
                        .hasAuthority("SCOPE_edit_autosell")
                        .requestMatchers(HttpMethod.GET, "/autosell-api/cars")
                        .hasAuthority("SCOPE_view_autosell")
                        .requestMatchers(HttpMethod.GET, "/autosell-api/car/{carId:\\d}")
                        .hasAuthority("SCOPE_view_autosell")
                        .anyRequest().denyAll()
                )
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(sessionManagment -> sessionManagment
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
                        .jwt(Customizer.withDefaults()))
                .build();
    }
}

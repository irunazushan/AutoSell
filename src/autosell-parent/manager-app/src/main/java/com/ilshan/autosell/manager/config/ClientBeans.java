package com.ilshan.autosell.manager.config;

import com.ilshan.autosell.manager.client.CarRestClientImlp;
import com.ilshan.autosell.manager.security.OAuthClientHttpRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean
    public CarRestClientImlp carRestClientImlp(
            @Value("${autosell.services.uri:http://localhost:8081}") String autosellBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
    OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${autosell.services.registration-id:keycloak}") String registrationId) {
        return new CarRestClientImlp(RestClient.builder()
                .baseUrl(autosellBaseUri)
                .requestInterceptor(new OAuthClientHttpRequestInterceptor(
                        new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository,
                                authorizedClientRepository), registrationId))
                .build());
    }
}

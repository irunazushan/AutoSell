package com.ilshan.autosell.manager.config;

import com.ilshan.autosell.manager.client.CarRestClientImlp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean
    public CarRestClientImlp carRestClientImlp(
            @Value("${autosell.services.uri:http://localhost:8081}") String autosellBaseUri) {
        return new CarRestClientImlp(RestClient.builder()
                .baseUrl(autosellBaseUri)
                .build());
    }
}

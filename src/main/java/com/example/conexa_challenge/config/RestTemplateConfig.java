package com.example.conexa_challenge.config;

import lombok.var;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean(name = "restTemplate")
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        var clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        return restTemplateBuilder
                .requestFactory(() -> clientHttpRequestFactory)
                .build();
    }
}

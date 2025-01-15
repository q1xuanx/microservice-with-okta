package com.microservice.api_gateway.config;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder(ReactiveClientRegistrationRepository clientRegistrations, ServerOAuth2AuthorizedClientRepository authorizeClient) {
        var oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrations, authorizeClient);
        oauth.setDefaultClientRegistrationId("okta");
        return WebClient.builder();
    }
}

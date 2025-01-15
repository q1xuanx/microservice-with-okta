package com.microservice.api_gateway.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.DefaultServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${okta.oauth2.audience}")
    private String audience;
    private final ReactiveClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        http.authorizeExchange(auth -> auth.anyExchange().authenticated())
                .oauth2Login(oauth2 -> oauth2.authorizationRequestResolver(authorizationRequestResolver(this.clientRegistrationRepository)));
        return http.build();
    }
    private ServerOAuth2AuthorizationRequestResolver authorizationRequestResolver(ReactiveClientRegistrationRepository clientRegistrationRepository) {
        var authorizationRequestResolver = new DefaultServerOAuth2AuthorizationRequestResolver(clientRegistrationRepository);
        authorizationRequestResolver.setAuthorizationRequestCustomizer(authorizeRequestCustomizer());
        return authorizationRequestResolver;
    }
    private Consumer<OAuth2AuthorizationRequest.Builder> authorizeRequestCustomizer(){
        return customizer -> customizer.additionalParameters(params -> params.put("audience", audience));
    }
}

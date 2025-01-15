package com.microservice.api_gateway.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private final Logger log = LoggerFactory.getLogger(HomeController.class);
    @GetMapping("/")
    public String howdy (@AuthenticationPrincipal OidcUser user) {
        return user.getFullName();
    }

    @GetMapping("/print-token")
    public String printAccessToken(@RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient oAuth2AuthorizedClient) {
        var accessToken = oAuth2AuthorizedClient.getAccessToken();
        log.info("Access token value: {}", accessToken.getTokenValue());
        log.info("Token type: {}", accessToken.getTokenType());
        log.info("Expires at: {}", accessToken.getExpiresAt());
        return "Access token has print in log !";
    }
}

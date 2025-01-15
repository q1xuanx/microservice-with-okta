package com.microservice.car_service.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;


@RestController
public class HomeController {
    private final Logger log = LoggerFactory.getLogger(HomeController.class);
    @GetMapping("/home")
    public String home(Principal principal) {
        var username = principal.getName();
        if (principal instanceof JwtAuthenticationToken token) {
            log.info(Marker.ANY_MARKER, "claims: " + token.getTokenAttributes());
        }
        return "Hello " + username;
    }
}

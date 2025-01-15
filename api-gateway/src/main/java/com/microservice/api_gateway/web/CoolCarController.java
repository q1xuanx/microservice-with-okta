package com.microservice.api_gateway.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
class CoolCarController {
    private Logger log = LoggerFactory.getLogger(CoolCarController.class);
    private final WebClient.Builder webClientBuilder;
    private final ReactiveCircuitBreaker reactiveCircuitBreaker;

    public CoolCarController(WebClient.Builder webClientBuilder, ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory) {
        this.webClientBuilder = webClientBuilder;
        this.reactiveCircuitBreaker = reactiveCircuitBreakerFactory.create("circuit-breaker");
    }

    record Car(String name){ }

    @GetMapping("/cool-cars")
    public Flux<Car> coolCars(){
        return reactiveCircuitBreaker.run(
                webClientBuilder.baseUrl("http://localhost:8080").build().get().uri("/cars")
                        .retrieve().bodyToFlux(Car.class)
                        .filter(this::isCool),
                throwable -> {
                    log.warn("Error while making request " + throwable.getMessage());
                    return Flux.empty();
                });
    }
    public boolean isCool(Car car){
        return !car.name().equals("AMC Gremlin") &&
                !car.name().equals("Triumph Stag") &&
                !car.name().equals("Ford Pinto") &&
                !car.name().equals("Yugo GV");
    }
}

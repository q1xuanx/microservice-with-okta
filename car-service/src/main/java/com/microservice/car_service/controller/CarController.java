package com.microservice.car_service.controller;

import com.microservice.car_service.data.Car;
import com.microservice.car_service.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarController {
    private final CarRepository carRepository;

    @GetMapping("/cars")
    public List<Car> getCar(){
        return carRepository.findAll();
    }
}

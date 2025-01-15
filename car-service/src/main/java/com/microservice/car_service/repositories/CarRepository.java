package com.microservice.car_service.repositories;

import com.microservice.car_service.data.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {

}

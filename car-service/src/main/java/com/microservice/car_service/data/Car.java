package com.microservice.car_service.data;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Car {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    public Car(){}

    public Car(String name){
        this.name = name;
    }

}

package com.ilshan.autosell.repositories;

import com.ilshan.autosell.entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    List<Car> findAll();

    Car save(Car car);

    Optional<Car> findById(Integer carId);


    void deleteById(int carId);
}

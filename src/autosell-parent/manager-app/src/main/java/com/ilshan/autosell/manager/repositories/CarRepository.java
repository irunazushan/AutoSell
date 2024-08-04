package com.ilshan.autosell.manager.repositories;

import com.ilshan.autosell.manager.entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    List<Car> findAll();

    Car save(Car car);

    Optional<Car> findById(Integer carId);


    void deleteById(int carId);
}

package com.ilshan.autosell.services;

import com.ilshan.autosell.entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    List<Car> findAllCars();

    Car saveCar(String name, String description, String price);

    Optional<Car> findCar(Integer carId);

    void updateCar(int carId, String name, String description, String price);

    void deleteCar(int carId);
}

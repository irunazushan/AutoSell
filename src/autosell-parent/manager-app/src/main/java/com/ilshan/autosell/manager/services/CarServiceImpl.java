package com.ilshan.autosell.manager.services;

import com.ilshan.autosell.manager.entity.Car;
import com.ilshan.autosell.manager.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    @Override
    public List<Car> findAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Car saveCar(String name, String description, String price) {
        Integer intPrice = null;
        if (price != null) {
            try {
                intPrice = Integer.parseInt(price);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid price format: " + price);
            }
        }
        return carRepository.save(new Car (null, name, description, intPrice));
    }

    @Override
    public Optional<Car> findCar(Integer carId) {
        return carRepository.findById(carId);
    }

    @Override
    public void updateCar(int carId, String name, String description, String price) {
        this.carRepository.findById(carId)
                .ifPresentOrElse(product -> {
                    product.setName(name);
                    product.setDescription(description);
                    product.setPrice(Integer.parseInt(price));
                }, () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    public void deleteCar(int carId) {
        carRepository.deleteById(carId);
    }
}

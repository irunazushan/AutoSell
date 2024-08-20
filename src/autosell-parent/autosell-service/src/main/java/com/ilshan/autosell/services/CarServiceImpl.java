package com.ilshan.autosell.services;

import com.ilshan.autosell.entity.Car;
import com.ilshan.autosell.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    @Override
    public Iterable<Car> findAllCars(String filter) {
        if (filter != null && !filter.isBlank()) {
            return carRepository.findAllByNameLikeIgnoreCase("%" + filter + "%");
        } else {
            return carRepository.findAll();
        }
    }

    @Override
    public Car saveCar(String name, String description, String price) {
        return carRepository.save(new Car (null, name, description, Integer.parseInt(price)));
    }

    @Override
    public Optional<Car> findCar(Integer carId) {
        return carRepository.findById(carId);
    }

    @Override
    public void updateCar(int carId, String name, String description, String price) {
        carRepository.findById(carId)
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

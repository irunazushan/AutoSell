package com.ilshan.autosell.manager.repositories;

import com.ilshan.autosell.manager.entity.Car;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.IntStream;

@Repository //стереотипичная анотация
public class InMemoryCarRepository implements CarRepository {
    private List<Car> cars;

    public InMemoryCarRepository() {
        if (cars == null) {
            cars = new ArrayList<>();
        }
        IntStream.rangeClosed(1, 5).forEach(i -> this.cars.add(new Car(
                i,
                "Name_%d".formatted(i),
                "escription_%d".formatted(i),
                i*1000
        )));

    }
    public List<Car> findAll() {
        return Collections.unmodifiableList(this.cars);
    };

    @Override
    public Car save(Car car) {
        car.setId(cars.stream()
                .map(Car::getId)
                .max(Integer::compareTo).orElse(0) + 1);
        cars.add(car);
        return car;
    }

    @Override
    public Optional<Car> findById(Integer carId) {
        return this.cars.stream()
                .filter(car -> Objects.equals(car.getId(), carId))
                .findFirst();
    }

    @Override
    public void deleteById(int carId) {
        this.cars.removeIf(car -> Objects.equals(car.getId(), carId));
    }
}

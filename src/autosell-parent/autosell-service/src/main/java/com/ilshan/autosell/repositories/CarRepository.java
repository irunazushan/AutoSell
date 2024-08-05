package com.ilshan.autosell.repositories;

import com.ilshan.autosell.entity.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Integer> {
    Iterable<Car> findAllByNameLikeIgnoreCase(String filter);
}

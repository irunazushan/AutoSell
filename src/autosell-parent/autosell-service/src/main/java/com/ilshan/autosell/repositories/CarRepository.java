package com.ilshan.autosell.repositories;

import com.ilshan.autosell.entity.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {

    @Query(value = "select c from Car c where name ilike :filter")
    Iterable<Car> findAllByNameLikeIgnoreCase(@Param("filter") String filter);
}

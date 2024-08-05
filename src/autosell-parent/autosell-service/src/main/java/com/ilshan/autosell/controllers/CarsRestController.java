package com.ilshan.autosell.controllers;


import com.ilshan.autosell.controllers.payloads.NewCarPayload;
import com.ilshan.autosell.entity.Car;
import com.ilshan.autosell.services.CarService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/autosell-api/cars")
public class CarsRestController {
    private final CarService carService;

    @GetMapping
    public Iterable<Car> getCars(@RequestParam(name = "filter", required = false) String filter) {
        return this.carService.findAllCars(filter);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createCar(@Valid @RequestBody NewCarPayload payload, BindingResult bindingResult,
                                       UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Car car = carService.saveCar(payload.name(), payload.description(), payload.price());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/autosell-api/cars{carId}")
                            .build(Map.of("carId", car.getId())))
                    .body(car);
        }
    }
}

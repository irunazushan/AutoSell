package com.ilshan.autosell.controllers;


import com.ilshan.autosell.controllers.payloads.NewCarPayload;
import com.ilshan.autosell.entity.Car;
import com.ilshan.autosell.services.CarService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/autosell-api/cars")
public class CarsRestController {
    private final CarService carService;

    @GetMapping
    public List<Car> getCars() {
        return this.carService.findAllCars();
    }

    @PostMapping
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

package com.ilshan.autosell.controllers;


import com.ilshan.autosell.controllers.payloads.NewCarPayload;
import com.ilshan.autosell.controllers.payloads.UpdateCarPayload;
import com.ilshan.autosell.entity.Car;
import com.ilshan.autosell.services.CarService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/autosell-api/car/{carId:\\d+}")
public class CarRestController {
    private final CarService carService;
    private final MessageSource messageSource;

    @ModelAttribute("car")
    public Car getCar(@PathVariable("carId") int carId) {
        return this.carService.findCar(carId)
                .orElseThrow(() -> new NoSuchElementException("autosell.errors.cars.auto_not_found"));
    }
    @GetMapping
    public Car getCar(@ModelAttribute("car") Car car) {
        return car;
    }

    @PatchMapping
    @Transactional
    public ResponseEntity<?> editCar(@PathVariable("carId") int carId, @Valid @RequestBody UpdateCarPayload payload,
                                     BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            carService.updateCar(carId, payload.name(), payload.description(), payload.price());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> deleteCar(@PathVariable("carId") int carId) {
        carService.deleteCar(carId);
        return  ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> noSuchElementExceptionHandler(NoSuchElementException exception, Locale locale) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                Objects.requireNonNull(messageSource.getMessage(exception.getMessage(),
                        new Object[0], exception.getMessage(), locale))));
    }

}

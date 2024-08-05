package com.ilshan.autosell.manager.controllers;


import com.ilshan.autosell.manager.client.BadRequestException;
import com.ilshan.autosell.manager.client.CarRestClient;
import com.ilshan.autosell.manager.controllers.payloads.UpdateCarPayload;
import com.ilshan.autosell.manager.entity.Car;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/autosell/cars/{carId:\\d+}")
public class CarController {
    private final CarRestClient carRestClient;
    private final MessageSource messageSource;

    @ModelAttribute("car")
    public Car car(@PathVariable("carId") int carId) {
        return this.carRestClient.findCar(carId).orElseThrow(() -> new NoSuchElementException("autosell.errors.cars.auto_not_found"));
    }

    @GetMapping
    public String getCar() {
        return "/autosell/cars/car";
    }

    @GetMapping("/edit")
    public String editPage() {
        return "/autosell/cars/edit";
    }

    @PostMapping("/edit")
    public String editCar(@ModelAttribute(name = "car", binding = false) Car car,
                          @ModelAttribute("payload") UpdateCarPayload payload,
                          Model model) {
        try {
            this.carRestClient.updateCar(car.id(), payload.name(), payload.description(), payload.price());
            return "redirect:/autosell/cars/%d".formatted(car.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "/autosell/cars/edit";
        }
    }

    @PostMapping("/delete")
    public String deleteCar(@PathVariable("carId") int carId) {
        this.carRestClient.deleteCar(carId);
        return "redirect:/autosell/cars";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoElementError(NoSuchElementException error, Model model, HttpServletResponse httpResponse, Locale locale) {
        httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
        model.addAttribute("error",
                this.messageSource.getMessage(error.getMessage(),
                        new Object[0], error.getMessage(), locale));
        return "errors/404";
    }
}

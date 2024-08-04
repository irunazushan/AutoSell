package com.ilshan.autosell.manager.controllers;


import com.ilshan.autosell.manager.controllers.payloads.NewCarPayload;
import com.ilshan.autosell.manager.controllers.payloads.UpdateCarPayload;
import com.ilshan.autosell.manager.entity.Car;
import com.ilshan.autosell.manager.services.CarService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/autosell/cars/{carId:\\d+}")
public class CarController {
    private final CarService carService;
    private final MessageSource messageSource;

    @ModelAttribute("car")
    public Car car(@PathVariable("carId") int carId) {
        return this.carService.findCar(carId).orElseThrow(() -> new NoSuchElementException("autosell.errors.cars.auto_not_found"));
    }

    @GetMapping
    public String getCar() {
        return "/autosell/cars/car";
    }
    @GetMapping("/edit")
    public String editPage(@ModelAttribute("payload") UpdateCarPayload updateCarPayload) {
        return "/autosell/cars/edit";
    }

    @PostMapping("/edit")
    public String editCar(@ModelAttribute(name = "car", binding = false) Car car, @ModelAttribute("payload") @Valid UpdateCarPayload updateCarPayload,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/autosell/cars/edit";
        }
        this.carService.updateCar(car.getId(), updateCarPayload.name(), updateCarPayload.description(), updateCarPayload.price());
        return "redirect:/autosell/cars/%d".formatted(car.getId());
    }

    @PostMapping("/delete")
    public String deleteCar(@PathVariable("carId") int carId) {
        this.carService.deleteCar(carId);
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

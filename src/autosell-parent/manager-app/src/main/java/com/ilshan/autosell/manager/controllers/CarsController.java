package com.ilshan.autosell.manager.controllers;


import com.ilshan.autosell.manager.controllers.payloads.NewCarPayload;
import com.ilshan.autosell.manager.entity.Car;
import com.ilshan.autosell.manager.services.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/autosell/cars")
public class CarsController {
    private final CarService carService;

    @GetMapping
    public String getCarsList(Model model) {
        model.addAttribute("cars", carService.findAllCars());
        return "/autosell/cars/list";
    }

    @GetMapping("create")
    public String createPage(@ModelAttribute("payload") NewCarPayload newCarPayload) {
        return "/autosell/cars/create";
    }

    @PostMapping("create")
    public String createCar(@ModelAttribute("payload") @Valid NewCarPayload newCarPayload,
                            BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            return "/autosell/cars/create";
        }
        Car car = this.carService.saveCar(newCarPayload.name(), newCarPayload.description(), newCarPayload.price());
        return "redirect:/autosell/cars/%d".formatted(car.getId());
    }
}

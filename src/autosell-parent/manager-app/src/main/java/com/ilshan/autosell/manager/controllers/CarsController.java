package com.ilshan.autosell.manager.controllers;


import com.ilshan.autosell.manager.client.BadRequestException;
import com.ilshan.autosell.manager.client.CarRestClient;
import com.ilshan.autosell.manager.controllers.payloads.NewCarPayload;
import com.ilshan.autosell.manager.entity.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/autosell/cars")
public class CarsController {
    private final CarRestClient carRestClient;

    @GetMapping
    public String getCarsList(@RequestParam(value = "filter", required = false) String filter, Model model) {
        model.addAttribute("cars", carRestClient.findAllCars(filter));
        model.addAttribute("filter", filter);
        return "/autosell/cars/list";
    }

    @GetMapping("create")
    public String createPage() {
        return "/autosell/cars/create";
    }

    @PostMapping("create")
    public String createCar(@ModelAttribute("payload") NewCarPayload payload,
                            Model model) {
        try {
            Car car = carRestClient.createCar(payload.name(), payload.description(), payload.price());
            return "redirect:/autosell/cars/%d".formatted(car.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "/autosell/cars/create";
        }
    }
}

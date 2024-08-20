package com.ilshan.autosell.controllers.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateCarPayload(
        @NotEmpty(message = "{autosell.cars.update.errors.name_is_null}")
        @Size(min = 2, max = 50, message = "{autosell.cars.update.errors.name_size_is_invalid}")
        String name,

        @Size(max = 1000, message = "{autosell.cars.update.errors.description_size_is_invalid}")
        String description,

        @Positive(message = "{autosell.cars.update.errors.price_is_negative}")
        String price) {
}

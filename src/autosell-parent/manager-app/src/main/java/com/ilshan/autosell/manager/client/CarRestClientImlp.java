package com.ilshan.autosell.manager.client;

import com.ilshan.autosell.manager.controllers.payloads.NewCarPayload;
import com.ilshan.autosell.manager.controllers.payloads.UpdateCarPayload;
import com.ilshan.autosell.manager.entity.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class CarRestClientImlp implements CarRestClient {
    private static ParameterizedTypeReference<List<Car>> CAR_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {
            };
    private final RestClient restClient;

    @Override
    public List<Car> findAllCars() {
        return this.restClient
                .get()
                .uri("/autosell-api/cars")
                .retrieve()
                .body(CAR_TYPE_REFERENCE);
    }

    @Override
    public Car createCar(String name, String description, String price) {
        try {
            return this.restClient
                    .post()
                    .uri("/autosell-api/cars")
                    .body(new NewCarPayload(name, description, price))
                    .retrieve()
                    .body(Car.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<Car> findCar(Integer carId) {
        try {
            return Optional.ofNullable(this.restClient
                    .get()
                    .uri("/autosell-api/car/{carId}", carId)
                    .retrieve()
                    .body(Car.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateCar(int carId, String name, String description, String price) {
        try {
            this.restClient
                    .patch()
                    .uri("/autosell-api/car/{carId}", carId)
                    .body(new UpdateCarPayload(name, description, price))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteCar(int carId) {
        try {
            this.restClient
                    .delete()
                    .uri("/autosell-api/car/{carId}", carId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}

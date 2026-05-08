package com.mahmoud.car;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CarService {

    private final CarDao carDao;

    public CarService(CarDao carDao) {
        this.carDao = carDao;
    }

    public List<Car> getAllCars() {
        return carDao.getAllCars();
    }

    public Optional<Car> getCarById(UUID carId) {
        return getAllCars()
                .stream()
                .filter(car -> car.getId().equals(carId))
                .findFirst();
    }
}
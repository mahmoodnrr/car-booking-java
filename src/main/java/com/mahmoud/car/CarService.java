package com.mahmoud.car;

import java.util.Optional;
import java.util.UUID;

public class CarService {

    private CarDao carDao = new CarDao();

    public Car[] getAllCars() {
        return carDao.getAllCars();
    }

    public Optional<Car> getCarById(UUID carId) {

        var cars = getAllCars();

        for(Car car : cars){
            if(car.getId().equals(carId)) return Optional.of(car);
        }

        return Optional.empty();
    }
}
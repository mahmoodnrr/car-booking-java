package com.mahmoud.car;

import java.util.Optional;

public class CarService {

    private CarDao carDao;

    public CarService(CarDao carDao) {
        this.carDao = carDao;
    }

    public static Car[] viewAllCars() {
        return CarDao.getAllAvailableCars();
    }

    public Optional<Car> getCarByBrandService(String brand) {
        return carDao.getCarByBrand(brand);
    }
}
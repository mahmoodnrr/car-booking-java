package com.mahmoud.car;

import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarFakerDataAccessService implements CarDao {

    @Override
    public List<Car> getAllCars() {
        Faker faker = new Faker();

        List<Car> cars = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            cars.add(new Car(UUID.randomUUID(),
                    faker.bothify("??## ???").toUpperCase(),
                    BigDecimal.valueOf(faker.number().randomDouble(2, 20000, 100_000)),
                    faker.options().option(Brand.values()),
                    faker.bool().bool()
                    ));
        }

        return cars;
    }
}

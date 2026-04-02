package com.mahmoud.car;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class CarDao {

    private static Car[] cars;

    public static class CarDAOException extends RuntimeException{
        public CarDAOException(String message) {
            super(message);
        }
    }

    static {
        cars = new Car[]{
                new Car(UUID.randomUUID(), "M70 LOP", BigDecimal.valueOf(70), Brand.BMW, false, true ),
                new Car(UUID.randomUUID(), "N68 HGJ", BigDecimal.valueOf(70), Brand.MERCEDES, false, true ),
                new Car(UUID.randomUUID(), "L72 RTT", BigDecimal.valueOf(40), Brand.TOYOTA, false, true ),
                new Car(UUID.randomUUID(), "E74 TLS", BigDecimal.valueOf(60), Brand.TESLA, true, true )
        };
    }

    public static Car[] getAllAvailableCars(){
        if(cars.length == 0) throw new CarDAOException("No available cars!");
        return cars;
    }

    public Optional<Car> getCarByBrand(String carBrand){

        for(Car car : cars){
            if(car.getBrand().name().equalsIgnoreCase(carBrand)) return Optional.of(car);
        }

        System.out.println("\nPlease type a valid car brand\n");
        return Optional.empty();
    }
}

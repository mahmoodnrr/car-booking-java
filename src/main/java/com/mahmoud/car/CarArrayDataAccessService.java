package com.mahmoud.car;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class CarArrayDataAccessService implements CarDao{

    private static final List<Car> cars;

    static {
        cars = List.of(
                new Car(UUID.randomUUID(), "M70 LOP", BigDecimal.valueOf(70), Brand.BMW, false ),
                new Car(UUID.randomUUID(), "N68 HGJ", BigDecimal.valueOf(70), Brand.MERCEDES, false ),
                new Car(UUID.randomUUID(), "L72 RTT", BigDecimal.valueOf(40), Brand.TOYOTA, false ),
                new Car(UUID.randomUUID(), "E74 TLS", BigDecimal.valueOf(60), Brand.TESLA, true)
        );
    }

    @Override
    public List<Car> getAllCars(){
        return cars;
    }
}

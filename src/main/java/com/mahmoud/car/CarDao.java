package com.mahmoud.car;

import java.math.BigDecimal;
import java.util.UUID;

public class CarDao {

    private static Car[] cars;

    static {
        cars = new Car[]{
                new Car(UUID.randomUUID(), "M70 LOP", BigDecimal.valueOf(70), Brand.BMW, false, true ),
                new Car(UUID.randomUUID(), "N68 HGJ", BigDecimal.valueOf(70), Brand.MERCEDES, false, true ),
                new Car(UUID.randomUUID(), "L72 RTT", BigDecimal.valueOf(40), Brand.TOYOTA, false, true ),
                new Car(UUID.randomUUID(), "E74 TLS", BigDecimal.valueOf(60), Brand.TESLA, true, true )
        };
    }

    public Car[] getAllCars(){
        return cars;
    }
}

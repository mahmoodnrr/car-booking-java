package com.mahmoud.car;

import java.math.BigDecimal;
import java.util.UUID;

public class CarArrayDataAccessService implements CarDao{

    private static final Car[] cars;

    static {
        cars = new Car[]{
                new Car(UUID.randomUUID(), "M70 LOP", BigDecimal.valueOf(70), Brand.BMW, false ),
                new Car(UUID.randomUUID(), "N68 HGJ", BigDecimal.valueOf(70), Brand.MERCEDES, false ),
                new Car(UUID.randomUUID(), "L72 RTT", BigDecimal.valueOf(40), Brand.TOYOTA, false ),
                new Car(UUID.fromString("0f6db7f0-dfeb-411c-8661-8ca223d20170"), "E74 TLS", BigDecimal.valueOf(60), Brand.TESLA, true)
        };
    }

    @Override
    public Car[] getAllCars(){
        return cars;
    }
}

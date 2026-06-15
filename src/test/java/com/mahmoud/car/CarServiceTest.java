package com.mahmoud.car;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class CarServiceTest {

    @Mock
    private CarDao carDao;

    @InjectMocks
    private CarService underTest;


    @Test
    void canGetAllCars() {
        Car car1 = new Car(UUID.randomUUID(), "EX12 YYR", new BigDecimal("60"), Brand.BMW, true);
        Car car2 = new Car(UUID.randomUUID(), "KI99 YYR", new BigDecimal("33"), Brand.TOYOTA, true);

        when(carDao.getAllCars()).thenReturn(List.of(car1, car2));

        assertThat(underTest.getAllCars()).isEqualTo(List.of(car1, car2));
    }

    @Test
    void canGetCarById() {
        Car car = new Car(UUID.randomUUID(), "EX12 YYR", new BigDecimal("60"), Brand.BMW, true);

        when(carDao.getAllCars()).thenReturn(List.of(car));

        assertThat(underTest.getCarById(car.getId())).isEqualTo(Optional.of(car));
    }


}
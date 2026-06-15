package com.mahmoud.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CarArrayDataAccessServiceTest {

    private CarArrayDataAccessService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CarArrayDataAccessService();
    }

    @Test
    void canGetAllCars() {

        List<Car> allCars = underTest.getAllCars();

        assertThat(allCars.isEmpty()).isEqualTo(false);
        assertThat(allCars.size()).isEqualTo(4);
    }

}
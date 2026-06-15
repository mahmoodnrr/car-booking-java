package com.mahmoud.booking;

import com.mahmoud.car.Brand;
import com.mahmoud.car.Car;
import com.mahmoud.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CarBookingArrayDataAccessServiceTest {

    private CarBookingArrayDataAccessService underTest;
    private LocalDateTime localDateTime;

    private Clock clock;

    @BeforeEach
    void setUp() {
        underTest = new CarBookingArrayDataAccessService();
        localDateTime = LocalDateTime.of(2026, 6, 1, 0, 0, 0);
        clock = Clock.fixed(localDateTime.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
    }

    @Test
    void canSaveBooking() {
        User user = new User(UUID.randomUUID(), "Test User");
        Car car = new Car(UUID.randomUUID(), "EX12 YYR", new BigDecimal("60"), Brand.BMW, true);
        var numberOfDays = ChronoUnit.DAYS.between(localDateTime, localDateTime.plusDays(1));
        BigDecimal price = new BigDecimal(numberOfDays).multiply(car.getRentalPricePerDay());

        CarBooking carBooking = new CarBooking(UUID.randomUUID(), user, car, LocalDateTime.now(clock), LocalDateTime.now(clock).plusDays(1),
                price, BookingStatus.ACTIVE, localDateTime);

        underTest.saveBooking(carBooking);
        List<CarBooking> allBookings = underTest.getAllBookings();

        assertThat(allBookings.size()).isEqualTo(1);
    }

    @Test
    void canCancelBooking() {
        User user = new User(UUID.randomUUID(), "Test User");
        Car car = new Car(UUID.randomUUID(), "EX12 YYR", new BigDecimal("60"), Brand.BMW, true);
        var numberOfDays = ChronoUnit.DAYS.between(localDateTime, localDateTime.plusDays(1));
        BigDecimal price = new BigDecimal(numberOfDays).multiply(car.getRentalPricePerDay());

        CarBooking carBooking = new CarBooking(UUID.randomUUID(), user, car, LocalDateTime.now(clock), LocalDateTime.now(clock).plusDays(1),
                price, BookingStatus.ACTIVE, localDateTime);

        underTest.saveBooking(carBooking);
        underTest.cancelBooking(carBooking.getId());
        List<CarBooking> allBookings = underTest.getAllBookings();

        assertThat(allBookings.getFirst().getStatus()).isEqualTo(BookingStatus.CANCELLED);
    }

    @Test
    void canThrowIllegalArgumentException_WhenCancelBookingIdInvalid() {

        assertThatThrownBy(() -> underTest.cancelBooking(UUID.randomUUID())).
                isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> underTest.cancelBooking(UUID.randomUUID())).
                hasMessageContaining("was not found");

    }

}
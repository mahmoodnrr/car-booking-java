package com.mahmoud.booking;

import com.mahmoud.car.Brand;
import com.mahmoud.car.Car;
import com.mahmoud.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CarBookingFileDataAccessServiceTest {

    @TempDir
    Path tempDir;

    private CarBookingFileDataAccessService underTest;
    private LocalDateTime localDateTime;
    private Clock clock;

    @BeforeEach
    void setUp() {
        String filePath = tempDir.resolve("bookings.dat").toString();
        underTest = new CarBookingFileDataAccessService(filePath);
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

        assertThat(underTest.cancelBooking(carBooking.getId())).isEqualTo(true);

        List<CarBooking> allBookings = underTest.getAllBookings();
        assertThat(allBookings.getFirst().getStatus()).isEqualTo(BookingStatus.CANCELLED);
    }

    @Test
    void canGetAllBooking() {
        User user1 = new User(UUID.randomUUID(), "Test User1");
        User user2 = new User(UUID.randomUUID(), "Test User2");

        Car car1 = new Car(UUID.randomUUID(), "EX11 YYR", new BigDecimal("60"), Brand.BMW, true);
        Car car2 = new Car(UUID.randomUUID(), "EX12 YYR", new BigDecimal("90"), Brand.TESLA, true);

        var numberOfDays = ChronoUnit.DAYS.between(localDateTime, localDateTime.plusDays(1));

        BigDecimal price1 = new BigDecimal(numberOfDays).multiply(car1.getRentalPricePerDay());
        BigDecimal price2 = new BigDecimal(numberOfDays).multiply(car2.getRentalPricePerDay());

        CarBooking carBooking1 = new CarBooking(UUID.randomUUID(), user1, car1, LocalDateTime.now(clock), LocalDateTime.now(clock).plusDays(1),
                price1, BookingStatus.ACTIVE, localDateTime);

        CarBooking carBooking2 = new CarBooking(UUID.randomUUID(), user1, car1, LocalDateTime.now(clock), LocalDateTime.now(clock).plusDays(1),
                price2, BookingStatus.ACTIVE, localDateTime);

        underTest.saveBooking(carBooking1);
        underTest.saveBooking(carBooking2);

        List<CarBooking> allBookings = underTest.getAllBookings();

        assertThat(allBookings.size()).isEqualTo(2);
    }


}
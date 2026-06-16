package com.mahmoud.booking;

import com.mahmoud.car.Brand;
import com.mahmoud.car.Car;
import com.mahmoud.car.CarService;
import com.mahmoud.user.User;
import com.mahmoud.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarBookingServiceTest {


    @Mock
    private CarBookingDao carBookingDao;

    @Mock
    private UserService userService;

    @Mock
    private CarService carService;

    @InjectMocks
    private CarBookingService underTest;

    private LocalDateTime localDateTime;
    private Clock clock;

    @BeforeEach
    void setUp() {
        localDateTime = LocalDateTime.now();
        clock = Clock.fixed(localDateTime.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
    }

    @Test
    void canBookCar() {
        User user = new User(UUID.randomUUID(), "User1");
        Car car = new Car(UUID.randomUUID(), "EX12 YYR", new BigDecimal("60"), Brand.BMW, true);

        when(userService.getUserById(any(UUID.class)))
                .thenReturn(Optional.of(user));

        when(carService.getCarById(any(UUID.class)))
                .thenReturn(Optional.of(car));

        CarBooking carBooking = underTest.bookCar(user.getId(), car.getId(), LocalDateTime.now(clock), LocalDateTime.now(clock).plusDays(2));

        ArgumentCaptor<CarBooking> carBookingArgumentCaptor = ArgumentCaptor.forClass(CarBooking.class);

        verify(carBookingDao, times(1)).saveBooking(carBookingArgumentCaptor.capture());
        List<CarBooking> allValues = carBookingArgumentCaptor.getAllValues();

        assertThat(allValues.getFirst().getEndDate()).isEqualTo(LocalDateTime.now(clock).plusDays(2));
        assertThat(allValues.getFirst().getStartDate()).isEqualTo(LocalDateTime.now(clock));
        assertThat(allValues.getFirst().getStatus()).isEqualTo(BookingStatus.ACTIVE);
        assertThat(allValues.getFirst().getCar()).isEqualTo(car);
        assertThat(allValues.getFirst().getUser()).isEqualTo(user);
    }

    @Test
    void canGetAllBookings() {
        User user = new User(UUID.randomUUID(), "User1");
        Car car = new Car(UUID.randomUUID(), "EX12 YYR", new BigDecimal("60"), Brand.BMW, true);

        when(userService.getUserById(any(UUID.class)))
                .thenReturn(Optional.of(user));

        when(carService.getCarById(any(UUID.class)))
                .thenReturn(Optional.of(car));

        CarBooking carBooking = underTest.bookCar(user.getId(), car.getId(), LocalDateTime.now(clock), LocalDateTime.now(clock).plusDays(2));

        when(carBookingDao.getAllBookings()).thenReturn(List.of(carBooking));

        assertThat(underTest.getAllBookings().size()).isEqualTo(1);
    }

    @Test
    void canCancelBooking() {
        User user = new User(UUID.randomUUID(), "User1");
        Car car = new Car(UUID.randomUUID(), "EX12 YYR", new BigDecimal("60"), Brand.BMW, true);

        when(userService.getUserById(any(UUID.class)))
                .thenReturn(Optional.of(user));

        when(carService.getCarById(any(UUID.class)))
                .thenReturn(Optional.of(car));

        CarBooking carBooking = underTest.bookCar(user.getId(), car.getId(), LocalDateTime.now(clock), LocalDateTime.now(clock).plusDays(2));

        when(carBookingDao.getAllBookings()).thenReturn(List.of(carBooking));
        when(carBookingDao.cancelBooking(any(UUID.class))).thenReturn(true);

        assertThat(underTest.cancelBooking(carBooking.getId())).isEqualTo(true);
    }

    @Test
    void canGetBookingById() {
        User user = new User(UUID.randomUUID(), "User1");
        Car car = new Car(UUID.randomUUID(), "EX12 YYR", new BigDecimal("60"), Brand.BMW, true);

        when(userService.getUserById(any(UUID.class)))
                .thenReturn(Optional.of(user));

        when(carService.getCarById(any(UUID.class)))
                .thenReturn(Optional.of(car));

        CarBooking carBooking = underTest.bookCar(user.getId(), car.getId(), LocalDateTime.now(clock), LocalDateTime.now(clock).plusDays(2));

        when(carBookingDao.getAllBookings()).thenReturn(List.of(carBooking));

        assertThat(underTest.getUserBookingsById(carBooking.getUser().getId())).isEqualTo(List.of(carBooking));
    }

    @Test
    void canThrowIllegalArgumentExceptionWhenUserNotFound() {
        User user = new User(UUID.randomUUID(), "User1");
        Car car = new Car(UUID.randomUUID(), "EX12 YYR", new BigDecimal("60"), Brand.BMW, true);

        when(userService.getUserById(any(UUID.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->  underTest.bookCar(user.getId(), car.getId(),
                LocalDateTime.now(clock), LocalDateTime.now(clock).plusDays(2)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void canThrowIllegalArgumentExceptionWhenCarNotFound() {
        User user = new User(UUID.randomUUID(), "User1");
        Car car = new Car(UUID.randomUUID(), "EX12 YYR", new BigDecimal("60"), Brand.BMW, true);

        when(userService.getUserById(any(UUID.class)))
                .thenReturn(Optional.of(user));

        when(carService.getCarById(any(UUID.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->  underTest.bookCar(user.getId(), car.getId(),
                LocalDateTime.now(clock), LocalDateTime.now(clock).plusDays(2)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
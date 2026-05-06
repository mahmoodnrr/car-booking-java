package com.mahmoud.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CarBookingArrayDataAccessService implements CarBookingDao {

    private static List<CarBooking> carBookings;

    static {
        carBookings = new ArrayList<>();
    }

    @Override
    public void saveBooking(CarBooking carBooking) {
        carBookings.add(carBooking);
    }

    @Override
    public List<CarBooking> getAllBookings() {
        return carBookings;
    }

    @Override
    public boolean deleteBooking(UUID bookingId) {

        CarBooking car = carBookings.stream()
                .filter(carBooking -> carBooking.getId().equals(bookingId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Booking ID " + bookingId + " was not found"));

        car.setStatus(BookingStatus.CANCELLED);

        return true;
    }
}
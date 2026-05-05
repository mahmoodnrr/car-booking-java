package com.mahmoud.booking;

import java.util.ArrayList;
import java.util.List;
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


        return carBookings
                .stream()
                .filter(carBooking -> carBooking.getId().equals(bookingId))
                .findFirst()
                .map(car -> {
                    car.setStatus(BookingStatus.CANCELLED);
                    return true;
                })
                .orElse(false);
    }
}
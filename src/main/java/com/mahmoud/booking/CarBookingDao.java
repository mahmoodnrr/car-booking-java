package com.mahmoud.booking;

import java.util.UUID;

public interface CarBookingDao {

    void saveBooking(CarBooking carBooking);

    CarBooking[] getAllBookings();

    boolean deleteBooking(UUID bookingId);
}

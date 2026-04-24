package com.mahmoud.booking;

import java.util.List;
import java.util.UUID;

public interface CarBookingDao {

    void saveBooking(CarBooking carBooking);

    List<CarBooking> getAllBookings();

    boolean deleteBooking(UUID bookingId);
}

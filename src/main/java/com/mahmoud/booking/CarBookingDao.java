package com.mahmoud.booking;

import java.io.IOException;
import java.util.UUID;

public interface CarBookingDao {

    void saveBooking(CarBooking carBooking) throws IOException, ClassNotFoundException;

    CarBooking[] getAllBookings() throws IOException, ClassNotFoundException;

    boolean deleteBooking(UUID bookingId) throws IOException, ClassNotFoundException;
}

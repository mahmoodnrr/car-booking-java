package com.mahmoud.booking;

import java.util.Arrays;
import java.util.UUID;

public class CarBookingDao {

    private static CarBooking[] carBookings;
    private int availableSlot;
    private static final int CAPACITY = 2;
    static {
        carBookings = new CarBooking[CAPACITY];
    }

    public void saveBooking(CarBooking carBooking) {

        if (availableSlot + 1 >= CAPACITY) {
            carBookings = Arrays.copyOf(carBookings, carBookings.length * 2); // double the size
        }

            for (int i = 0; i < carBookings.length; i++) {
                if (carBookings[i] == null){
                    carBookings[i] = carBooking;
                    break;
                }
        }
    }

    public CarBooking[] getAllBookings() {

        int size = 0;

        for (CarBooking carBooking : carBookings) {
            if (carBooking != null) size++;
        }

        if (size == 0) return new CarBooking[]{};

        CarBooking[] bookings = new CarBooking[size];

        int index = 0;

        for (CarBooking carBooking : carBookings) {
            if (carBooking != null) {
                bookings[index++] = carBooking;
            }
        }

        return bookings;
    }

    public boolean deleteBooking(UUID bookingId) {

        for (CarBooking carBooking : carBookings) {
            if (carBooking != null && carBooking.getId().equals(bookingId)) {
                carBooking.setStatus(BookingStatus.CANCELLED);
                return true;
            }
        }
        return false;
    }
}
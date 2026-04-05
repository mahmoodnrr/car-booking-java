package com.mahmoud.booking;

import com.mahmoud.car.Car;
import com.mahmoud.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public class CarBookingDao {
    private static CarBooking[] carBookings;
    private int availableSlot;
    private static final int CAPACITY = 2;
    private boolean hasEmptySlot = false;
    static {
        carBookings = new CarBooking[CAPACITY];
    }

    public void saveBooking(UUID id, User user, Car car, LocalDateTime startDate, LocalDateTime endDate,
                            BigDecimal price) {
        if (availableSlot + 1 >= CAPACITY) {
            // Grow the database
            carBookings = Arrays.copyOf(carBookings, carBookings.length * 2); // double the size
        }

        var flag = false;

        if (carBookings.length != 0 && hasEmptySlot) {

            // Prioritise filling the indexes with null values
            for (int i = 0; i < carBookings.length; i++) {
                if (carBookings[i] == null){
                    carBookings[i] = new CarBooking(id, user, car, startDate, endDate, price,
                            BookingStatus.ACTIVE, LocalDateTime.now());
                    System.out.println("After: " + Arrays.toString(carBookings));
                    flag = true;
                    break;
                }
            }
        }

        if(!flag)  carBookings[availableSlot++] = new CarBooking(id, user, car, startDate, endDate, price,
                BookingStatus.ACTIVE, LocalDateTime.now());
    }

    public CarBooking[] getAllBookings() {

        int size = 0;

        for (CarBooking carBooking : carBookings) {
            if (carBooking != null) size++;
        }

        if (size == 0) return null;

        CarBooking[] bookings = new CarBooking[size];

        int index = 0;

        for (int i = 0; i < carBookings.length; i++) {
            if (carBookings[i] != null) {
                bookings[index++] = carBookings[i];
            }
        }

        return bookings;
    }

    public boolean deleteBooking(UUID bookingId) {

        for (int i = 0; i < carBookings.length; i++) {
            if(carBookings[i] != null && carBookings[i].getId().equals(bookingId)){
                carBookings[i] = null;
                hasEmptySlot = true;
                return true;
            }
        }
        return false;
    }
}
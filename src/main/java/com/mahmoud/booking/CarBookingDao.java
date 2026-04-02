package com.mahmoud.booking;

import com.mahmoud.car.Brand;
import com.mahmoud.car.Car;
import com.mahmoud.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class CarBookingDao {
    private static CarBooking[] carBookings;
    private static int availableSlot;
    private static final int CAPACITY = 4;

    public static class CarBookingDaoException extends RuntimeException {
        public CarBookingDaoException(String message) {
            super(message);
        }
    }

    static {
        carBookings = new CarBooking[CAPACITY];
        User u = new User(UUID.fromString("4a48a9cb-752c-4411-b462-9115674d6aed"), "Mahmoud");
        Car car = new Car(UUID.fromString("4a48a9cb-752c-4411-b462-9115674d6aed"), "reg 11", new BigDecimal(12), Brand.TESLA, true, false);
        carBookings[0] = new CarBooking(UUID.fromString("4a48a9cb-752c-4411-b462-9115674d6aed"), u, car, LocalDate.now(), LocalDate.now(),
                new BigDecimal(12), BookingStatus.ACTIVE, LocalDateTime.now());
    }

    public static void saveBooking(CarBooking carBooking) {
        if (availableSlot + 1 > CAPACITY)
            throw new CarBookingDaoException("Error: Cannot save car due to insufficient database space");
        carBookings[availableSlot++] = carBooking;

        System.out.println("Success!");
        System.out.println("Booking for " + carBooking.getUser() + ", chosen car" + carBooking.getCar() + " has been successfully completed.");
    }

    public static CarBooking getBookingById(UUID id) {

        for (CarBooking carBooking : carBookings) {
            if (carBookings != null && carBooking.getId().equals(id)) {
                return carBooking;
            }
        }

        throw new CarBookingDaoException("Error: Failed to book car.");
    }

    public static CarBooking[] getUserBookingById(UUID id) {

        var  userBookingsLength = 0;

        for (CarBooking carBooking : getAllBookings()) {
            if (carBooking.getId().equals(id)) userBookingsLength++;
        }

        CarBooking[] userCarBookings = new CarBooking[userBookingsLength];

        for (int i = 0; i < userCarBookings.length; i++) {
            if (carBookings[i].getId().equals(id)){
                userCarBookings[i] = carBookings[i];
            }
        }

        return userCarBookings;
    }

    public static CarBooking[] getAllBookings() {
//        User u = new User(UUID.fromString("4a48a9cb-752c-4411-b462-9115674d6aed"), "Mahmoud");
//        Car car = new Car(UUID.fromString("4a48a9cb-752c-4411-b462-9115674d6aed"), "reg 11", new BigDecimal(12), Brand.TESLA, true);
//        carBookings[0] = new CarBooking(UUID.fromString("4a48a9cb-752c-4411-b462-9115674d6aed"), u, car, LocalDate.now(), LocalDate.now(),
//                new BigDecimal(12), BookingStatus.ACTIVE, LocalDateTime.now());

        var validLength = 0;

        for (CarBooking carBooking : carBookings) {
            if (carBooking != null) validLength++;
        }

        CarBooking[] carBookingArr = new CarBooking[validLength];

        for (int i = 0; i < carBookings.length; i++) {

            if (carBookings[i] != null) carBookingArr[i] = carBookings[i];
        }

        return carBookingArr;
    }

    public static void deleteCarBooking(UUID bookingId) {

        var validArray = getAllBookings();

        for (int i = 0; i < validArray.length; i++) {
            if (carBookings[i].getId().equals(bookingId)) {
                carBookings[i] = null;
                System.out.println("Successfully deleted booking id: " + bookingId + "\n");
                return;
            }
        }

        System.out.println("No booking matches this ID: " + bookingId);
    }
}
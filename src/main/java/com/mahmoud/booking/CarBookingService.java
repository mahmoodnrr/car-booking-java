package com.mahmoud.booking;

import com.mahmoud.car.Car;
import com.mahmoud.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class CarBookingService {

    public static class CarBookingServiceException extends RuntimeException {
        public CarBookingServiceException(String message) {
            super(message);
        }
    }

    public CarBooking bookCar(User user, Car car, LocalDate startDate, LocalDate
            endDate) {

        if (!isCarAvailabileService(car)) throw new CarBookingServiceException("Car is not available.");

        // Calculate number of days between startDate and endDate
        var numberOfDays = ChronoUnit.DAYS.between(startDate, endDate);

        // Calculate price: car.getRentalPricePerDay() × numberOfDays
        BigDecimal price = new BigDecimal(numberOfDays).multiply(car.getRentalPricePerDay());


        // Save the booking via the DAO
        UUID id = UUID.randomUUID();
        saveCarBookingService(id, user, car, startDate, endDate, price, BookingStatus.ACTIVE, LocalDateTime.now());

        return CarBookingDao.getBookingById(id);
    }

    private boolean isCarAvailabileService(Car car) {

        for (CarBooking carBooking : CarBookingDao.getAllBookings()) {
            if (carBooking.getCar().getBrand().equals(car.getBrand())) {
                return false;
            }
        }
        return true;
    }

    public static void deleteCarBookingService(UUID booking){
         CarBookingDao.deleteCarBooking(booking);
    }

    public static CarBooking[] getUserBookingByIdService(UUID userId){
        return CarBookingDao.getUserBookingById(userId);
    }

    private void saveCarBookingService(UUID id, User user, Car car, LocalDate startDate, LocalDate endDate, BigDecimal price,
                                       BookingStatus status, LocalDateTime bookedAt){
        CarBookingDao.saveBooking(new CarBooking(id, user, car, startDate, endDate, price,
                status, bookedAt));
    }

    public static CarBooking[] viewAllBookingsService(){
        return CarBookingDao.getAllBookings();
    }
}

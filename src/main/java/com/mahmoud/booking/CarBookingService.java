package com.mahmoud.booking;

import com.mahmoud.car.Car;
import com.mahmoud.car.CarService;
import com.mahmoud.user.User;
import com.mahmoud.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class CarBookingService {

    private final CarBookingDao carBookingDao = new CarBookingDao();
    private final UserService userService = new UserService();
    private final CarService carService = new CarService();


    public CarBooking bookCar(UUID userId, UUID carId, LocalDateTime startDate, LocalDateTime endDate) {

        // Validate user ID
        User user = userService.getUserById(userId).orElseThrow( () -> new IllegalArgumentException("User not found: " + userId));

        // Validate car ID
        Car car = carService.getCarById(carId).orElseThrow(() -> new IllegalArgumentException ("Car not found: " + carId));

        // Validate dates
        if(!validateDates(startDate, endDate)) throw new IllegalArgumentException("Start date cannot be in the past " +
                "and end date must be after start date.");

        // Check if car is available on chosen dates
        if(!isCarAvailable(car.getId(), startDate)) {
            throw new IllegalStateException("Car " + carId + " is not available on the given dates.");
        }

        // Get number of days between startDate and endDate
        var numberOfDays = ChronoUnit.DAYS.between(startDate, endDate);

        // Calculate price
        BigDecimal price = new BigDecimal(numberOfDays).multiply(car.getRentalPricePerDay());

        CarBooking carBooking = new CarBooking(UUID.randomUUID(), user, car, startDate, endDate, price, BookingStatus.ACTIVE, LocalDateTime.now());

        // Save the booking
        carBookingDao.saveBooking(carBooking);

        return carBooking;
    }

    private boolean isCarAvailable(UUID carId, LocalDateTime startDate) {

        var bookings = carBookingDao.getAllBookings();

        for (CarBooking carBooking : bookings) {
            if (carBooking.getCar().getId().equals(carId)) {
                if((carBooking.getEndDate().isAfter(startDate)) ||
                        ((carBooking.getEndDate().isEqual(startDate)) ||
                            carBooking.getStatus().equals(BookingStatus.CANCELLED))) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean deleteBooking(UUID bookingId){
        return carBookingDao.deleteBooking(bookingId);
    }

    public CarBooking[] getUserBookingById(UUID userId){

        var size = 0;

        var bookings = carBookingDao.getAllBookings();

        for (CarBooking carBooking : bookings) {
            if (carBooking.getUser().getId().equals(userId)) size++;
        }

        CarBooking[] userBookings = new CarBooking[size];

        for (int i = 0; i < userBookings.length; i++) {
            if (bookings[i].getUser().getId().equals(userId)){
                userBookings[i] = bookings[i];
            }
        }

        return userBookings;
    }

    public CarBooking[] getAllBookings(){
        return carBookingDao.getAllBookings();
    }

    private boolean validateDates(LocalDateTime start, LocalDateTime end){

        LocalDate past = LocalDate.now().minusDays(1);

        LocalDate startDate = start.toLocalDate();
        LocalDate endDate = end.toLocalDate();

        return startDate.isBefore(endDate) && startDate.isAfter(past);
    }
}

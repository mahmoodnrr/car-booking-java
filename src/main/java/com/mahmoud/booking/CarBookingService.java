package com.mahmoud.booking;

import com.mahmoud.car.Car;
import com.mahmoud.car.CarService;
import com.mahmoud.user.User;
import com.mahmoud.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class CarBookingService {

    private CarBookingDao carBookingDao = new CarBookingDao();

    public CarBooking bookCar(UserService userService, CarService carService, Scanner scanner) {

        // Validate User Id
        Optional<User> user;

        do {
            System.out.println("Please enter user ID or type 'quit' to return to main menu");

            String input = scanner.nextLine();

            if(input.equalsIgnoreCase("quit")) return null;

            user = userService.getUserById(UUID.fromString(input));

        } while (user.isEmpty());

        // Validate Car
        Optional<Car> car;
        do {
            System.out.println("Please choose a car or type 'quit' to return to main menu");

            for (Car currentCar : carService.getAllCars()) {
                System.out.println("\nBrand " + currentCar.getBrand());
                System.out.println("ID " + currentCar.getId());
            }

            String input = scanner.nextLine();
            if(input.equalsIgnoreCase("quit")) return null;

            car = carService.getCarById(UUID.fromString(input));
        } while (car.isEmpty());

        // Validate rental start and end dates
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime startDate;
        LocalDateTime endDate;

        while (true) {
            try {
                System.out.println("Enter start date and time (dd-MM-yyyy HH:mm):");
                startDate = LocalDateTime.parse(scanner.nextLine(), dateTimeFormatter);

                System.out.println("Enter return date and time (dd-MM-yyyy HH:mm):");
                endDate = LocalDateTime.parse(scanner.nextLine(), dateTimeFormatter);

                if (validateDate(startDate, endDate)) break;

                System.out.println("Invalid Date!");

            } catch (IllegalArgumentException | DateTimeParseException e) {
                System.out.println("Invalid format! Please use dd-MM-yyyy HH:mm");
            }
        }

        // Check if car is available on chosen dates
        if(!isCarAvailableCheck(car.get().getId(), startDate)) {
            System.out.println("Checking car availability...");
            System.out.println("\nThis car is not available on the given date.");
            return null;
        }

        // Calculate number of days between startDate and endDate
        var numberOfDays = ChronoUnit.DAYS.between(startDate, endDate);

        // Calculate price
        BigDecimal price = new BigDecimal(numberOfDays).multiply(car.get().getRentalPricePerDay());

        // Save the booking via the DAO

        UUID id = UUID.randomUUID();
        carBookingDao.saveBooking(id, user.get(), car.get(), startDate, endDate, price);

        return getBookingById(id);
    }

    private boolean isCarAvailableCheck(UUID carId, LocalDateTime startDate) {

        var bookings = carBookingDao.getAllBookings();

        // Booking list is empty so this car is available
        if(bookings == null) return true;

        for (CarBooking carBooking : bookings) {
            if (carBooking.getCar().getId().equals(carId)) {
                if((carBooking.getEndDate().isAfter(startDate)) ||
                        (carBooking.getEndDate().isEqual(startDate))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void deleteBooking(UUID bookingId){
        // TODO- do we delete bookings or update status to cancelled
        // TODO- delete bookings from original array
        if (carBookingDao.getAllBookings() == null) {
            System.out.println("There are no bookings");
            return;
        }

        if (carBookingDao.deleteBooking(bookingId)) {
            System.out.println("Cancelled booking");
        } else {
            System.out.println("Invalid booking ID");
        }
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

    public CarBooking getBookingById(UUID bookingId) {
        var bookings = carBookingDao.getAllBookings();

        if (bookings.length == 0) {
            System.out.println("Car booking list is empty");
            return null;
        }

        for (CarBooking carBooking : bookings) {
            if (carBooking.getId().equals(bookingId)) {
                return carBooking;
            }
        }
            return null;
    }
}

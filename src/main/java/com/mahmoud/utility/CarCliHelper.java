package com.mahmoud.utility;

import com.mahmoud.booking.CarBooking;
import com.mahmoud.booking.CarBookingService;
import com.mahmoud.car.Car;
import com.mahmoud.car.CarService;
import com.mahmoud.user.User;
import com.mahmoud.user.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class CarCliHelper {

    public static void showMainMenu(){
        System.out.println(
                """
                        1 - Book Car
                        2 - Cancel booking
                        3 - View All User Booked Cars
                        4 - View All Bookings
                        5 - View Available Cars
                        6 - View Available Electric Cars
                        7 - View All Users
                        8 - Exit""");

        System.out.println("Please select an option");
    }

    public static void option1(UserService userService, CarService carService, CarBookingService carBookingService, Scanner scanner) {

        option7(userService);

        System.out.println("Please enter user ID");
        UUID userId = UUID.fromString(scanner.nextLine());

        option5(carService);
        System.out.println("Please choose a car ID");
        UUID carId = UUID.fromString(scanner.nextLine());

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        System.out.println("Enter start date and time (dd-MM-yyyy HH:mm)");
        LocalDateTime startDate = LocalDateTime.parse(scanner.nextLine(), dateTimeFormatter);

        System.out.println("Enter return date and time (dd-MM-yyyy HH:mm)");
        LocalDateTime endDate = LocalDateTime.parse(scanner.nextLine(), dateTimeFormatter);

        CarBooking carBooking = carBookingService.bookCar(userId, carId, startDate, endDate);

       if(carBooking != null) {
           System.out.println("\nBooking details");
           System.out.println(carBooking);
       }
       else {
           System.out.println("Error: Could not create this booking.");
       }
    }

    public static void option2(CarBookingService carBookingService, Scanner scanner) {
        // Delete Booking
        System.out.println("Please enter booking ID");

        var result = carBookingService.deleteBooking(UUID.fromString(scanner.nextLine()));

        if (result) {
            System.out.println("Cancelled booking");
        } else {
            System.out.println("Invalid booking ID");
        }

    }

    public static void option3(UserService userService, CarBookingService carBookingService, Scanner scanner) {
        // Validate User Id
        Optional<User> user;

        System.out.println("Please enter user ID");
        user = userService.getUserById(UUID.fromString(scanner.nextLine()));

        if(user.isEmpty()){
            System.out.println("User not found\n");
            return;
        }

        CarBooking[] carBookings = carBookingService.getUserBookingById(user.get().getId());
        if(carBookings.length == 0) {
            System.out.println("This user does not have any bookings\n");
            return;
        }

        System.out.println("Bookings for " + user.get().getName() + ":\n " + Arrays.toString(carBookings));
    }

    public static void option4(CarBookingService carBookingService) {

        var bookings =  carBookingService.getAllBookings();

        if(bookings.length == 0) {
            System.out.println("No bookings available");
            return;
        }

        System.out.println("Bookings List: \n");

        for(CarBooking carBookingList : bookings){
            System.out.println(carBookingList.toString());
            System.out.println();
        }
    }

    public static void option5(CarService carService) {
        System.out.println("View all available cars:\n");

        for(Car car : carService.getAllCars()){
                System.out.println(car);
                System.out.println();
        }
    }

    public static void option6(CarService carService) {
        System.out.println("View all electric cars:\n");

        for(Car car : carService.getAllCars()){
            if(car.isElectric()){
                System.out.println(car);
                System.out.println();
            }
        }
    }

    public static void option7(UserService userService){
        System.out.println("Users List: \n");
        System.out.println(Arrays.toString(userService.getAllUsers()));
    }

    public static void option8(Scanner scanner){
        System.out.println("Goodbye!");
        scanner.close();
    }
}

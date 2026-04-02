package com.mahmoud.utility;

import com.mahmoud.booking.CarBooking;
import com.mahmoud.booking.CarBookingService;
import com.mahmoud.car.Brand;
import com.mahmoud.car.Car;
import com.mahmoud.car.CarService;
import com.mahmoud.user.User;
import com.mahmoud.user.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class CarCliHelper {

    public static boolean validateDate(LocalDate startDate, LocalDate endDate){
        LocalDate past = LocalDate.now().minusDays(1);

        return startDate.isBefore(endDate) && startDate.isAfter(past);
    }

    public static void showMainMenu(){
        System.out.println(
                "1 - Book Car\n" +
                        "2 - Delete Car\n" +
                        "3 - View All User Booked Cars\n" +
                        "4 - View All Bookings\n" +
                        "5 - View Available Cars\n" +
                        "6 - View Available Electric Cars\n" +
                        "7 - View All Users\n" +
                        "8 - Exit");

        System.out.println("Please select an option");
    }

    public static void option1(UserService userService, CarService carService,
                               CarBookingService carBookingService, Scanner scanner) {
            // Validate User Id
            Optional<User> user;
            do {
                System.out.println("Please enter user ID");
                user = userService.getUserbyId(scanner.nextLine());
            } while (user.isEmpty());

            // Validate Car
            Optional<Car> car;
            do {
                System.out.println("Please choose a car");
                for (Brand brand : Brand.values()) {
                    System.out.println(" - " + brand);
                }
                car = carService.getCarByBrandService(scanner.nextLine());
            } while (car.isEmpty());

            // Validate rental start and end dates
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate startDate;
            LocalDate endDate;

            while (true) {
                try {
                    System.out.println("Please choose your rental START date.\nDate format dd-MM-yyyy");
                    //System.out.println("Start date must not be in the past and endDate must be after startDate");
                    startDate = LocalDate.parse(scanner.nextLine(), dateTimeFormatter);

                    System.out.println("Please choose your rental END date.\nDate format dd-MM-yyyy");
                    endDate = LocalDate.parse(scanner.nextLine(), dateTimeFormatter);

                    if (CarCliHelper.validateDate(startDate, endDate)) break;

                    System.out.println("Start date must not be in the past and endDate must be after startDate");

                } catch (IllegalArgumentException | DateTimeParseException e) {
                    System.out.println("\nInvalid Date\n");
                }
            }
            carBookingService.bookCar(user.get(), car.get(), startDate, endDate);
    }

    public static void option2(Scanner scanner) {
        // Delete Booking
        System.out.println("Please enter booking ID");
        UUID booking = UUID.fromString(scanner.nextLine());

        CarBookingService.deleteCarBookingService(booking);
    }

    public static void option3(UserService userService, Scanner scanner) {
        // Validate User Id
        Optional<User> user;

        System.out.println("Please enter user ID");
        user = userService.getUserbyId(scanner.nextLine());

        if(user.isEmpty()){
            System.out.println("User not found\n");
            return;
        }

        CarBooking[] carBookings = CarBookingService.getUserBookingByIdService(user.get().getId());

        if(carBookings.length == 0) {
            System.out.println("This user does not have any bookings\n");
            return;
        }

        System.out.println("Bookings List for " + user.get().getName() + ":\n " + Arrays.toString(carBookings));
    }

    public static void option4() {
        System.out.println("Bookings List: \n");

        for(CarBooking carBookingList : CarBookingService.viewAllBookingsService()){
            System.out.println(carBookingList.toString());
            System.out.println();
        }
    }

    public static void option5() {
        System.out.println("View all available cars:\n");

        for(Car car : CarService.viewAllCars()){
            if(car.isAvailable()){
                System.out.println(car);
                System.out.println();
            }
        }
    }

    public static void option6() {
        System.out.println("View all electric cars:\n");

        for(Car car : CarService.viewAllCars()){
            if(car.isElectric()){
                System.out.println(car);
                System.out.println();
            }
        }
    }

    public static void option7(){
        System.out.println("Users List: \n");

        System.out.println(Arrays.toString(UserService.viewAllUsersService()));
    }

    public static void option8(Scanner scanner){
        System.out.println("Goodbye!");
        scanner.close();
    }
}

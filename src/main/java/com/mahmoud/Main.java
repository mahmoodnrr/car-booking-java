package com.mahmoud;

import com.mahmoud.booking.CarBookingDao;
import com.mahmoud.booking.CarBookingFileDataAccessService;
import com.mahmoud.booking.CarBookingService;
import com.mahmoud.car.CarArrayDataAccessService;
import com.mahmoud.car.CarDao;
import com.mahmoud.car.CarService;
import com.mahmoud.user.UserArrayDataAccessService;
import com.mahmoud.user.UserDao;
import com.mahmoud.user.UserService;
import com.mahmoud.utility.CarCliHelper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        UserDao userDao = new UserArrayDataAccessService();
        CarDao carDao = new CarArrayDataAccessService();

        CarService carService = new CarService(carDao);
        UserService userService = new UserService(userDao);
        CarBookingDao carBookingDao = new CarBookingFileDataAccessService("carbookings.dat");

        CarBookingService carBookingService = new CarBookingService(carBookingDao, carService, userService);

        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {

            CarCliHelper.showMainMenu();

            try {
                int userSelectionInput;

                try {
                    userSelectionInput = scanner.nextInt();
                    scanner.nextLine();

                } catch (InputMismatchException e) {
                    System.out.println("Error: user did not select an option.");
                    break;
                }
                switch (userSelectionInput) {
                    case 1:
                        CarCliHelper.option1(userService, carService, carBookingService, scanner);
                        break;
                    case 2:
                        CarCliHelper.option2(carBookingService, scanner);
                        break;
                    case 3:
                        CarCliHelper.option3(userService, carBookingService, scanner);
                        break;
                    case 4:
                        CarCliHelper.option4(carBookingService);
                        break;
                    case 5:
                        CarCliHelper.option5(carService);
                        break;
                    case 6:
                        CarCliHelper.option6(carService);
                        break;
                    case 7:
                        CarCliHelper.option7(userService);
                        break;
                    case 8:
                        CarCliHelper.option8(scanner);
                        isRunning = false;
                        break;
                    default:
                        System.out.println("User did not select an option");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}


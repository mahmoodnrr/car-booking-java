package com.mahmoud;

import com.mahmoud.booking.CarBookingService;
import com.mahmoud.car.CarService;
import com.mahmoud.user.UserService;
import com.mahmoud.utility.CarCliHelper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        CarBookingService carBookingService = new CarBookingService();
        CarService carService = new CarService();
        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {

           CarCliHelper.showMainMenu();

            try {
                int userSelectionInput;

                // Store user's selection
                try {
                    userSelectionInput = scanner.nextInt();
                    scanner.nextLine();

                } catch (InputMismatchException e) {
                    System.out.println("Error: user did not select an option.");
                    break;
                }
                // Display Menu
                switch (userSelectionInput){
                    case 1:
                        CarCliHelper.option1(userService, carBookingService, scanner);
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


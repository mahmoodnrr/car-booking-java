package com.mahmoud;

import com.mahmoud.booking.CarBookingService;
import com.mahmoud.car.CarDao;
import com.mahmoud.car.CarService;
import com.mahmoud.user.UserDao;
import com.mahmoud.user.UserService;
import com.mahmoud.utility.CarCliHelper;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        CarBookingService carBookingService = new CarBookingService();
        CarService carService = new CarService(new CarDao());
        UserService userService = new UserService(new UserDao());

        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello there!");
        System.out.println();

        while (true) {

           CarCliHelper.showMainMenu();

            try {
                    int userSelectionInput = 0;

                    // Store user's selection
                    try {
                        userSelectionInput = scanner.nextInt();
                        scanner.nextLine();

                        // Display and handle selected option
                        switch (userSelectionInput){
                            case 1:
                                CarCliHelper.option1(userService, carService, carBookingService, scanner);
                                break;
                            case 2:
                                CarCliHelper.option2(scanner);
                                break;
                            case 3:
                                CarCliHelper.option3(userService, scanner);
                                break;
                            case 4:
                                CarCliHelper.option4();
                                break;
                            case 5:
                                CarCliHelper.option5();
                                break;
                            case 6:
                                CarCliHelper.option6();
                                break;
                            case 7:
                                CarCliHelper.option7();
                                break;
                            case 8:
                                CarCliHelper.option8(scanner);
                                break;
                            default:
                                System.out.println("User did not select an option");
                        }
                    } catch (CarDao.CarDAOException | UserDao.UserDAOException |
                             CarBookingService.CarBookingServiceException e) {
                        throw new RuntimeException(e);
                    } catch (InputMismatchException e) {
                        System.out.println("Error: Only number inputs are allowed.");
                        break;
                    }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}


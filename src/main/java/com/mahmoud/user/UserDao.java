package com.mahmoud.user;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class UserDao {

    private static User[] users;

    public static class UserDAOException extends RuntimeException{
        public UserDAOException(String message) {
            super(message);
        }
    }

    static {
        users = new User[]{
                new User(UUID.fromString("4a48a9cb-752c-4411-b462-9115674d6aed"), "Khalid"),
                new User(UUID.fromString("b225c1c8-eccd-4a65-85b0-a56ea901dd92"), "Layan")
        };

        System.out.println(Arrays.toString(users));
    }

    public static User[] getAllUsers(){

        var validLength = 0;

        for (User user : users) {
            if (user != null) validLength++;
        }

        User[] userArr = new User[validLength];

        for (int i = 0; i < users.length; i++) {

            if (users[i] != null) userArr[i] = users[i];
        }

        return userArr;
    }

    public static Optional<User> getUserById(UUID userId) {

        for (User user : users) {
            if (user.getId().equals(userId)) return Optional.of(user);
        }

        System.out.println("\nInvalid ID.\n");
        return Optional.empty();
    }
}

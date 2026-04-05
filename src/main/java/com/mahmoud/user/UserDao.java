package com.mahmoud.user;

import java.util.Arrays;
import java.util.UUID;

public class UserDao {

    private static User[] users;

    static {
        users = new User[]{
                new User(UUID.fromString("4a48a9cb-752c-4411-b462-9115674d6aed"), "Khalid"),
                new User(UUID.fromString("b225c1c8-eccd-4a65-85b0-a56ea901dd92"), "Layan")
        };

        System.out.println(Arrays.toString(users));
    }

    public User[] getAllUsers(){
        return users;
    }
}

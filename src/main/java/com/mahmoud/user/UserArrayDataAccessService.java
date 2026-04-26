package com.mahmoud.user;

import java.util.List;
import java.util.UUID;

public class UserArrayDataAccessService implements UserDao {

    private static final List<User> users;

    static {
        users = List.of(
                new User(UUID.fromString("4a48a9cb-752c-4411-b462-9115674d6aed"), "Khalid"),
                new User(UUID.fromString("b225c1c8-eccd-4a65-85b0-a56ea901dd92"), "Layan")
        );
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }
}

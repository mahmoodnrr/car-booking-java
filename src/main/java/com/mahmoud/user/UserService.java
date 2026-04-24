package com.mahmoud.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public Optional<User> getUserById(UUID userId) {

        var users = getAllUsers();

        for (User user : users) {
            if (user.getId().equals(userId)) return Optional.of(user);
        }

        return Optional.empty();
    }
}

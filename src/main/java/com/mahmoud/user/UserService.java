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
        return getAllUsers()
                .stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst();
    }
}

package com.mahmoud.user;

import java.util.Optional;
import java.util.UUID;

public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    public static User[] viewAllUsersService(){
        return UserDao.getAllUsers();
    }

    public static  Optional<User> getUserbyId(String userId){
        return UserDao.getUserById(UUID.fromString(userId));
    }
}

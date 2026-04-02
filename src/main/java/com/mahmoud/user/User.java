package com.mahmoud.user;

import java.util.UUID;

public class User {

    private UUID id;
    private String name;

    public User(){}

    public User(UUID uuid, String name) {
        this.id = uuid;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{ID=" + getId() + " name=" + getName() + "}";
    }

}

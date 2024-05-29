package com.user.user.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String name) {
        super(name);
    }
}

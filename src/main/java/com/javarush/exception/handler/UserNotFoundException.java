package com.javarush.exception.handler;

public class UserNotFoundException extends IllegalArgumentException{

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(Integer id) {
        super(String.format("User with Id %d not found", id));
    }

}

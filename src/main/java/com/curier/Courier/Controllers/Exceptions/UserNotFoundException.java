package com.curier.Courier.Controllers.Exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("Could not find order " + id);
    }
    public UserNotFoundException(String username) {
        super("Could not find order " + username);
    }

}
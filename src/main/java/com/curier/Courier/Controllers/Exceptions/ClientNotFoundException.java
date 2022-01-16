package com.curier.Courier.Controllers.Exceptions;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(Long id) {
        super("Could not find client " + id);
    }
    public ClientNotFoundException(String firstName) {
        super("Could not find client " + firstName);
    }
}
package com.curier.Courier.Controllers.Exceptions;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
        super("Could not find employee " + id);
    }
    public EmployeeNotFoundException(String username) {
        super("Could not find employee " + username);
    }
}
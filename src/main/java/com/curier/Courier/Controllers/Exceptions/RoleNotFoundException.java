package com.curier.Courier.Controllers.Exceptions;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(Long id) {
        super("Could not find role " + id);
    }
    public RoleNotFoundException(String name) {
        super("Could not find role " + name);
    }
}
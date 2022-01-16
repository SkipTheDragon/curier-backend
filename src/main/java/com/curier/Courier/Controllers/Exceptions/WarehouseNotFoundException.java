package com.curier.Courier.Controllers.Exceptions;

public class WarehouseNotFoundException extends RuntimeException {

    public WarehouseNotFoundException(Long id) {
        super("Could not find order " + id);
    }
    public WarehouseNotFoundException(String name) {
        super("Could not find order " + name);
    }

}
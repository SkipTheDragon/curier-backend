package com.curier.Courier.Controllers.Exceptions;

public class ShipmentNotFoundException extends RuntimeException {

    public ShipmentNotFoundException(Long id) {
        super("Could not find order " + id);
    }
    public ShipmentNotFoundException(String custom) {
        super("Could not find order " + custom);
    }
}
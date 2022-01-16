package com.curier.Courier.Controllers.Exceptions;

public class LocationNotFoundException extends RuntimeException {

    public LocationNotFoundException(Long id) {
        super("Could not find location " + id);
    }
    public LocationNotFoundException(Integer coordX, Integer coordY) {
        super("Could not find coordonates (x:" + coordX+ ";y:"+coordY+";");
    }
}
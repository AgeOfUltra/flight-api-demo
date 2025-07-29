package com.flight.api.exception;


public class FlightNotFoundException extends RuntimeException{


    public FlightNotFoundException(String message) {
        super(message);
    }
}

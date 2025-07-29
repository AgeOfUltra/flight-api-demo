package com.flight.api.exception;

import com.flight.api.utils.FlightErrorResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<?> handleFLightNotFoundException(FlightNotFoundException e){
        HttpStatusCode errorObject = HttpStatus.NOT_FOUND;
        FlightErrorResponseUtil error = new FlightErrorResponseUtil(LocalDateTime.now(),errorObject.toString(),e.getMessage());
        return new
                ResponseEntity<>((Object) error, errorObject);

    }
}

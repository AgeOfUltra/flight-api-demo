package com.flight.api.exception;

import com.flight.api.utils.FlightErrorResponseUtil;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<?> handleFLightNotFoundException(FlightNotFoundException e){
        FlightErrorResponseUtil error = new FlightErrorResponseUtil(LocalDateTime.now(),HttpStatus.NOT_FOUND.toString(),e.getMessage());
        return
                ResponseEntity.badRequest().body(error);

    }

    @ExceptionHandler(FlightFieldException.class)
    public ResponseEntity<?> handleWrongFlightTimeHandler(FlightFieldException e){
        FlightErrorResponseUtil error = new FlightErrorResponseUtil(LocalDateTime.now(),HttpStatus.BAD_REQUEST.toString(),e.getMessage());
        return  ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        });

        e.getBindingResult().getGlobalErrors().forEach(error -> {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        });

        FlightErrorResponseUtil error = new FlightErrorResponseUtil(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.toString(),
                errors.toString()
        );

        System.out.println("DTO Validation Error: " + e.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}

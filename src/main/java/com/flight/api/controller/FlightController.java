package com.flight.api.controller;

import com.flight.api.model.FlightDto;
import com.flight.api.service.IFlightService;
import com.flight.api.utils.FlightSuccessResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
public class FlightController {

    @Autowired
    IFlightService service;


    @GetMapping("/flights/getAllFlights")
    public ResponseEntity<List<FlightDto>> getAllFlights(){
        List<FlightDto> allFlights = service.getAllFlights();
        log.debug("Response {}", allFlights.toString());
        return ResponseEntity.ok().body(allFlights);
    }

    @PostMapping("/flights/addFlight")
    public ResponseEntity<?> addFlight(@Valid  @RequestBody FlightDto flightDto){
       String flightID =  service.saveFlight(flightDto);
       log.debug("Request {}",flightDto);
        FlightSuccessResponse response = new FlightSuccessResponse(LocalDateTime.now(),"Flight ID "+flightID+" is saved successfully");
       log.debug("Response {}", response);
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/flight/getFlightById/{flightID}")
    public ResponseEntity<FlightDto> getFlightById(@PathVariable int flightID){
        FlightDto flightDto = service.getFlightById(flightID);
        return  ResponseEntity.status(HttpStatus.OK).body(flightDto);
    }

    @PutMapping("/flight/updateFlight")
    public ResponseEntity<?> updateFlight(@Valid @RequestBody  FlightDto flightDto){
        String result = service.updateFlight(flightDto);
        FlightSuccessResponse response = new FlightSuccessResponse(LocalDateTime.now(),result);
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/flight/deleteFlight/{flightID}")
    public ResponseEntity<?> deleteFlight(@PathVariable int flightID){
        String result = service.deleteFlight(flightID);
        FlightSuccessResponse response = new FlightSuccessResponse(LocalDateTime.now(),result+" for flight ID "+flightID);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
/* future implementation
* package com.flight.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flight.api.model.FlightDto;
import com.flight.api.service.IFlightService;
import com.flight.api.utils.FlightSuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
public class FlightController {

    @Autowired
    IFlightService service;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/flights/getAllFlights")
    public ResponseEntity<List<FlightDto>> getAllFlights(HttpServletRequest request) {
        try {
            // Log request
            log.debug("GET Request - URI: {}, Method: {}", request.getRequestURI(), request.getMethod());

            List<FlightDto> allFlights = service.getAllFlights();

            // Log response
            String responseJson = objectMapper.writeValueAsString(allFlights);
            log.debug("GET Response - Status: {}, Body: {}", HttpStatus.OK.value(), responseJson);

            return ResponseEntity.ok().body(allFlights);
        } catch (Exception e) {
            log.error("Error in getAllFlights: ", e);
            throw e;
        }
    }

    @PostMapping("/flights/addFlight")
    public ResponseEntity<?> addFlight(@Valid @RequestBody FlightDto flightDto, HttpServletRequest request) {
        try {
            // Log request
            String requestJson = objectMapper.writeValueAsString(flightDto);
            log.debug("POST Request - URI: {}, Method: {}, Body: {}",
                     request.getRequestURI(), request.getMethod(), requestJson);

            String flightID = service.saveFlight(flightDto);
            FlightSuccessResponse response = new FlightSuccessResponse(
                LocalDateTime.now(), "Flight ID " + flightID + " is saved successfully");

            // Log response
            String responseJson = objectMapper.writeValueAsString(response);
            log.debug("POST Response - Status: {}, Body: {}", HttpStatus.CREATED.value(), responseJson);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error in addFlight: ", e);
            throw e;
        }
    }

    @GetMapping("/flight/getFlightById/{flightID}")
    public ResponseEntity<FlightDto> getFlightById(@PathVariable int flightID, HttpServletRequest request) {
        try {
            // Log request
            log.debug("GET Request - URI: {}, Method: {}, PathVariable: flightID={}",
                     request.getRequestURI(), request.getMethod(), flightID);

            FlightDto flightDto = service.getFlightById(flightID);

            // Log response
            String responseJson = objectMapper.writeValueAsString(flightDto);
            log.debug("GET Response - Status: {}, Body: {}", HttpStatus.OK.value(), responseJson);

            return ResponseEntity.status(HttpStatus.OK).body(flightDto);
        } catch (Exception e) {
            log.error("Error in getFlightById for flightID {}: ", flightID, e);
            throw e;
        }
    }

    @PutMapping("/flight/updateFlight")
    public ResponseEntity<?> updateFlight(@Valid @RequestBody FlightDto flightDto, HttpServletRequest request) {
        try {
            // Log request
            String requestJson = objectMapper.writeValueAsString(flightDto);
            log.debug("PUT Request - URI: {}, Method: {}, Body: {}",
                     request.getRequestURI(), request.getMethod(), requestJson);

            String result = service.updateFlight(flightDto);
            FlightSuccessResponse response = new FlightSuccessResponse(LocalDateTime.now(), result);

            // Log response
            String responseJson = objectMapper.writeValueAsString(response);
            log.debug("PUT Response - Status: {}, Body: {}", HttpStatus.OK.value(), responseJson);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error("Error in updateFlight: ", e);
            throw e;
        }
    }

    @DeleteMapping("/flight/deleteFlight/{flightID}")
    public ResponseEntity<?> deleteFlight(@PathVariable int flightID, HttpServletRequest request) {
        try {
            // Log request
            log.debug("DELETE Request - URI: {}, Method: {}, PathVariable: flightID={}",
                     request.getRequestURI(), request.getMethod(), flightID);

            String result = service.deleteFlight(flightID);
            FlightSuccessResponse response = new FlightSuccessResponse(
                LocalDateTime.now(), result + " for flight ID " + flightID);

            // Log response
            String responseJson = objectMapper.writeValueAsString(response);
            log.debug("DELETE Response - Status: {}, Body: {}", HttpStatus.OK.value(), responseJson);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error("Error in deleteFlight for flightID {}: ", flightID, e);
            throw e;
        }
    }
}
*
* */
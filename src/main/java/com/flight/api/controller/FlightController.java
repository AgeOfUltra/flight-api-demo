package com.flight.api.controller;

import com.flight.api.model.FlightDto;
import com.flight.api.service.IFlightService;
import com.flight.api.utils.FlightSuccessResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class FlightController {

    @Autowired
    IFlightService service;


    @GetMapping("/flights/getAllFlights")
    public ResponseEntity<List<FlightDto>> getAllFlights(){
        List<FlightDto> allFlights = service.getAllFlights();
        return ResponseEntity.ok().body(allFlights);
    }

    @PostMapping("/flights/addFlight")
    public ResponseEntity<?> addFlight(@Valid  @RequestBody FlightDto flightDto){
       String flightID =  service.saveFlight(flightDto);
        FlightSuccessResponse response = new FlightSuccessResponse(LocalDateTime.now(),"Flight ID "+flightID+" is saved successfully");
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

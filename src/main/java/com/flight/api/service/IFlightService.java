package com.flight.api.service;


import com.flight.api.model.FlightDto;

import java.util.List;
import java.util.Optional;

public interface IFlightService {
    String saveFlight(FlightDto flightDto);
    String updateFlight(FlightDto flightDto);
    String deleteFlight(int flightID);
    List<FlightDto> getAllFlights();
    FlightDto getFlightById(int flightID);
}

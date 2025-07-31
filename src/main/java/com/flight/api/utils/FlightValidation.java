package com.flight.api.utils;

import com.flight.api.model.FlightDto;

public class FlightValidation {

    // as we have used the default validation dependency removed this part of code.
//    public boolean allFieldsPassed(FlightDto flightDto) {
//        return false;
//        return Integer.valueOf(flightDto.getCapacity()) != null &&
//                flightDto.getModel() != null && flightDto.getOrigin() != null &&
//                flightDto.getDestination() != null && flightDto.getArrivalDate() != null &&
//                flightDto.getDepartureDate() != null;
//    }

    public boolean departureDateValidate(FlightDto flightDto) {
        return flightDto.getDepartureDate().isAfter(flightDto.getArrivalDate());
    }
}
//flightDto.getFlightName() != null &&
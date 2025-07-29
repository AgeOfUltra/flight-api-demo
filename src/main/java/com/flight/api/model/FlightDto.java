package com.flight.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDto {
    int flightID;
    String flightName;
    String model;
    String departureDate;
    String origin;
    String destination;
    int capacity;
}

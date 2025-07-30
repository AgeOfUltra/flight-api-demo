package com.flight.api.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDto {

    private int flightID;
    @NotNull(message = "Flight name cannot be null")
    @NotBlank(message = "Flight name cannot be empty")
    private String flightName;

    @NotNull(message = "model is required")
    @NotBlank(message = "model is required")
    private String model;

    @NotNull(message = "Origin is required")
    @NotBlank(message = "Origin is required")
    private String origin;

    @NotNull(message = "Destination date is required")
    @NotBlank(message = "Destination date is required")
    private String destination;

    @NotNull(message = "Departure date is required")
    private LocalDateTime departureDate;

    @NotNull(message = "Arrival date is required")
    private LocalDateTime arrivalDate;

    @Min(value = 1,message = "Capacity must be at least 1")
    private int capacity;


    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}

package com.flight.api.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FlightSuccessResponse {
    LocalDateTime dateTime;
    String responseMessage;
}

package com.flight.api.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightErrorResponseUtil {
    LocalDateTime dateTime;
    String errorCode;
    String errorDetails;
}

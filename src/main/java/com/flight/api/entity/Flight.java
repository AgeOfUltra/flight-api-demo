package com.flight.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int flightID;
    @Column(nullable = false)
    private String flightName;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private String origin;
    @Column(nullable = false)
    private String destination;
    @Column(nullable = false)
    private LocalDateTime departureDate;
    @Column(nullable = false)
    private LocalDateTime arrivalDate;
    @Column(nullable = false)
    private int capacity;
    @Column(nullable = false)
    private LocalDateTime createdDate;
    @Column(nullable = false)
    private LocalDateTime modifiedDate;
}

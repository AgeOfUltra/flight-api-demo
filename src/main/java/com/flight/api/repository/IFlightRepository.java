package com.flight.api.repository;

import com.flight.api.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IFlightRepository extends JpaRepository<Flight,Integer> {

//    @Modifying
//    @Query("delete from Flight f where f.flightID = :flightID")
//    void deleteFlightByFlightID(@Param("flightID") int  flightID);
}

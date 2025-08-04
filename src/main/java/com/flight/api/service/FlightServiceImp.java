package com.flight.api.service;

import com.flight.api.entity.Flight;
import com.flight.api.exception.FlightNotFoundException;
import com.flight.api.exception.FlightFieldException;
import com.flight.api.aspect.SyncFlightData;
import com.flight.api.model.FlightDto;
import com.flight.api.repository.IFlightRepository;
import com.flight.api.utils.FlightValidation;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FlightServiceImp implements IFlightService{

    boolean isUpdatedValue = false;

    @Autowired
    IFlightRepository flightRepository;

    @Autowired
    ModelMapper modelMapper;

    static  List<Flight> allFlights;
    private FlightValidation validation;

    @PostConstruct
    private void init(){
        this.validation = new FlightValidation();
        syncDbAndLocal();
        isUpdatedValue= true;
    }


    @Override
//    @Transactional
    @SyncFlightData()
    public String saveFlight(FlightDto flightDto) {
        if(validation.departureDateValidate(flightDto)){
            throw new FlightFieldException("Arrival date cannot be before departure date");
        }
//        if(validation.allFieldsPassed(flightDto)){
//            throw new FlightFieldException("Please pass all fields");
//        }
        Flight flight = modelMapper.map(flightDto, Flight.class);
        flight.setCreatedDate(LocalDateTime.now());
        flight.setModifiedDate(LocalDateTime.now());
        Flight flightInfo = flightRepository.save(flight);
        isUpdatedValue=false;
        return String.valueOf(flightInfo.getFlightID());
    }

    @Override
//    @Transactional
    @SyncFlightData()
    public String updateFlight(FlightDto flightDto) {
        Flight savedFlight;

        if(validation.departureDateValidate(flightDto)){
            throw new FlightFieldException("Arrival date cannot be before departure date");
        }

        //because we are passing a flight as a new object.
        Optional<Flight> availableFlight = allFlights.stream().filter(flight -> flight.getFlightID()==flightDto.getFlightID()).findFirst();
        if(availableFlight.isPresent()){
            System.out.println("flight value at the service layer before updating the value = "+flightDto);
            flightDto.setCreatedDate(availableFlight.get().getCreatedDate());
            flightDto.setModifiedDate(LocalDateTime.now());
             Flight flight = modelMapper.map(flightDto,Flight.class);
             savedFlight = flightRepository.save(flight);
             isUpdatedValue=false;
             return "Existing flight is updated which has flight Name "+savedFlight.getFlightName();
        }else{
            throw new FlightNotFoundException("No matching flight found!!");
        }

    }

    @Override
//    @Transactional
    @SyncFlightData()
    public String deleteFlight(int flightID) {
        boolean isFlightAvail =  allFlights.stream().anyMatch(flight->flight.getFlightID()==flightID);
       if(!isFlightAvail){
           throw new FlightNotFoundException( "Delete Operation failed");
       }else{
           flightRepository.deleteById(flightID);
           isUpdatedValue=false;
           return "Deleted Successfully";
       }
    }


    @Override
    @SyncFlightData()
    public List<FlightDto> getAllFlights() {
        return allFlights.stream().map(a -> modelMapper.map(a,FlightDto.class)).collect(Collectors.toList());
    }

    @Override
    @SyncFlightData()
    public FlightDto getFlightById(int flightID) {
        Optional<Flight> currentFlight = allFlights.stream()
                .filter(a -> a.getFlightID() == flightID)
                .findFirst();

        if (currentFlight.isPresent()) {
            return modelMapper.map(currentFlight.get(), FlightDto.class);
        } else {
            throw new FlightNotFoundException("No matching flight found!!");
        }
    }


    public void syncDbAndLocal(){
        if(!isUpdatedValue){
            if (allFlights != null) {
                allFlights.clear();
            }
            allFlights = flightRepository.findAll();
            isUpdatedValue= true;
        }
    }
}

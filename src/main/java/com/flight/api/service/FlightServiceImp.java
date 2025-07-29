package com.flight.api.service;

import com.flight.api.entity.Flight;
import com.flight.api.exception.FlightNotFoundException;
import com.flight.api.logger.SyncFlightData;
import com.flight.api.model.FlightDto;
import com.flight.api.repository.IFlightRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightServiceImp implements IFlightService{

    boolean isUpdatedValue = false;

    @Autowired
    IFlightRepository flightRepository;

    @Autowired
    ModelMapper modelMapper;

    static  List<Flight> allFlights;

    @PostConstruct
    private void init(){
        syncDbAndLocal();
        isUpdatedValue= true;
    }


    @Override
    @Transactional
    @SyncFlightData(action = "CREATE")
    public String saveFlight(FlightDto flightDto) {
        Flight flight = modelMapper.map(flightDto, Flight.class);
        Flight flightInfo = flightRepository.save(flight);
        isUpdatedValue=false;
        return String.valueOf(flightInfo.getFlightID());
    }

    @Override
    @Transactional
//    @SyncFlightData(action = "UPDATE")
    public String updateFlight(FlightDto flightDto) {
        Flight savedFlight;
        Flight flight = modelMapper.map(flightDto,Flight.class);
        Optional<FlightDto> flightInfo = Optional.ofNullable(modelMapper.map(allFlights.stream().filter(a -> a.getFlightID() == flightDto.getFlightID()).findFirst(), FlightDto.class));
        if(flightInfo.isPresent()){
             savedFlight = flightRepository.save(flight);
             isUpdatedValue=false;
             return "Existing flight is updated with flight Name "+savedFlight.getFlightName();
        }else{
            throw new FlightNotFoundException("No matching flight found!!");
        }

    }

    @Override
    @Transactional
//    @SyncFlightData(action = "DELETE")
    public String deleteFlight(int flightID) {
        Optional<Flight> flight =  allFlights.stream().filter(a -> a.getFlightID()==flightID).findFirst();
       if(flight.isEmpty()){
           throw new FlightNotFoundException( "Delete Operation failed");
       }else{
           flightRepository.deleteFlightByFlightID(flightID);
           isUpdatedValue=false;
           return "Deleted Successfully";
       }
    }


    @Override
    @SyncFlightData(action = "FETCH")
    public List<FlightDto> getAllFlights() {
        return allFlights.stream().map(a -> modelMapper.map(a,FlightDto.class)).collect(Collectors.toList());
    }

    @Override
    public FlightDto getFlightById(int flightID) {
        if (!isUpdatedValue) {
            syncDbAndLocal();
        }
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

package com.flight.api.logger;


import com.flight.api.service.FlightServiceImp;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DBSyncAspect {

    @Autowired
    public FlightServiceImp flightServiceImp;

    @Before("@annotation(SyncFlightData)")
    public void dbSync(){
        flightServiceImp.syncDbAndLocal();
    }

}

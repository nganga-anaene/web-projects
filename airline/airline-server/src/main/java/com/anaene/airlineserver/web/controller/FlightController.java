package com.anaene.airlineserver.web.controller;

import com.anaene.airlineserver.data.entity.Flight;
import com.anaene.airlineserver.web.controller.util.FlightDetail;
import com.anaene.airlineserver.web.service.FlightService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService){
        this.flightService = flightService;
    }

    @GetMapping(value = "", params = {"page", "size"})
    public Page<Flight> getFlights(@RequestBody FlightDetail flightDetail, @RequestParam("page") int page, @RequestParam("size") int size) {
        System.out.println(flightDetail);
        return flightService.getFlightByDay(flightDetail, page, size);
    }
}

package com.anaene.airlineserver.web.controller;

import com.anaene.airlineserver.data.entity.Flight;
import com.anaene.airlineserver.web.service.FlightService;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("airports/{airportId}")
public class AirportFlightsController {

    private final FlightService flightService;

    AirportFlightsController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping(value = "/arrivals")
    public ResponseEntity<PagedResources<Resource<Flight>>> getAirportArrivalFlightsPage
            (@PathVariable long airportId,
             @RequestParam(name = "page", required = false, defaultValue = "0") int page,
             @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        try {
            PagedResources<Resource<Flight>> flightsPagedResource = flightService.getAirportFlightsArrivalPage(airportId, page, size);
            return ResponseEntity.ok(flightsPagedResource);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/arrivals/{flightId}")
    public ResponseEntity<Resource<Flight>> getArrivalFlight(@PathVariable long airportId, @PathVariable long flightId) {
        try {
            Resource<Flight> resource = flightService.getArrivalFlightResource(airportId, flightId);
            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/departures")
    public ResponseEntity<PagedResources<Resource<Flight>>> getAirportDepartureFlightsPage
            (@PathVariable long airportId,
             @RequestParam(name = "page", required = false, defaultValue = "0") int page,
             @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        try {
            PagedResources<Resource<Flight>> flightsPagedResource = flightService.getAirportFlightsArrivalPage(airportId, page, size);
            return ResponseEntity.ok(flightsPagedResource);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/departures/{flightId}")
    public ResponseEntity<Resource<Flight>> findDepartureFlight(@PathVariable long airportId, @PathVariable long flightId) {
        try {
            Resource<Flight> resource = flightService.getDepartureFlightResource(airportId, flightId);
            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

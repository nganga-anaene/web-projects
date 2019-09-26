package com.anaene.airlineserver.web.controller;

import com.anaene.airlineserver.data.entity.Flight;
import com.anaene.airlineserver.web.service.FlightService;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("flights")
public class AirportFlightsController {

    private final FlightService flightService;

    AirportFlightsController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/departure/{airportId}")
    public ResponseEntity<PagedResources<Resource<Flight>>> getDepartingFlights(
            @PathVariable("airportId") long airportId,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        try {
            return ResponseEntity.ok(flightService.getDepartingFlights(airportId, page, size));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/arrival/{airportId}")
    public ResponseEntity<PagedResources<Resource<Flight>>> getArrivalFlights(
            @PathVariable("airportId") long airportId,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        try {
            return ResponseEntity.ok(flightService.getArrivalFlights(airportId, page, size));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/departure/{departureAirportId}/arrival/{arrivalAirportId}")
    public ResponseEntity<PagedResources<Resource<Flight>>> getAirportFlights(
            @PathVariable("departureAirportId") long departureAirportId, @PathVariable("arrivalAirportId") long arrivalAirportId,
            @RequestParam(name = "flight-date") String date,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        try {
            return ResponseEntity.ok(flightService.getFlightByDepartureAndArrivalAirports(departureAirportId, arrivalAirportId, date, page, size));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<Resource<Flight>> getFlightById(@PathVariable("flightId") long flightId) {
        try {
            return ResponseEntity.ok(flightService.getFlightById(flightId));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

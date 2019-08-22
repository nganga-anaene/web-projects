package com.anaene.airlineserver.web.controller;

import com.anaene.airlineserver.data.entity.Airport;
import com.anaene.airlineserver.web.service.AirportService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("airports")
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping("")
    public ResponseEntity<Resources<Resource<Airport>>> getAirports() {
        try {
            return ResponseEntity.ok(airportService.getAllAirports());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<Airport>> getAirportById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(airportService.getAirportResource(id));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

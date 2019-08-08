package com.anaene.airlineserver.web.controller;

import com.anaene.airlineserver.data.entity.Airport;
import com.anaene.airlineserver.web.service.AirportService;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("airports")
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping("airports/{id}")
    public ResponseEntity<Resource<Airport>> getAirportById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(airportService.getAirportResource(id));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

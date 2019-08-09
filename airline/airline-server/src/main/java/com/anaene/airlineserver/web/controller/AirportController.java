package com.anaene.airlineserver.web.controller;

import com.anaene.airlineserver.data.entity.Airport;
import com.anaene.airlineserver.web.service.AirportService;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
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

    @GetMapping("/{id}")
    public ResponseEntity<Resource<Airport>> getAirportById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(airportService.getAirportResource(id));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "", params = {"page", "size"})
    public ResponseEntity<PagedResources<Resource<Airport>>> getAirportsPage(@RequestParam("page") int page, @RequestParam("size") int size, PagedResourcesAssembler<Airport> pagedResourcesAssembler) {
        try {
            PagedResources<Resource<Airport>> pagedResources = airportService.getAirportsResourcePage(page, size, pagedResourcesAssembler);
            return ResponseEntity.ok(pagedResources);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

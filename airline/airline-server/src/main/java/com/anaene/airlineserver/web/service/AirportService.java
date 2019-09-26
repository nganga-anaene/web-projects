package com.anaene.airlineserver.web.service;

import com.anaene.airlineserver.data.entity.Airport;
import com.anaene.airlineserver.data.repository.AirportRepository;
import com.anaene.airlineserver.web.controller.AirportFlightsController;
import com.anaene.airlineserver.web.resource.AirportResourceAssembler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class AirportService {

    private final AirportRepository airportRepository;
    private AirportResourceAssembler airportResourceAssembler;

    public AirportService(AirportRepository airportRepository, AirportResourceAssembler airportResourceAssembler) {
        this.airportRepository = airportRepository;
        this.airportResourceAssembler = airportResourceAssembler;
    }


    public Resources<Resource<Airport>> getAllAirports() {
        return new Resources<>(airportRepository.findAll().stream().map(airportResourceAssembler::toResource).collect(Collectors.toList()));
    }

    public Page<Airport> getAirportsPage(int page, int size) {
        return airportRepository.findAll(PageRequest.of(page, size));
    }

    public Resource<Airport> getAirportResource(long id) {
        Airport airport = getAirport(id);
        Resource<Airport> resource = airportResourceAssembler.toResource(airport);
        resource.add(linkTo(methodOn(AirportFlightsController.class).getDepartingFlights(id, 0, 10)).withRel("arrival-flights"));
        resource.add(linkTo(methodOn(AirportFlightsController.class).getArrivalFlights(id, 0, 10)).withRel("departure-flights"));
        return resource;
    }

    @Transactional
    Airport getAirport(long id) {
        return airportRepository.findById(id).orElseThrow();
    }
}

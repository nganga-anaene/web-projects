package com.anaene.airlineserver.web.service;

import com.anaene.airlineserver.data.entity.Airport;
import com.anaene.airlineserver.data.repository.AirportRepository;
import com.anaene.airlineserver.web.controller.AirportFlightsController;
import com.anaene.airlineserver.web.resource.AirportResourceAssembler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    public PagedResources<Resource<Airport>> getAirportsResourcePage(int page, int size, PagedResourcesAssembler<Airport> pagedResourcesAssembler) {
        Page<Airport> airportPage = getAirportsPage(page, size);
        return pagedResourcesAssembler.toResource(airportPage, airportResourceAssembler);
    }

    public Page<Airport> getAirportsPage(int page, int size) {
        return airportRepository.findAll(PageRequest.of(page, size));
    }

    public Resource<Airport> getAirportResource(long id) {
        Airport airport = getAirport(id);
        Resource<Airport> resource = airportResourceAssembler.toResource(airport);
        resource.add(linkTo(methodOn(AirportFlightsController.class).getAirportArrivalFlightsPage(id, 0, 10)).withRel("arrival-flights"));
        resource.add(linkTo(methodOn(AirportFlightsController.class).getAirportDepartureFlightsPage(id, 0, 10)).withRel("departure-flights"));
        return resource;
    }

    @Transactional
    Airport getAirport(long id) {
        return airportRepository.findById(id).orElseThrow();
    }
}

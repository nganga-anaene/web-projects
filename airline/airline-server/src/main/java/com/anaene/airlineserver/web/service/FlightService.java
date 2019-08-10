package com.anaene.airlineserver.web.service;

import com.anaene.airlineserver.data.entity.Airport;
import com.anaene.airlineserver.data.entity.Flight;
import com.anaene.airlineserver.data.repository.AirportRepository;
import com.anaene.airlineserver.data.repository.FlightRepository;
import com.anaene.airlineserver.web.controller.AirportController;
import com.anaene.airlineserver.web.controller.AirportFlightsController;
import com.anaene.airlineserver.web.resource.FlightResourceAssembler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;
    private FlightResourceAssembler flightResourceAssembler;

    public FlightService(FlightRepository flightRepository, AirportRepository airportRepository, FlightResourceAssembler flightResourceAssembler) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
        this.flightResourceAssembler = flightResourceAssembler;
    }

    public PagedResources<Resource<Flight>> getAirportFlightsArrivalPage(long airportId, int page, int size) {
        Airport airport = airportRepository.findById(airportId).orElseThrow();
        Page<Flight> flightPage = flightRepository.findByArrivalAirport(airport, PageRequest.of(page, size));
        PagedResourcesAssembler<Flight> pagedAssembler = new PagedResourcesAssembler<>(null, null);
        PagedResources<Resource<Flight>> resources = pagedAssembler.toResource(flightPage, flightResourceAssembler);
        resources.add(linkTo(methodOn(AirportController.class).getAirportById(airportId)).withRel("arrival-airport"));
        return resources;
    }

    public PagedResources<Resource<Flight>> getAirportFlightsDeparturePage(long airportId, int page, int size) {
        Airport airport = airportRepository.findById(airportId).orElseThrow();
        Page<Flight> flightPage = flightRepository.findByDepartingAirport(airport, PageRequest.of(page, size));
        PagedResourcesAssembler<Flight> pagedAssembler = new PagedResourcesAssembler<>(null, null);
        PagedResources<Resource<Flight>> resources = pagedAssembler.toResource(flightPage, flightResourceAssembler);
        resources.add(linkTo(methodOn(AirportController.class).getAirportById(airportId)).withRel("departure-airport"));
        return resources;
    }

    @Transactional
    public Resource<Flight> getArrivalFlightResource(long airportId, long flightId) {
        Flight flight = flightRepository.findById(flightId).orElseThrow();
        Resource<Flight> resource = flightResourceAssembler.toResource(flight);
        resource.add(linkTo(methodOn(AirportFlightsController.class).getArrivalFlight(airportId, flightId)).withSelfRel());
        resource.add(linkTo(methodOn(AirportController.class).getAirportById(flight.getArrivalAirport().getId())).withRel("arrival-airport"));
        return resource;
    }

    @Transactional
    public Resource<Flight> getDepartureFlightResource(long airportId, long flightId) {
        Flight flight = flightRepository.findById(flightId).orElseThrow();
        Resource<Flight> resource = flightResourceAssembler.toResource(flight);
        resource.add(linkTo(methodOn(AirportFlightsController.class).findDepartureFlight(airportId, flightId)).withSelfRel());
        resource.add(linkTo(methodOn(AirportController.class).getAirportById(flight.getArrivalAirport().getId())).withRel("departure-airport"));
        return resource;
    }

    @Transactional
    public List<Flight> getEmptyFlights() {
        return flightRepository.findAll().stream().filter(flight -> flight.getBookings().size() < flight.getMaxBookings()).collect(Collectors.toList());
    }
}

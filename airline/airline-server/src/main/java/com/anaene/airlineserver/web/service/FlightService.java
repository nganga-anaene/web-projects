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
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Transactional
    public PagedResources<Resource<Flight>> getFlightByDepartureAndArrivalAirports(long departureAirportId, long arrivalAirportId, String date, int page, int size) {
        Airport departureAirport = airportRepository.findById(departureAirportId).orElseThrow();
        Airport arrivalAirport = airportRepository.findById(arrivalAirportId).orElseThrow();
        LocalDateTime localDateTime = parseDate(date);
        Page<Flight> flightPage = flightRepository.findByDepartingAirportAndArrivalAirportAndDepartureTimeAfter(departureAirport, arrivalAirport, localDateTime, PageRequest.of(page, size));
        PagedResourcesAssembler<Flight> pagedResourcesAssembler = new PagedResourcesAssembler<>(null, null);
        PagedResources<Resource<Flight>> resources = pagedResourcesAssembler.toResource(flightPage, flightResourceAssembler);
        resources.add(linkTo(methodOn(AirportController.class).getAirportById(departureAirportId)).withRel("departing-airport"));
        resources.add(linkTo(methodOn(AirportController.class).getAirportById(arrivalAirportId)).withRel("arrival-airport"));
        return resources;
    }

    private LocalDateTime parseDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        if (localDate.atStartOfDay().isEqual(LocalDateTime.now().toLocalDate().atStartOfDay())) {
            return LocalDateTime.now();
        }
        return localDate.atStartOfDay();
    }

    @Transactional
    public List<Flight> getEmptyFlights() {
        return flightRepository.findAll().stream().filter(flight -> flight.getBookings().size() < flight.getMaxBookings()).collect(Collectors.toList());
    }

    @Transactional
    public List<Flight> getEmptyFlights(Airport departureAirport, Airport arrivalAirport, LocalDateTime departureDate) {
        return flightRepository.findByDepartingAirportAndArrivalAirportAndDepartureTimeAfter(departureAirport, arrivalAirport, departureDate)
                .stream().filter(f -> f.getMaxBookings() > f.getBookings().size()).collect(Collectors.toList());
    }

    @Transactional
    public Resource<Flight> getFlightById(long flightId) {
        Flight flight = flightRepository.findById(flightId).orElseThrow();
        Resource<Flight> resource = flightResourceAssembler.toResource(flight);
        resource.add(linkTo(methodOn(AirportController.class).getAirportById(flight.getDepartingAirport().getId())).withRel("departure-airport"));
        resource.add(linkTo(methodOn(AirportController.class).getAirportById(flight.getArrivalAirport().getId())).withRel("arrival-airport"));
        return resource;
    }

    @Transactional
    public PagedResources<Resource<Flight>> getDepartingFlights(long airportId, int page, int size) {
        Airport departingAirport = airportRepository.findById(airportId).orElseThrow();
        Page<Flight> flightPage = flightRepository.findByDepartingAirport(departingAirport, PageRequest.of(page, size));
        PagedResourcesAssembler<Flight> pagedResourcesAssembler = new PagedResourcesAssembler<>(null, null);
        return pagedResourcesAssembler.toResource(flightPage, flightResourceAssembler);
    }

    @Transactional
    public PagedResources<Resource<Flight>> getArrivalFlights(long airportId, int page, int size) {
        Airport departingAirport = airportRepository.findById(airportId).orElseThrow();
        Page<Flight> flightPage = flightRepository.findByArrivalAirport(departingAirport, PageRequest.of(page, size));
        PagedResourcesAssembler<Flight> pagedResourcesAssembler = new PagedResourcesAssembler<>(null, null);
        return pagedResourcesAssembler.toResource(flightPage, flightResourceAssembler);
    }

    public Resources<Resource<Flight>> getFeaturedFlights() {
        List<Flight> flights = flightRepository
                .findByDepartureTimeAfterAndFeatured(LocalDateTime.now(), true);
        List<Airport> airports = flights.stream().map(Flight::getDepartingAirport).distinct().collect(Collectors.toList());
        List<Resource<Flight>> resourceList = new ArrayList<>();
        airports.stream().limit(4).forEach(airport -> {
            resourceList.add(flights.stream().filter(f -> f.getArrivalAirport().equals(airport)).findFirst().map(flightResourceAssembler::toResource).get());
        });
        Resources<Resource<Flight>> resources = new Resources<>(resourceList);
        resources.add(linkTo(methodOn(AirportFlightsController.class).getFeaturedFlights()).withSelfRel());
        return resources;
    }
}

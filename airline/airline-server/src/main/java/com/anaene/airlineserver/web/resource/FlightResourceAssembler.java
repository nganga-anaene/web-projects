package com.anaene.airlineserver.web.resource;

import com.anaene.airlineserver.data.entity.Flight;
import com.anaene.airlineserver.data.repository.BookingRepository;
import com.anaene.airlineserver.web.controller.AirportFlightsController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class FlightResourceAssembler implements ResourceAssembler<Flight, Resource<Flight>> {

    private final BookingRepository bookingRepository;

    public FlightResourceAssembler(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Resource<Flight> toResource(Flight flight) {
        flight.setCurrentBookings(setCurrentBookings(flight));
        Resource<Flight> resource = new Resource<>(flight);
        resource.add(linkTo(methodOn(AirportFlightsController.class).getFlightById(flight.getId())).withSelfRel());
        return resource;
    }

    @Transactional
    public int setCurrentBookings(Flight flight) {
        return this.bookingRepository.findByFlightsContaining(flight).size();
    }
}

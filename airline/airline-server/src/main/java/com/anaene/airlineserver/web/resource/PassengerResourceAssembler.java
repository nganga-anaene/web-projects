package com.anaene.airlineserver.web.resource;

import com.anaene.airlineserver.data.entity.Passenger;
import com.anaene.airlineserver.web.controller.PassengerController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class PassengerResourceAssembler implements ResourceAssembler<Passenger, Resource<Passenger>> {
    @Override
    public Resource<Passenger> toResource(Passenger passenger) {
        Resource<Passenger> resource = new Resource<>(passenger);
        resource.add(linkTo(methodOn(PassengerController.class).getPassengerDetails(passenger.getId())).withSelfRel());
        resource.add(linkTo(methodOn(PassengerController.class).getPassengerBookings(passenger.getId())).withRel("bookings"));
        return resource;
    }
}

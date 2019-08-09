package com.anaene.airlineserver.web.resource;

import com.anaene.airlineserver.data.entity.Booking;
import com.anaene.airlineserver.web.controller.PassengerController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class BookingResourceAssembler implements ResourceAssembler<Booking, Resource<Booking>> {
    @Override
    public Resource<Booking> toResource(Booking entity) {
        Resource<Booking> resource = new Resource<>(entity);
        resource.add(linkTo(methodOn(PassengerController.class).getPassengerBooking(entity.getPassenger().getId(), entity.getId())).withSelfRel());
        return resource;
    }
}

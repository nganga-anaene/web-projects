package com.anaene.airlineserver.web.resource;

import com.anaene.airlineserver.data.entity.Airport;
import com.anaene.airlineserver.web.controller.AirportController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class AirportResourceAssembler implements ResourceAssembler<Airport, Resource<Airport>> {
    @Override
    public Resource<Airport> toResource(Airport airport) {
        Resource<Airport> resource = new Resource<>(airport);
        resource.add(linkTo(methodOn(AirportController.class)).withSelfRel());
        return resource;
    }
}

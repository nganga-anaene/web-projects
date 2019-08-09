package com.anaene.airlineserver.web.resource;

import com.anaene.airlineserver.data.entity.Flight;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class FlightResourceAssembler implements ResourceAssembler<Flight, Resource<Flight>> {
    @Override
    public Resource<Flight> toResource(Flight flight) {
        return new Resource<>(flight);
    }
}

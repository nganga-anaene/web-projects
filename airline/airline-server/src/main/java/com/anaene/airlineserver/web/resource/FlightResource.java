package com.anaene.airlineserver.web.resource;

import com.anaene.airlineserver.data.entity.Flight;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

public class FlightResource implements ResourceAssembler<Flight, Resource<Flight>> {
    @Override
    public Resource<Flight> toResource(Flight flight) {
        Resource<Flight> resource = new Resource<>(flight);
        return resource;
    }
}

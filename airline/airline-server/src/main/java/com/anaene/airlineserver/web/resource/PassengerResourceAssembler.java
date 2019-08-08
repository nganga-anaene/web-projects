package com.anaene.airlineserver.web.resource;

import com.anaene.airlineserver.data.entity.Passenger;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

public class PassengerResourceAssembler implements ResourceAssembler<Passenger, Resource<Passenger>> {
    @Override
    public Resource<Passenger> toResource(Passenger passenger) {
        Resource<Passenger> resource = new Resource<>(passenger);

        return resource;
    }
}

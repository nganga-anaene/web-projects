package com.anaene.airlineserver.web.resource;

import com.anaene.airlineserver.data.entity.Booking;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

public class BookingResource implements ResourceAssembler<Booking, Resource<Booking>> {
    @Override
    public Resource<Booking> toResource(Booking entity) {
        return null;
    }
}

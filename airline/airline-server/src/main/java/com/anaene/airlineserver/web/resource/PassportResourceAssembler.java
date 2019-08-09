package com.anaene.airlineserver.web.resource;

import com.anaene.airlineserver.data.entity.Passport;
import com.anaene.airlineserver.web.controller.PassengerController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class PassportResourceAssembler implements ResourceAssembler<Passport, Resource<Passport>> {
    @Override
    public Resource<Passport> toResource(Passport entity) {
        return new Resource<>(entity);
    }
}

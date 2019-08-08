package com.anaene.airlineserver.web.resource;

import com.anaene.airlineserver.data.entity.Passport;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

public class PassportResource implements ResourceAssembler<Passport, Resource<Passport>> {
    @Override
    public Resource<Passport> toResource(Passport entity) {
        return null;
    }
}

package com.anaene.airlineserver.web.resource;

import com.anaene.airlineserver.data.entity.Client;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class ClientResourceAssembler implements ResourceAssembler<Client, Resource<Client>> {
    @Override
    public Resource<Client> toResource(Client entity) {
        return null;
    }
}

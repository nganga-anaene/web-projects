package com.anaene.airlineserver.web.resource;

import com.anaene.airlineserver.data.entity.Client;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

public class ClientResource implements ResourceAssembler<Client, Resource<Client>> {
    @Override
    public Resource<Client> toResource(Client entity) {
        return null;
    }
}

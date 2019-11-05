package bookstore.web.resource;

import bookstore.data.entity.Address;
import bookstore.web.controller.AccountController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class AddressResourceAssembler implements ResourceAssembler<Address, Resource<Address>> {
    @Override
    public Resource<Address> toResource(Address address) {
        Resource<Address> resource = new Resource<>(address);
        resource.add(linkTo(methodOn(AccountController.class).getCustomerAddress(address.getId())).withSelfRel());
        resource.add(linkTo(methodOn(AccountController.class).getCustomer()).withRel("account"));
        return resource;
    }
}

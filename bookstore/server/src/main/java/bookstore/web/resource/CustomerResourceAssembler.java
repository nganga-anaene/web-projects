package bookstore.web.resource;

import bookstore.data.entity.Customer;
import bookstore.web.controller.AccountController;
import bookstore.web.controller.CustomerOrderController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CustomerResourceAssembler implements ResourceAssembler<Customer, Resource<Customer>> {
    @Override
    public Resource<Customer> toResource(Customer customer) {
        Resource<Customer> resource = new Resource<>(customer);
        resource.add(linkTo(methodOn(AccountController.class).getCustomer()).withSelfRel());
        if (customer.getName() == null || customer.getAddresses().isEmpty() || customer.getUser() == null) {
            resource.add(linkTo(methodOn(AccountController.class).completeRegistration(customer)).withRel("complete-registration"));
        }
        resource.add(linkTo(CustomerOrderController.class).withRel("orders"));
        resource.add(linkTo(methodOn(AccountController.class).getCustomerAddresses()).withRel("addresses"));
        return resource;
    }
}

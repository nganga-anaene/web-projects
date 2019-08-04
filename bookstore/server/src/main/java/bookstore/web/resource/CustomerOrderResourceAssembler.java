package bookstore.web.resource;

import bookstore.data.entity.CustomerOrder;
import bookstore.web.controller.CustomerOrderController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CustomerOrderResourceAssembler implements ResourceAssembler<CustomerOrder, Resource<CustomerOrder>> {
    @Override
    public Resource<CustomerOrder> toResource(CustomerOrder entity) {
        Resource<CustomerOrder> resource = new Resource<>(entity);
        resource.add(linkTo(methodOn(CustomerOrderController.class).getCustomerOrder(entity.getId())).withSelfRel());
        return resource;
    }
}

package bookstore.web.controller;

import bookstore.data.entity.BookItem;
import bookstore.data.entity.Customer;
import bookstore.data.entity.CustomerOrder;
import bookstore.server.config.User;
import bookstore.web.resource.CustomerOrderResourceAssembler;
import bookstore.web.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("account/orders")
public class CustomerOrderController {

    private final CustomerService service;
    private final CustomerOrderResourceAssembler assembler;

    public CustomerOrderController(CustomerService service, CustomerOrderResourceAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<CustomerOrder>> getCustomerOrder(@PathVariable long id) {
        try {
            Customer customer = loggedInCustomer();
            CustomerOrder order = service.getOrdersByCustomerAndId(customer, id);
            Resource<CustomerOrder> resource = assembler.toResource(order);
            resource.add(linkTo(methodOn(AccountController.class).getCustomer()).withRel("user"));
            resource.add(linkTo(CustomerOrderController.class).withRel("orders"));
            resource.add(order.getBookItems().stream().map(this::getBookItemLink).collect(Collectors.toList()));
            resource.add(order.getBookItems().stream().map(this::getBookLink).collect(Collectors.toList()));
            return ResponseEntity.ok(resource);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<PagedResources<Resource<CustomerOrder>>> getCustomerOrders(PagedResourcesAssembler<CustomerOrder> pagedResourcesAssembler) {
        Page<CustomerOrder> customerOrderPage = service.getCustomerOrders(loggedInCustomer());
        PagedResources<Resource<CustomerOrder>> resources = pagedResourcesAssembler.toResource(customerOrderPage, assembler);
        resources.add(linkTo(methodOn(AccountController.class).getCustomer()).withRel("user"));
        return ResponseEntity.ok(resources);
    }

    private Customer loggedInCustomer() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.findCustomerByUsername(user.getUsername());
    }

    private Link getBookItemLink(BookItem bookItem) {
        return linkTo(methodOn(BookController.class)
                .getBookItem(bookItem.getBook().getId(), bookItem.getId()))
                .withRel("bookItems");
    }

    private Link getBookLink(BookItem bookItem) {
        return linkTo(methodOn(BookController.class)
                .getFullBook(bookItem.getBook().getId()))
                .withRel("books");
    }
}

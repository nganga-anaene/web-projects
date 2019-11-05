package bookstore.web.controller;

import bookstore.data.entity.Address;
import bookstore.data.entity.BookItem;
import bookstore.data.entity.Customer;
import bookstore.data.entity.CustomerOrder;
import bookstore.server.config.User;
import bookstore.web.resource.CustomerOrderResourceAssembler;
import bookstore.web.service.BookService;
import bookstore.web.service.CustomerService;
import bookstore.web.util.Order;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("order")
public class OrderController {


    private final CustomerService customerService;
    private final BookService bookService;

    public OrderController(CustomerService customerService, BookService bookService) {
        this.customerService = customerService;
        this.bookService = bookService;
    }

    @GetMapping(value = "")
    public ResponseEntity<Resource<CustomerOrder>> getCustomerOrder(@RequestBody Order order, CustomerOrderResourceAssembler resourceAssembler) {
        try {
            if (order.getBookItemIds().isEmpty()) throw new EntityNotFoundException();
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer customer = customerService.findCustomerByUsername(user.getUsername());
            Set<BookItem> bookItems = bookService.getBookItemsByIdIn(order.getBookItemIds());
            Address address = getAddress(order, customer);
            CustomerOrder customerOrder = new CustomerOrder(customer, address, bookItems);
            customerOrder = customerService.saveCustomerOrder(customerOrder);
            Resource<CustomerOrder> resource = getCustomerOrderResource(customerOrder, resourceAssembler);
            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "", params = "orderId")
    public ResponseEntity<Resource<CustomerOrder>> cancelOrder(@RequestParam("orderId") long orderId, CustomerOrderResourceAssembler resourceAssembler) {
        try {
            CustomerOrder customerOrder = customerService.getCustomerOrderById(orderId);
            customerOrder = customerService.cancelOrder(customerOrder);
            return ResponseEntity.ok(getCustomerOrderResource(customerOrder, resourceAssembler));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    private Resource<CustomerOrder> getCustomerOrderResource(CustomerOrder customerOrder, CustomerOrderResourceAssembler resourceAssembler) {
        Resource<CustomerOrder> resource = resourceAssembler.toResource(customerOrder);
        resource.add(linkTo(AccountController.class).withRel("user"));
        resource.add(linkTo(CustomerOrderController.class).withRel("orders"));
        return resource;
    }

    private Address getAddress(Order order, Customer customer) {
        return (order.getAddress() != null) ? customerService.getAddress(order.getAddress())
                : customer.getAddresses().stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("NO ADDRESS FOUND"));
    }
}

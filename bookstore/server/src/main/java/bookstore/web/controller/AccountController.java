package bookstore.web.controller;

import bookstore.data.entity.Customer;
import bookstore.server.config.User;
import bookstore.web.exception.CustomerNotFoundException;
import bookstore.web.resource.CustomerResourceAssembler;
import bookstore.web.service.CustomerService;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("account")
public class AccountController {

    private final CustomerService service;
    private final CustomerResourceAssembler assembler;

    public AccountController(CustomerService service, CustomerResourceAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping("")
    public ResponseEntity<Resource<Customer>> getCustomer() {
        try {
            Customer customer = loggedInCustomer();
            Resource<Customer> resource = assembler.toResource(customer);
            return ResponseEntity.ok(assembler.toResource(customer));
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/complete-registration")
    public ResponseEntity<Resource<Customer>> completeRegistration(@RequestBody Customer customer) {
        try{
            customer = service.saveCompleteCustomer(customer);
            return ResponseEntity.ok(assembler.toResource(customer));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private Customer loggedInCustomer() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.findCustomerByUsername(user.getUsername());
    }
}

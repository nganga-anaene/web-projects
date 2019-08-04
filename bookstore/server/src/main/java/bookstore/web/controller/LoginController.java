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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class LoginController {


    private final CustomerService customerService;

    public LoginController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("login")
    public ResponseEntity<Resource<Customer>> login(CustomerResourceAssembler assembler) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer customer = customerService.findCustomerByUsername(user.getUsername());
            return ResponseEntity.ok(assembler.toResource(customer));
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("logout")
    public ResponseEntity logout(HttpServletResponse response) throws IOException {
        System.out.println("Logging out");
        return ResponseEntity.ok("Logged out");
    }

}

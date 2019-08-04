package bookstore.web.controller;

import bookstore.data.entity.Customer;
import bookstore.data.entity.util.AppUser;
import bookstore.server.config.User;
import bookstore.web.resource.CustomerResourceAssembler;
import bookstore.web.service.CustomerService;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityExistsException;

@RestController
@RequestMapping("sign-up")
public class RegistrationController {

    private final CustomerService customerService;
    private final CustomerResourceAssembler assembler;

    public RegistrationController(CustomerService customerService, CustomerResourceAssembler assembler) {
        this.customerService = customerService;
        this.assembler = assembler;
    }

    @PostMapping("")
    public ResponseEntity<Resource<Customer>> saveUser(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email) {
        try {
            AppUser user = new AppUser(username, email, customerService.encodePassword(password));
            Customer customer = customerService.saveUserDetails(user);
            Resource<Customer> resource = assembler.toResource(customer);
            login(customer.getUser());
            return ResponseEntity.ok(resource);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
    }

    private void login(AppUser user) {

    }

    private User setRegisteredUser(AppUser user) {
        return new User(user.getUsername(), user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
    }
}

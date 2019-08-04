package bookstore.web.exception;

import javax.persistence.EntityNotFoundException;

public class CustomerNotFoundException extends EntityNotFoundException {

    public CustomerNotFoundException(String username) {
        super("Customer with username: " + username + " DOES NOT EXIST");
    }

    public CustomerNotFoundException(long id) {
        super("Customer with id: " + id + "DOES NOT EXIST");
    }
}

package bookstore.web.exception;

import bookstore.data.entity.CustomerOrder;

import javax.persistence.EntityNotFoundException;

public class CustomerOrderNotFoundException extends EntityNotFoundException {

    public CustomerOrderNotFoundException(String name, long id) {
        super("Customer order for " + name + ", with id " + id + "NOT FOUND");
    }

    public CustomerOrderNotFoundException(long id) {
        super("Customer order: " + id + "NOT FOUND");
    }
}

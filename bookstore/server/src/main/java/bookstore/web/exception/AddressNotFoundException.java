package bookstore.web.exception;

import javax.persistence.EntityNotFoundException;

public class AddressNotFoundException extends EntityNotFoundException {

    public AddressNotFoundException(String street, String postcode, String city) {
        super("Address [street=" + street + ", postcode=" + postcode +", city=" + city + "] NOT FOUND");
    }
}

package bookstore.web.exception;

import javax.persistence.EntityNotFoundException;

public class BookNotFoundException extends EntityNotFoundException {

    public BookNotFoundException(Long id) {
        super("Book with id: " + id + " does not exist");
    }

    public BookNotFoundException(String isbn) {
        super("Book with isbn: " + isbn + " does not exist");
    }
}

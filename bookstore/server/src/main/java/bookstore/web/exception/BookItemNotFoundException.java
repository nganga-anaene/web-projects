package bookstore.web.exception;

import javax.persistence.EntityNotFoundException;

public class BookItemNotFoundException extends EntityNotFoundException {

    public BookItemNotFoundException(String bookTitle, Long bookItemId) {
        super("Book: " + bookTitle + ", item: " + bookItemId + " NOT FOUND");
    }
}

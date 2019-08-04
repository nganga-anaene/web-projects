package bookstore.web.exception;

import bookstore.data.entity.Book;

import javax.persistence.EntityNotFoundException;

public class AuthorNotFoundException extends EntityNotFoundException {

    public AuthorNotFoundException(Book book){
        super("Author for book: " + book.getTitle() + " NOT FOUND");
    }

    public AuthorNotFoundException(long id) {
        super("Author: " + id + " NOT FOUND");
    }
}

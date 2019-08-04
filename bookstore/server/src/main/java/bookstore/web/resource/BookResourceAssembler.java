package bookstore.web.resource;

import bookstore.data.entity.Book;
import bookstore.web.controller.BookController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class BookResourceAssembler implements ResourceAssembler<Book, Resource<Book>> {
    @Override
    public Resource<Book> toResource(Book book) {
        Resource<Book> bookResource = new Resource<>(book);
        bookResource.add(linkTo(methodOn(BookController.class).getFullBook(book.getId())).withSelfRel());
        return bookResource;
    }
}

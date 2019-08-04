package bookstore.web.resource;

import bookstore.data.entity.BookItem;
import bookstore.web.controller.BookController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class BookItemResourceAssembler implements ResourceAssembler<BookItem, Resource<BookItem>> {
    @Override
    public Resource<BookItem> toResource(BookItem bookItem) {
        Resource<BookItem> resource = new Resource<>(bookItem);
        resource.add(linkTo(methodOn(BookController.class).getBookItem(bookItem.getBook().getId(), bookItem.getId())).withSelfRel());
        return resource;
    }
}

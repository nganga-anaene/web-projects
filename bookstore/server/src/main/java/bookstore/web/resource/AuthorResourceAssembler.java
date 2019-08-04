package bookstore.web.resource;

import bookstore.data.entity.Author;
import bookstore.web.controller.AuthorController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class AuthorResourceAssembler implements ResourceAssembler<Author, Resource<Author>> {
    @Override
    public Resource<Author> toResource(Author author) {
        Resource<Author> resource = new Resource<>(author);
        resource.add(linkTo(methodOn(AuthorController.class).findAuthorById(author.getId())).withSelfRel());
        resource.add(linkTo(methodOn(AuthorController.class).getAuthorBooks(author.getId())).withRel("books"));
        return resource;
    }
}
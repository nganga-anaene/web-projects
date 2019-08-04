package bookstore.web.controller;

import bookstore.data.entity.Author;
import bookstore.data.entity.Book;
import bookstore.web.exception.AuthorNotFoundException;
import bookstore.web.resource.AuthorResourceAssembler;
import bookstore.web.resource.BookResourceAssembler;
import bookstore.web.service.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("authors")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorResourceAssembler assembler;

    public AuthorController(final AuthorService authorService, AuthorResourceAssembler assembler) {
        this.authorService = authorService;
        this.assembler = assembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<Author>> findAuthorById(@PathVariable Long id) {
        try {
            Author author = authorService.findAuthorById(id);
            Resource<Author> resource = assembler.toResource(author);
            resource.add(linkTo(AuthorController.class).withRel("authors"));
            return ResponseEntity.ok(resource);
        } catch (AuthorNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "", params = {"page", "size"})
    public ResponseEntity<PagedResources<Resource<Author>>> getAuthors(@RequestParam("page") int page, @RequestParam("size") int size, PagedResourcesAssembler<Author> pagedResourcesAssembler) {
        return getAuthorPagedResources(page, size, pagedResourcesAssembler);
    }

    @GetMapping("")
    public ResponseEntity<PagedResources<Resource<Author>>> getAuthors(PagedResourcesAssembler<Author> pagedResourcesAssembler) {
        return getAuthorPagedResources(0, 5, pagedResourcesAssembler);
    }

    public ResponseEntity<PagedResources<Resource<Author>>> getAuthorPagedResources(int page, int size, PagedResourcesAssembler<Author> pagedResourcesAssembler) {
        Page<Author> authorPage = authorService.findAuthors(page, size);
        if (authorPage.getSize() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PagedResources<Resource<Author>> resources = pagedResourcesAssembler.toResource(authorPage, assembler);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<Resources<Resource<Book>>> getAuthorBooks(@PathVariable long id) {
        BookResourceAssembler assembler = new BookResourceAssembler();
        List<Resource<Book>> bookResources = authorService.findAuthorById(id)
                .getBooks()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
        Resources<Resource<Book>> resources = new Resources<>(bookResources, linkTo(methodOn(AuthorController.class).getAuthorBooks(id)).withSelfRel());
        resources.add(linkTo(methodOn(AuthorController.class).findAuthorById(id)).withRel("author"));
        return ResponseEntity.ok(resources);
    }
}

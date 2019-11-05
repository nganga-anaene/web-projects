package bookstore.web.controller;

import bookstore.data.entity.Book;
import bookstore.data.entity.BookItem;
import bookstore.data.entity.Publisher;
import bookstore.web.exception.BookItemNotFoundException;
import bookstore.web.exception.BookNotFoundException;
import bookstore.web.resource.BookItemResourceAssembler;
import bookstore.web.resource.BookResourceAssembler;
import bookstore.web.service.BookService;
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
@RequestMapping("books")
public class BookController {


    private final BookService bookService;
    private final BookResourceAssembler assembler;
    private final BookItemResourceAssembler bookItemResourceAssembler;

    public BookController(BookService bookService, BookResourceAssembler assembler, BookItemResourceAssembler bookItemResourceAssembler) {
        this.bookService = bookService;
        this.assembler = assembler;
        this.bookItemResourceAssembler = bookItemResourceAssembler;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Resource<Book>> getFullBook(@PathVariable long id) {
        try {
            Book book = bookService.getFullBook(id);
            Resource<Book> resource = assembler.toResource(book);
            long authorId = book.getAuthor().getId();
            resource.add(linkTo(methodOn(AuthorController.class).findAuthorById(authorId)).withRel("author"));
            resource.add(linkTo(methodOn(BookController.class).getBookItems(id)).withRel("bookItems"));
            resource.add(linkTo(BookController.class).withRel("books"));
            return ResponseEntity.ok(resource);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<Resources<Resource<BookItem>>> getBookItems(@PathVariable long id) {
        Resources<Resource<BookItem>> resources = new Resources<>(bookService.getFullBook(id)
                .getBookItems()
                .stream()
                .map(bookItemResourceAssembler::toResource)
                .collect(Collectors.toList()));
        resources.add(linkTo(methodOn(BookController.class).getFullBook(id)).withRel("book"));
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{bookId}/items/{bookItemId}")
    public ResponseEntity<Resource<BookItem>> getBookItem(@PathVariable long bookId, @PathVariable long bookItemId) {
        try {
            BookItem bookItem = bookService.getBookItem(bookId, bookItemId);
            Resource<BookItem> resource = new Resource<>(bookItem);
            resource.add(linkTo(methodOn(BookController.class).getBookItem(bookId, bookItemId)).withSelfRel());
            resource.add(linkTo(methodOn(BookController.class).getBookItems(bookId)).withRel("bookItems"));
            resource.add(linkTo(methodOn(BookController.class).getFullBook(bookId)).withRel("book"));
            return ResponseEntity.ok(resource);
        } catch (BookItemNotFoundException | BookNotFoundException E) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/recommended")
    public ResponseEntity<Resources<Resource<Book>>> getRecommendedBooks() {
        List<Resource<Book>> resourceList = bookService.getRecommendedBooks().stream().map(assembler::toResource).collect(Collectors.toList());
        Resources<Resource<Book>> resources = new Resources<>(resourceList);
        resources.add(linkTo(methodOn(BookController.class).getRecommendedBooks()).withSelfRel());
        return ResponseEntity.ok(resources);
    }

    @GetMapping(value = "")
    public ResponseEntity<PagedResources<Resource<Book>>> getBooks(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            PagedResourcesAssembler<Book> pagedResourcesAssembler) {
        return getPagedResources(page, size, pagedResourcesAssembler);
    }

    public ResponseEntity<PagedResources<Resource<Book>>> getPagedResources(int page, int size, PagedResourcesAssembler<Book> pagedResourcesAssembler) {
        Page<Book> bookPage = bookService.getBookPage(page, size);
        PagedResources<Resource<Book>> pagedResources = pagedResourcesAssembler.toResource(bookPage, assembler);
        return ResponseEntity.ok(pagedResources);
    }

    @GetMapping("/{bookId}/publisher")
    public ResponseEntity<Resource<Publisher>> getBookPublisher(@PathVariable long bookId) {
        try {
            Resource<Publisher> resource = new Resource<>(bookService.getPublisher(bookId));
            resource.add(linkTo(methodOn(BookController.class).getBookPublisher(bookId)).withSelfRel());
            resource.add(linkTo(methodOn(BookController.class).getFullBook(bookId)).withRel("book"));
            return ResponseEntity.ok(resource);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Resources<Resource<String>>> getBookTitles() {
        Resources<Resource<String>> bookTitles = new Resources<>(bookService.getBookTitles());
        bookTitles.add(linkTo(methodOn(BookController.class).getBookTitles()).withSelfRel());
        bookTitles.add(linkTo(BookController.class).withRel("books"));
        return ResponseEntity.ok(bookTitles);
    }
}

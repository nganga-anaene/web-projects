package bookstore.web.service;

import bookstore.data.entity.Book;
import bookstore.data.entity.BookItem;
import bookstore.data.entity.Publisher;
import bookstore.data.repository.BookItemRepository;
import bookstore.data.repository.BookRepository;
import bookstore.web.controller.BookController;
import bookstore.web.exception.BookItemNotFoundException;
import bookstore.web.exception.BookNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookItemRepository bookItemRepository;

    public BookService(final BookRepository bookRepository, final BookItemRepository bookItemRepository) {
        this.bookRepository = bookRepository;
        this.bookItemRepository = bookItemRepository;
    }

    public Page<Book> getBookPage(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Book> getBookPageSorted(int page, int size, Sort sort) {
        return bookRepository.findAll(PageRequest.of(page, size, sort));
    }

    @Transactional
    public Book getBook(long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    @Transactional
    public Book getFullBook(long id) {
        return bookRepository.findFullBookById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    @Transactional
    public BookItem getBookItem(long bookId, long bookItemId) {
        Book book = bookRepository.findFullBookById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        return bookItemRepository.findByBookAndId(book, bookItemId)
                .orElseThrow(() -> new BookItemNotFoundException(book.getTitle(), bookItemId));
    }

    @Transactional
    public Publisher getPublisher(long bookId) {
        Book book = bookRepository.findFullBookById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        return book.getPublisher();
    }

    @Transactional
    public Set<BookItem> getBookItemsByIdIn(List<Long> bookItemIds) {
        return new HashSet<>(bookItemRepository.findByIdIn(bookItemIds));
    }

    @Transactional
    public List<Resource<String>> getBookTitles() {
        return bookRepository.findAll().stream().map(this::bookTitleResource).collect(Collectors.toList());
    }

    private Resource<String> bookTitleResource(Book book) {
        Resource<String> resource = new Resource<>(book.getTitle());
        resource.add(linkTo(methodOn(BookController.class).getFullBook(book.getId())).withRel("book"));
        return resource;
    }
}

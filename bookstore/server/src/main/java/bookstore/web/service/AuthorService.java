package bookstore.web.service;

import bookstore.data.entity.Author;
import bookstore.data.entity.Book;
import bookstore.data.repository.AuthorRepository;
import bookstore.web.exception.AuthorNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Author findAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
    }

    @Transactional
    public Author getAuthorByBook(Book book) {
        return authorRepository.findByBooksContains(book).orElseThrow(() -> new AuthorNotFoundException(book));
    }

    @Transactional
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Transactional
    public Page<Author> findAuthors(int page, int size) {
        return authorRepository.findAll(PageRequest.of(page, size));
    }
}
